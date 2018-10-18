package yunnuo.baseframe.net.download;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import yunnuo.baseframe.BuildConfig;
import yunnuo.baseframe.R;


public class DownloadService extends IntentService {
//    private static final String TAG = "1111";
//    private static final String ACTION_FOO = "com.bocop.manageassist.download.action.FOO";

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    int downloadCount = 0;
    private File outputFile;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("下载")
                .setContentText("下载中...")
                .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());

        download(intent.getStringExtra("url"));
    }
    private void download(String apkUrl) {
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                //不频繁发送通知，防止通知栏下拉卡顿
                int progress = (int) ((bytesRead * 100) / contentLength);
                if ((downloadCount == 0) || progress > downloadCount||done) {
                    Download download = new Download();
                    download.setTotalFileSize(contentLength);
                    download.setCurrentFileSize(bytesRead);
                    download.setProgress(progress);

                    sendNotification(download);

                    downloadCount = progress;
                }

            }
        };
//        outputFile = new File(Environment.getExternalStoragePublicDirectory
//                (Environment.DIRECTORY_DOWNLOADS), "file.apk");
        outputFile = new File(this.getExternalFilesDir(null) + "/updateAPK/"+ "manage.apk");
        if (outputFile.exists()) {
            outputFile.delete();
        }


        //这个地址没有实际作用，不写会报错，以后面的地址为准
        new DownloadRetrofitClient("https://180.168.146.77", listener).download(apkUrl, outputFile, new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                downloadCompleted();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                downloadCompleted();
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    private void downloadCompleted() {
        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentTitle("XXX应用");
        notificationBuilder.setContentText("下载完成");
        notificationManager.notify(0, notificationBuilder.build());

        //安装apk
        installAPK();
    }

    /** 下载完成后自动安装apk */
    public void installAPK() {
//        File apkFile = new File(outputFile);
        if (!outputFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".fileProvider", outputFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(outputFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }

    private void sendNotification(Download download) {

        sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(
                StringUtils.getDataSize(download.getCurrentFileSize()) + "/" +
                        StringUtils.getDataSize(download.getTotalFileSize()));
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download) {

        Intent intent = new Intent(BuildConfig.APPLICATION_ID+".download");
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }
}

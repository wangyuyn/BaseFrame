package yunnuo.baseframe.net;

import android.content.Context;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import yunnuo.baseframe.MyApp;
import yunnuo.baseframe.base.BaseResponse;
import yunnuo.baseframe.function.gsonconverter.CustomException;
import yunnuo.baseframe.function.gsonconverter.SystemException;


/**
 * Created by wangyu on 2017/7/24.
 */

public abstract class ProgressSubscriber<T extends BaseResponse> implements ProgressCancelListener, Observer<T> {

    private Context mContext;
    private ProgressDialogHandler mHandler;
    Disposable disposable;

    public ProgressSubscriber(Context context) {
        this.mContext = context;
        mHandler = new ProgressDialogHandler(context, this, true);
    }


    private void showProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        showProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    @Override
    public void onNext(final T t) {
        onSuccessful(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(MyApp.getInstance(), "网络中断，请检查您的网络状态", Toast.LENGTH_LONG).show();
            onError("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            Toast.makeText(MyApp.getInstance(), "网络中断，请检查您的网络状态", Toast.LENGTH_LONG).show();
            onError("网络中断，请检查您的网络状态");
        } else if (e instanceof CustomException) {
            Toast.makeText(MyApp.getInstance(), e.getMessage(), Toast.LENGTH_LONG).show();
            onError(e.toString());
        } else if (e instanceof SystemException) {
            Toast.makeText(MyApp.getInstance(), e.getMessage(), Toast.LENGTH_LONG).show();
            onError(e.toString());
        } else {
            Toast.makeText(MyApp.getInstance(), e.getMessage(), Toast.LENGTH_LONG).show();
            onError(e.toString());
        }
        dismissProgressDialog();
    }


    @Override
    public void onCancelProgress() {
        if (!this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }

    public abstract void onSuccessful(T t);

    public abstract void onError(String string);

}

package yunnuo.baseframe.net;

import android.content.Context;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import yunnuo.baseframe.MyApp;
import yunnuo.baseframe.base.BaseResponse;


/**
 * Created by wangyu on 2017/7/24.
 */

public abstract class NoProgressSubscriber<T extends BaseResponse> implements Observer<T> {

    private Context mContext;
    public static long loginTime = 0;

    public NoProgressSubscriber(Context context) {
        this.mContext = context;
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(final T t) {
//      throw new SystemException(t.rtnmsg);

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
        } else {
            Toast.makeText(MyApp.getInstance(), e.getMessage(), Toast.LENGTH_LONG).show();
            onError(e.toString());
        }
    }

    public abstract void onSuccessful(T t);

    public abstract void onError(String string);

}

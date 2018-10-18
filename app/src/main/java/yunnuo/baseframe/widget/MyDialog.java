package yunnuo.baseframe.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import yunnuo.baseframe.R;


/**
 * Created by hxb on 2017/8/23.
 */

public class MyDialog extends Dialog {

    public MyDialog(Context context) {
        this(context, R.style.DialogStyle_3);
    }
    public MyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.mydialog_loading);
    }
}

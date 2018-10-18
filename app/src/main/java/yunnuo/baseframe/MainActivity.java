package yunnuo.baseframe;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import yunnuo.baseframe.base.BaseActivity;
import yunnuo.baseframe.component.ApplicationComponent;

public class MainActivity extends BaseActivity {

    @BindView(R.id.helloWorld)
    TextView helloWorld;

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }
}

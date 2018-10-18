package yunnuo.baseframe;

import android.app.Application;

import yunnuo.baseframe.component.ApplicationComponent;
import yunnuo.baseframe.component.DaggerApplicationComponent;
import yunnuo.baseframe.module.ApplicationModule;
import yunnuo.baseframe.module.HttpModule;
import yunnuo.baseframe.utils.ContextUtils;

public class MyApp extends Application {

    private ApplicationComponent mApplicationComponent;

    private static MyApp sMyApp;

    public static int width = 0;

    public static int height = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
//        BGASwipeBackManager.getInstance().init(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();
//        LitePal.initialize(this);
        width = ContextUtils.getSreenWidth(this);
        height = ContextUtils.getSreenHeight(this);

    }

    public static MyApp getInstance() {
        return sMyApp;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}

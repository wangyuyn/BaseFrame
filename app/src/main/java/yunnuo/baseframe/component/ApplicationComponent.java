package yunnuo.baseframe.component;

import android.content.Context;

import dagger.Component;
import yunnuo.baseframe.MyApp;
import yunnuo.baseframe.module.ApplicationModule;
import yunnuo.baseframe.module.HttpModule;
import yunnuo.baseframe.net.ApiService;

/**
 * Created by hxb on 2018/4/19.
 */

@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface ApplicationComponent {
    MyApp getApplication();

    ApiService getApiService();

    Context getContext();

}

package yunnuo.baseframe.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import yunnuo.baseframe.MyApp;

/**
 * Created by hxb on 2018/4/19.
 */
@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    MyApp provideApplication() {
        return (MyApp) mContext.getApplicationContext();
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}

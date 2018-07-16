package lawyerku.android_mitra.ui.splash;

import dagger.Module;
import dagger.Provides;
import lawyerku.android_mitra.base.annotation.ActivityScope;

@Module
public class SplashActivityModule {
    SplashActivity activity;

    public SplashActivityModule(SplashActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    SplashActivity providesSplashActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    SplashPresenter provideSplashPresenter(){
        return new SplashPresenter(activity/*, credentialModel*/);
    }
}

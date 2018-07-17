package lawyerku.android_mitra.ui.login;

import dagger.Module;
import dagger.Provides;
import lawyerku.android_mitra.base.annotation.ActivityScope;

@Module
public class LoginActivityModule {
    LoginActivity activity;

    public LoginActivityModule(LoginActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    LoginActivity provideLoginActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    LoginPresenter provideLoginPresenter(){
        return new LoginPresenter(activity);
    }
}

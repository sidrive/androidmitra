package lawyerku.android_mitra.base;

import javax.inject.Singleton;

import dagger.Component;
import lawyerku.android_mitra.ui.login.LoginActivityComponent;
import lawyerku.android_mitra.ui.login.LoginActivityModule;
import lawyerku.android_mitra.ui.splash.SplashActivityComponent;
import lawyerku.android_mitra.ui.splash.SplashActivityModule;

@Singleton
@Component(
        modules = {
                AppModule.class
//                FirebaseModule.class,
//                NetworkModule.class
        }
)
public interface AppComponent {

        SplashActivityComponent plus(SplashActivityModule activityModule);

        LoginActivityComponent plus(LoginActivityModule activityModule);
}

package lawyerku.mitra.base;

import javax.inject.Singleton;

import dagger.Component;
import lawyerku.mitra.ui.login.LoginActivityComponent;
import lawyerku.mitra.ui.login.LoginActivityModule;
import lawyerku.mitra.ui.splash.SplashActivityComponent;
import lawyerku.mitra.ui.splash.SplashActivityModule;

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

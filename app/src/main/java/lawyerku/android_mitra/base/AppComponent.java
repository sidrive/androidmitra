package lawyerku.android_mitra.base;

import javax.inject.Singleton;

import dagger.Component;
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
}

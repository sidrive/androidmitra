package lawyerku.android_mitra.ui.splash;

import dagger.Subcomponent;
import lawyerku.android_mitra.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                SplashActivityModule.class
        }
)
public interface SplashActivityComponent {
    SplashActivity inject(SplashActivity activity);
}

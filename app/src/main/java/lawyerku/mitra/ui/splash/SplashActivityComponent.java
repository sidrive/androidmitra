package lawyerku.mitra.ui.splash;

import dagger.Subcomponent;
import lawyerku.mitra.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                SplashActivityModule.class
        }
)
public interface SplashActivityComponent {
    SplashActivity inject(SplashActivity activity);
}

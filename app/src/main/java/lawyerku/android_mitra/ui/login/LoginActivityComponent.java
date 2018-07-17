package lawyerku.android_mitra.ui.login;

import dagger.Subcomponent;
import lawyerku.android_mitra.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                LoginActivityModule.class
        }
)
public interface LoginActivityComponent {
    LoginActivity inject(LoginActivity activity);
}

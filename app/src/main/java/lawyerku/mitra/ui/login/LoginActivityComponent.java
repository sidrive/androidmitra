package lawyerku.mitra.ui.login;

import dagger.Subcomponent;
import lawyerku.mitra.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                LoginActivityModule.class
        }
)
public interface LoginActivityComponent {
    LoginActivity inject(LoginActivity activity);
}

package lawyerku.mitra.ui.register;

import dagger.Subcomponent;
import lawyerku.mitra.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                RegisterActivityModule.class
        }
)
public interface RegisterActivityComponent {
    RegisterActivity inject(RegisterActivity activity);
}

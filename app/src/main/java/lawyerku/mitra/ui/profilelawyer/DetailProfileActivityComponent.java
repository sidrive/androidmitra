package lawyerku.mitra.ui.profilelawyer;

import dagger.Subcomponent;
import lawyerku.mitra.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                DetailProfileActivityModule.class
        }
)
public interface DetailProfileActivityComponent {
    DetailProfileActivity inject(DetailProfileActivity activity);
}

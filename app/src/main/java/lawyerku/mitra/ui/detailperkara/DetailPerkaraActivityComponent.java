package lawyerku.mitra.ui.detailperkara;

import dagger.Subcomponent;
import lawyerku.mitra.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                DetailPerkaraActivityModule.class
        }
)
public interface DetailPerkaraActivityComponent {
    DetailPerkaraActivity inject(DetailPerkaraActivity activity);
}

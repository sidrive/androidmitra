package lawyerku.mitra.ui.message;

import dagger.Subcomponent;
import lawyerku.mitra.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                MessageActivityModule.class
        }
)
public interface MessageActivityComponent {
    MessageActivity inject(MessageActivity activity);
}

package lawyerku.mitra.ui.profilelawyer;

import dagger.Module;
import dagger.Provides;
import lawyerku.mitra.base.annotation.ActivityScope;

@Module
public class DetailProfileActivityModule {
    DetailProfileActivity activity;

    public DetailProfileActivityModule(DetailProfileActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    DetailProfileActivity provideDetailProfileActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    DetailProfilePresenter provideDetailProfilePresenter(){
        return new DetailProfilePresenter(activity);
    }
}

package lawyerku.mitra.ui.detailperkara;

import dagger.Module;
import dagger.Provides;
import lawyerku.mitra.base.annotation.ActivityScope;

@Module
public class DetailPerkaraActivityModule {
    DetailPerkaraActivity activity;

    public DetailPerkaraActivityModule (DetailPerkaraActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    DetailPerkaraActivity provideDetailPerkaraActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    DetailPerkaraPresenter provideDetailPerkaraPresenter(){
        return new DetailPerkaraPresenter(activity);
    }
}

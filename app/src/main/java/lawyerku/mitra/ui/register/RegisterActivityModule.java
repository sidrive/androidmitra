package lawyerku.mitra.ui.register;

import dagger.Module;
import dagger.Provides;
import lawyerku.mitra.base.annotation.ActivityScope;

@Module
public class RegisterActivityModule {
    RegisterActivity activity;

    public RegisterActivityModule(RegisterActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    RegisterActivity provideRegisterActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    RegisterPresenter providRegisterPresenter(){
        return new RegisterPresenter(activity);
    }
}

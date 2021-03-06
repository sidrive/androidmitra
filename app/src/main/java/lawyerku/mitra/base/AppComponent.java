package lawyerku.mitra.base;

import javax.inject.Singleton;

import dagger.Component;
import lawyerku.mitra.ui.detailperkara.DetailPerkaraActivityComponent;
import lawyerku.mitra.ui.detailperkara.DetailPerkaraActivityModule;
import lawyerku.mitra.ui.login.LoginActivityComponent;
import lawyerku.mitra.ui.login.LoginActivityModule;
import lawyerku.mitra.ui.message.MessageActivity;
import lawyerku.mitra.ui.message.MessageActivityComponent;
import lawyerku.mitra.ui.message.MessageActivityModule;
import lawyerku.mitra.ui.profilelawyer.DetailProfileActivityComponent;
import lawyerku.mitra.ui.profilelawyer.DetailProfileActivityModule;
import lawyerku.mitra.ui.register.RegisterActivityComponent;
import lawyerku.mitra.ui.register.RegisterActivityModule;
import lawyerku.mitra.ui.splash.SplashActivityComponent;
import lawyerku.mitra.ui.splash.SplashActivityModule;

@Singleton
@Component(
        modules = {
                AppModule.class
//                FirebaseModule.class,
//                NetworkModule.class
        }
)
public interface AppComponent {

        SplashActivityComponent plus(SplashActivityModule activityModule);

        LoginActivityComponent plus(LoginActivityModule activityModule);

        RegisterActivityComponent plus(RegisterActivityModule activityModule);

        DetailPerkaraActivityComponent plus(DetailPerkaraActivityModule activityModule);

        DetailProfileActivityComponent plus(DetailProfileActivityModule activityModule);

        MessageActivityComponent plus(MessageActivityModule activityModule);
}

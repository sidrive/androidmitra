package lawyerku.mitra.ui.splash;

import lawyerku.mitra.base.BasePresenter;

public class SplashPresenter implements BasePresenter {
    SplashActivity activity;

    public SplashPresenter(SplashActivity activity/*, CredentialModel credentialModel*/){
        this.activity = activity;
//        this.credentialModel = credentialModel;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}

package lawyerku.mitra.ui.login;

import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import lawyerku.mitra.api.LawyerkuService;
import lawyerku.mitra.api.model.CredentialModel;
import lawyerku.mitra.base.BasePresenter;
import lawyerku.mitra.preference.GlobalPreference;
import lawyerku.mitra.preference.PrefKey;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.support.constraint.Constraints.TAG;

public class LoginPresenter implements BasePresenter {
    LoginActivity activity;
    private CompositeSubscription subscription;

    public LoginPresenter(LoginActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void validateLogin(CredentialModel.Request request){

        subscription.add(LawyerkuService.Factory.create().login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "validateLogin: "+response.data.token );

                    if(response.data.token != null){
                        GlobalPreference.write(PrefKey.loggedIn, true, Boolean.class);
                        GlobalPreference.write(PrefKey.accessToken, String.format(Locale.US, "Bearer %s", response.data.token), String.class);
                        activity.loginProses();
                    }

                    if (response.status >= 200 && response.status < 300) {
//

                    }
                    if(response.error != null){
                        Toast.makeText(activity, ""+response.error, Toast.LENGTH_SHORT).show();
                    }
//                    listener.hideLoading();
                    Log.e(TAG, "validateLogin: "+response.message );
                }, throwable -> {
//                    String msg = ErrorUtils.getError(throwable);
                    Log.e("loginNow", "CredentialPresenter :"+throwable);
                    Toast.makeText(activity, "Username/Password Salah", Toast.LENGTH_SHORT).show();
                    activity.showLoading(false);
                }));
    }
}

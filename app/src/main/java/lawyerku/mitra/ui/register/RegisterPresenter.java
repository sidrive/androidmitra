package lawyerku.mitra.ui.register;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lawyerku.mitra.api.LawyerkuService;
import lawyerku.mitra.api.model.CredentialModel;
import lawyerku.mitra.base.BasePresenter;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.support.constraint.Constraints.TAG;

public class RegisterPresenter implements BasePresenter{
    RegisterActivity activity;
    private CompositeSubscription subscription;

    public RegisterPresenter(RegisterActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void register(CredentialModel.Request request) {
//        listener.showLoading();
        Log.e(TAG, "register: "+request );

//        if (!SysUtils.isOnline(App.getContext())) {
//            listener.onError(App.getContext().getString(R.string.error_lost_connection));
//            return;
//        }
        subscription.add(LawyerkuService.Factory.create().register(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
//                    listener.hideLoading();
                    Log.e(TAG, "register: "+response );
                    if(response.success != null){
                        login();
                    }

                    else {
                        Log.e(TAG, "register: "+response.error );
                    }

                }, throwable -> {
                    HttpException error = (HttpException) throwable;
                    try {
                        String errorBody = error.response().errorBody().string();
                        try {
                            JSONObject jsonObject = new JSONObject(errorBody);
                            String errorMessage = jsonObject.getString("error");
                            Toast.makeText(activity,errorMessage,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    listener.hideLoading();


                }));
    }

    public void login(){
        activity.logogin();
    }
}

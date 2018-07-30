package lawyerku.mitra.ui.profilelawyer;

import android.util.Log;

import lawyerku.mitra.api.LawyerkuService;
import lawyerku.mitra.base.BasePresenter;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.support.constraint.Constraints.TAG;

public class DetailProfilePresenter implements BasePresenter {
    DetailProfileActivity activity;
    CompositeSubscription subscription;

    public DetailProfilePresenter(DetailProfileActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getProfile(String accessToken,int id) {
        Log.e(TAG, "getProfile: "+id );
        subscription.add(LawyerkuService.Factory.create().getProfileLawyer(accessToken,String.valueOf(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e("detailprofile", "getAccount: "+response );
                    if (response.status >= 200 && response.status < 300) {

                        activity.showProfile(response.data);

                    } else {
//                        profileListener.onError(response.message);
                    }
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
//                    if (errorCode > 400)
//                        profileListener.onError(App.getContext().getString(R.string.error_general));
//                    profileListener.hideLoading();
                }));
    }
}

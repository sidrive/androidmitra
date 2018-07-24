package lawyerku.mitra.ui.detailperkara;

import android.util.Log;

import lawyerku.mitra.api.LawyerkuService;
import lawyerku.mitra.api.model.PerkaraModel;
import lawyerku.mitra.base.BasePresenter;
import lawyerku.mitra.preference.GlobalPreference;
import lawyerku.mitra.preference.PrefKey;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.support.constraint.Constraints.TAG;

public class DetailPerkaraPresenter implements BasePresenter {
    DetailPerkaraActivity activity;
    private CompositeSubscription subscription;
    private PerkaraModel.Status perkara;
    String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);

    public DetailPerkaraPresenter(DetailPerkaraActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
        this.perkara = new PerkaraModel.Status();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getProject(int idproject){

        subscription.add(LawyerkuService.Factory.create().getProject(accessToken,String.valueOf(idproject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getLawyer: "+response.data );

                    if (response.status >= 200 && response.status < 300) {

                        activity.initProject(response.data);

                    } else {
//                        profileListener.onError(response.message);
                    }
//                    profileListener.hideLoading();
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
                    Log.e(TAG, "getProject: "+throwable );
//                    if (errorCode > 400)
//                        profileListener.onError(App.getContext().getString(R.string.error_general));
//                    profileListener.hideLoading();
                }));
    }

    public void approveProject(int id,boolean approve) {
        perkara.id = id;
        if(approve){
            perkara.status = "on-progress";
        }else {
            perkara.status = "rejected";
        }
        Log.e(TAG, "approveProject: "+perkara.status );
        subscription.add(LawyerkuService.Factory.create().approveProject(accessToken,perkara)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getLawyer: "+response );

                    if (response.status >= 200 && response.status < 300) {

                        activity.gotoMain();

                    } else {
//                        profileListener.onError(response.message);
                    }
//                    profileListener.hideLoading();
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
                    Log.e(TAG, "getProject: "+throwable );
//                    if (errorCode > 400)
//                        profileListener.onError(App.getContext().getString(R.string.error_general));
//                    profileListener.hideLoading();
                }));
    }
}

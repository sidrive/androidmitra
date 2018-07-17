package lawyerku.mitra.api;

import java.util.concurrent.TimeUnit;

import lawyerku.mitra.BuildConfig;
import lawyerku.mitra.api.model.CredentialModel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface LawyerkuService {
    //    ==============================================================
    //                      CREDENTIAL
    //    ==============================================================

//    @Headers("Accept:application/json")
//    @POST("api/authenticate?api_token=b3b45k4n")
//    Observable<CredentialModel.LoginResponse> login(
//            @Body CredentialModel.Request body);

    //    @Headers("Accept:application/json")
    @Headers("Content-Type:application/json")
    @POST("api/login")
    Observable<CredentialModel.LoginResponse> login(
            @Body CredentialModel.Request body);

    @Headers("Accept:application/json")
    @POST("api/register/")
    Observable<CredentialModel.RegistrationResponse> register(
//            @Path("type") String type,
            @Body CredentialModel.Request body);

    //    ==============================================================
    //                      PROFILE
    //    ==============================================================

    @Headers("Accept:application/json")
    @GET("api/me")
//    Observable<ProfileModel.Response> getProfile(@Header("Authorization") String header);
    Observable<Throwable> getProfile(@Header("Authorization") String header);


    //    ==============================================================
    //                      SERVICE
    //    ==============================================================

    class Factory {
        public static LawyerkuService create() {
            return getRetrofitConfig().create(LawyerkuService.class);
        }
        private static Retrofit retrofit = null;
        public static Retrofit getRetrofit(){
            return retrofit;
        }
        public static Retrofit getRetrofitConfig() {
            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.LawyerkuUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client())
                    .build();
        }

        private static OkHttpClient client() {
            return new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build();
        }
    }
}

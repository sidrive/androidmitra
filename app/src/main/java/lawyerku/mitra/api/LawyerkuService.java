package lawyerku.mitra.api;

import java.util.concurrent.TimeUnit;

import lawyerku.mitra.BuildConfig;
import lawyerku.mitra.api.model.CredentialModel;
import lawyerku.mitra.api.model.LawyerModel;
import lawyerku.mitra.api.model.MessageModel;
import lawyerku.mitra.api.model.PerkaraModel;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;
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
    @POST("api/me")
//    Observable<ProfileModel.Response> getProfile(@Header("Authorization") String header);
    Observable<LawyerModel.ResponseCustomer> getProfile(@Header("Authorization") String header);

    @Headers("Accept:application/json")
//    @GET("api/lawyers/{id}")
    @POST("api/me")
    Observable<LawyerModel.Response> getProfileLawyer
            (@Header("Authorization") String header/*,
            @Path("id") String id*/);

    @Headers("Accept:application/json")
    @PUT("api/lawyers/update")
    Observable<LawyerModel.ResponseUpdate> updateLawyer
            (@Header("Authorization") String header,
             @Body LawyerModel.DataUpdate body);

    //    ==============================================================
    //                      PROJECT
    //    ==============================================================

    @Headers("Content-Type:application/json")
    @GET("api/projects/new/get")
    Observable<PerkaraModel.Response> getProjectNew(
            @Header("Authorization") String header );

    @Headers("Content-Type:application/json")
    @GET("api/projects/{status}/get")
    Observable<PerkaraModel.Response> getDataProject(
            @Header("Authorization") String header,
            @Path("status") String status );

    @Headers("Content-Type:application/json")
    @GET("api/projects")
    Observable<PerkaraModel.Response> getListProject(
            @Header("Authorization") String header );

    @Headers("Content-Type:application/json")
    @GET("api/projects/{idProject}")
    Observable<PerkaraModel.Response> getProject(
            @Header("Authorization") String header,
            @Path ("idProject") String idProject );

    @Headers("Content-Type:application/json")
    @POST("api/projects/setStatus")
    Observable<PerkaraModel.ResponseSetStatus> approveProject(
            @Header("Authorization") String header,
            @Body PerkaraModel.Status body );

    //    ==============================================================
    //                      MESSAGE
    //    ==============================================================

    @Headers("Accept:application/json")
    @GET("api/message/{id}")
    Observable<MessageModel.Response> getMessage
            (@Header("Authorization") String header,
             @Path("id") int id);

    @Headers("Accept:application/json")
    @POST("api/message")
    Observable<MessageModel.Response> sendMessage
            (@Header("Authorization") String header,
             @Body MessageModel.MessageToSend body);


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

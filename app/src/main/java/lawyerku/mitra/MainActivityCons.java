package lawyerku.mitra;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lawyerku.mitra.api.LawyerkuService;
import lawyerku.mitra.api.model.PerkaraModel;
import lawyerku.mitra.mainfragment.HistoryFragment;
import lawyerku.mitra.mainfragment.PerkaraNewFragment;
import lawyerku.mitra.mainfragment.PerkaraOnProgressFragment;
import lawyerku.mitra.mainfragment.PerkaraRejectedFragment;
import lawyerku.mitra.mainfragment.ViewPagerAdapter;
import lawyerku.mitra.preference.GlobalPreference;
import lawyerku.mitra.preference.PrefKey;
import lawyerku.mitra.ui.detailperkara.DetailPerkaraActivity;
import lawyerku.mitra.ui.profilelawyer.DetailProfileActivity;
import lawyerku.mitra.ui.splash.SplashActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.support.constraint.Constraints.TAG;

public class MainActivityCons extends AppCompatActivity implements PerkaraNewFragment.OnFragmentInteractionListener,
        PerkaraOnProgressFragment.OnFragmentInteractionListener,
        PerkaraRejectedFragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener{

  ViewPagerAdapter viewPagerAdapter;
  @BindView(R.id.tb_main)
  Toolbar tbMain;
  @BindView(R.id.tl_lawyer)
  TabLayout tlLawyer;
  @BindView(R.id.vp_lawyer)
  ViewPager vpLawyer;

  private static final int RC_LOC_PERM = 1001;
  protected static final int REQUEST_CHECK_SETTINGS = 0x1;
  private android.location.Location mlocation;
  public static int lawyerId;
  private LocationManager lm;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_cons);
    ButterKnife.bind(this);

    transparentStatusBar();
    displayLocationSettingsRequest(this);

    viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    vpLawyer.setAdapter(viewPagerAdapter);
    tlLawyer.setupWithViewPager(vpLawyer);

    setSupportActionBar(tbMain);
    getSupportActionBar().setTitle("Beranda");

    locationTask();
    getProfile();

  }

  @AfterPermissionGranted(RC_LOC_PERM)
  public void locationTask() {
    String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
    if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
      // Have permission, do the thing!
//            onLaunchCamera();
      Toast.makeText(this, "Access Granted", Toast.LENGTH_SHORT).show();
    } else {
      // Ask for one permission
      EasyPermissions.requestPermissions(this, getString(R.string.ijin_lokasi),
              RC_LOC_PERM, perms);
    }
  }

  private void displayLocationSettingsRequest(Context context) {
    GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build();
    googleApiClient.connect();

    LocationRequest locationRequest = LocationRequest.create();
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    locationRequest.setInterval(10000);
    locationRequest.setFastestInterval(10000 / 2);

    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
    builder.setAlwaysShow(true);

    PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
      @Override
      public void onResult(LocationSettingsResult result) {
        final Status status = result.getStatus();
        switch (status.getStatusCode()) {
          case LocationSettingsStatusCodes.SUCCESS:
            Log.i(TAG, "All location settings are satisfied.");
            break;
          case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

            try {
              // Show the dialog by calling startResolutionForResult(), and check the result
              // in onActivityResult().
              status.startResolutionForResult(MainActivityCons.this, REQUEST_CHECK_SETTINGS);
              getCurrentLocationUser();
            } catch (IntentSender.SendIntentException e) {
              Log.i(TAG, "PendingIntent unable to execute request.");
            }
            break;
          case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
            break;
        }
      }
    });
  }

  private void getCurrentLocationUser() {
    lm = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      if (isNetworkEnabled) {
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.e(TAG, "getCurrentLocationUser: "+mlocation );

      } else if (isGPSEnabled) {
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.e(TAG, "getCurrentLocationUser: "+mlocation );
      }

    }
    Log.e(TAG, "getCurrentLocationUser: "+Manifest.permission.ACCESS_FINE_LOCATION );
  }

  private android.location.LocationListener locationListener = new android.location.LocationListener() {
    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };

  private void transparentStatusBar() {
    if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
      setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
    }
    if (Build.VERSION.SDK_INT >= 19) {
      getWindow().getDecorView().setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    //make fully Android Transparent Status bar
    if (Build.VERSION.SDK_INT >= 21) {
      setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
      getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
  }

  public static void setWindowFlag(Activity activity, final int bits, boolean on) {
    Window win = activity.getWindow();
    WindowManager.LayoutParams winParams = win.getAttributes();
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_profile:
        Intent intent = new Intent(MainActivityCons.this, DetailProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",String.valueOf(lawyerId));
        intent.putExtras(bundle);
        startActivity(intent);
        break;
      case R.id.action_logout:
        logout();
        break;
    }
    return true;
  }


  @Override
  public void onFragmentInteraction(Uri uri) {

  }

  public void detailLawyer(PerkaraModel.Response.Data perkara) {
    Intent i = new Intent(MainActivityCons.this, DetailPerkaraActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("id", String.valueOf(perkara.id));
    i.putExtras(bundle);
    startActivity(i);

  }

  public void lawyerId(int lawyer_id) {
    lawyerId = lawyer_id;
  }

  private void getProfile() {
    String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);
    CompositeSubscription subscription = new CompositeSubscription();

    subscription.add(LawyerkuService.Factory.create().getProfile(accessToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {

              List<PerkaraModel.Response.Data> listPerkara = new ArrayList<>();

              if (response.status >= 200 && response.status < 300) {
//                for (int position = 0; position < response.data.size(); position++){
////                  listPerkara.add(response.data.get(position));
//
//                }

                lawyerId(response.data.id);
//                initListPerkara(listPerkara,lsperkara);
                Log.e(TAG, "searchLawyer: "+response.data );
              } else {
//                        createProjectListener.onError(response.message);
              }
            }, throwable -> {
              int errorCode = ((HttpException) throwable).response().code();
              if (errorCode > 400)
                Log.e(TAG, "getPerkara: "+throwable );
//                        createProjectListener.onError(App.getContext().getString(R.string.error_general));
//                    createProjectListener.hideLoading();
            }));
  }

  public void logout(){
    GlobalPreference.clear();
    Intent intent = new Intent(MainActivityCons.this, SplashActivity.class);
    startActivity(intent);
  }
  boolean doubleBackToExitPressedOnce = false;

  @Override
  public void onBackPressed() {
//    startActivity(new Intent(MainActivityCustomer.this, MainActivityCustomer.class));
//    finish();
    if (doubleBackToExitPressedOnce) {
      //super.onBackPressed();

      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
      startActivity(intent);
      finish();
      System.exit(0);
      return;
    }

    this.doubleBackToExitPressedOnce = true;
    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        doubleBackToExitPressedOnce = false;
      }
    }, 2000);
  }
}

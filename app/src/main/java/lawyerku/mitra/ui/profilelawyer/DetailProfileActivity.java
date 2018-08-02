package lawyerku.mitra.ui.profilelawyer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import lawyerku.mitra.MainActivityCons;
import lawyerku.mitra.R;
import lawyerku.mitra.api.model.LawyerModel;
import lawyerku.mitra.base.BaseActivity;
import lawyerku.mitra.base.BaseApplication;
import lawyerku.mitra.preference.GlobalPreference;
import lawyerku.mitra.preference.PrefKey;

public class DetailProfileActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener{

  private static final String TAG = "DetailProfile";
  @Inject
  DetailProfilePresenter presenter;

  @BindView(R.id.img_topup)
  ImageButton imgTopup;
  @BindView(R.id.btn_save)
  Button btnSave;
  @BindView(R.id.tv_name)
  TextView txtNama;
  @BindView(R.id.et_first_name)
  TextInputEditText txtFirstName;
  @BindView(R.id.et_last_name)
  TextInputEditText txtLastName;
  @BindView(R.id.et_address)
  TextInputEditText txtAddress;
  @BindView(R.id.et_idcard)
  TextInputEditText txtIdcard;
  @BindView(R.id.et_csmap)
  TextInputEditText txtMap;
  @BindView(R.id.et_phone)
  TextInputEditText txtPhone;
  @BindView(R.id.et_email)
  TextInputEditText txtEmail;
  @BindView(R.id.rel_map)
  RelativeLayout relMap;
  @BindView(R.id.chkIndonesia)
  CheckBox chkIndonesia;
  @BindView(R.id.chkInggris)
  CheckBox chkInggris;
  @BindView(R.id.chkPidana)
          CheckBox chkPidana;
  @BindView(R.id.chkPerdata)
          CheckBox chkPerdata;


  CharSequence[] bahasa = {"Indonesia", "Inggris"};
  CharSequence[] bidang = {"Pidana", "Perdata"};


  public static int id;
  public static String accessToken;
  public static String address2;
  public static double latitudenew;
  public static double longitudenew;
  public static String firstname;
  public static String lastname;
  public static int jobskillnew;
  public static int languageskillnew;

  public static List<LawyerModel.Data> dataLawyer;

  private LocationManager lm;
  private android.location.Location mlocation;
  private GoogleMap mMap;

  private boolean mapMode = false;

  private double latitude = 0;
  private double longitude = 0;
  SupportMapFragment mapFragment;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_cons);
    ButterKnife.bind(this);
    transparentStatusBar();

    getCurrentLocationUser();

    mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    Bundle bundle = getIntent().getExtras();
    id = Integer.parseInt(bundle.getString("id"));

    accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);
    initProfile(accessToken,id);
  }



  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getAppComponent()
            .plus(new DetailProfileActivityModule(this))
            .inject(this);
  }

  private void transparentStatusBar() {
    if (VERSION.SDK_INT >= 19 && VERSION.SDK_INT < 21) {
      setWindowFlag(this, LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
    }
    if (VERSION.SDK_INT >= 19) {
      getWindow().getDecorView().setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    //make fully Android Transparent Status bar
    if (VERSION.SDK_INT >= 21) {
      setWindowFlag(this, LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
      getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
  }

  public static void setWindowFlag(Activity activity, final int bits, boolean on) {
    Window win = activity.getWindow();
    LayoutParams winParams = win.getAttributes();
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
  }

  @OnClick(R.id.img_topup)
  public void onImgTopupClicked() {
    LayoutInflater li = LayoutInflater.from(this);
    View promptsView = li.inflate(R.layout.redeem_saldo, null);

    Builder alertDialogBuilder = new Builder(
        this);

    // set prompts.xml to alertdialog builder
    alertDialogBuilder.setView(promptsView);

    final EditText userInput = (EditText) promptsView
        .findViewById(R.id.txtReedemSaldo);
//        userInput.setText(String.valueOf(barang.getStokBarang()));
    // set dialog message
    alertDialogBuilder
        .setCancelable(false)
        .setPositiveButton("Reedem",
            new OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {

//                                barang.setStokBarang(Integer.valueOf(userInput.getText().toString()));
//                                barang.setUpdateTerakhir(System.currentTimeMillis());
//                                showLoading(true,holder.viewProgress);
//                                updateStok(barang,holder);
                Toast.makeText(DetailProfileActivity.this,
                    "Saldo Berhasil Dikirim, silahkan tunggu konfirmasi admin", Toast.LENGTH_LONG)
                    .show();

              }
            })
        .setNegativeButton("Cancel",
            new OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
              }
            });

    // create alert dialog
    AlertDialog alertDialog = alertDialogBuilder.create();

    // show it
    alertDialog.show();
  }



  @OnClick(R.id.btn_save)
  public void onBtnSaveClicked() {
//    Toast.makeText(this, "Profile Berhasil Disimpan", Toast.LENGTH_SHORT).show();
//    Intent i = new Intent(DetailProfileActivity.this, MainActivityCons.class);
//    startActivity(i);
    validate();
  }

  private void initProfile(String accessToken,int id) {
    presenter.getProfile(accessToken,id);
  }

  public void showProfile(List<LawyerModel.Data> data) {
    Log.e("profileLawyer", "showProfile: "+data );

    dataLawyer = data;
    txtNama.setText(data.get(0).name);
    txtAddress.setText(data.get(0).address_1);
    txtIdcard.setText(data.get(0).idnumber);
    txtPhone.setText(data.get(0).cellphone1);
    txtEmail.setText(data.get(0).user.email);
    if(data.get(0).address_2 != null){
      txtMap.setText(data.get(0).address_2);
    }
    if(!data.get(0).jobskills.isEmpty()){
        if(data.get(0).jobskills.get(0).id == 1){
            chkPidana.setChecked(true);
        }
        if(data.get(0).jobskills.get(0).id == 2){
            chkPerdata.setChecked(true);
        }
    }
      if(!data.get(0).languageskills.isEmpty()) {
          if (data.get(0).languageskills.get(0).id == 1) {
              chkIndonesia.setChecked(true);
          }
          if (data.get(0).languageskills.get(0).id == 2) {
              chkInggris.setChecked(true);
          }
      }

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
    Log.e(TAG, "getCurrentLocationUser: "+mlocation );
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


  @Override
  public void onMapReady(GoogleMap googleMap) {

    mMap = googleMap;

//        LatLng indonesia = new LatLng(-7.803249, 110.3398253);
    LatLng indonesia = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
    Log.e(TAG, "initMap: "+indonesia );
//    initMap(indonesia);
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 16));
    mMap.setOnCameraIdleListener(this);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    mMap.setMyLocationEnabled(true);

//    mMap.addMarker(new MarkerOptions()
//            .position(mMap.getCameraPosition().target)
//            .title("Marker"));
  }

//

  @Override
  public void onCameraIdle() {
    LatLng latLng = mMap.getCameraPosition().target;
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {

  }



  @OnClick(R.id.et_csmap)
  void showPeta(){
    relMap.setVisibility(View.VISIBLE);
    mapMode = true;
//    menuDone.setVisible(false);
  }

  @OnClick(R.id.btnSimpanMap)
  void hideMap(){
    relMap.setVisibility(View.GONE);
    mapMode = false;
//    menuDone.setVisible(true);
    LatLng latLng = mMap.getCameraPosition().target;
    Log.e(TAG, "hideMap: "+latLng );
    getAddress(latLng);
//    initMap(latLng);
  }



  private void getAddress(LatLng latLng) {

    Geocoder geocoder;
    List<Address> addresses = null;
    geocoder = new Geocoder(this, Locale.getDefault());

    try {
      addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

    } catch (IOException e) {
      e.printStackTrace();
    }
    Log.e(TAG, "getAddress: "+addresses.get(0));
    String fulladdress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String city = addresses.get(0).getSubAdminArea();
    String state = addresses.get(0).getAdminArea();
    String country = addresses.get(0).getCountryName();
    String postalCode = addresses.get(0).getPostalCode();
    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

    String [] add = fulladdress.split(",");

    txtMap.setText(fulladdress);

    address2 = fulladdress;
    latitudenew = latLng.latitude;
    longitudenew = latLng.longitude;

  }

  public void validate(){
    LawyerModel.DataUpdate lawyer = new LawyerModel.DataUpdate();

    txtFirstName.setError(null);
    txtLastName.setError(null);
    txtEmail.setError(null);
    txtAddress.setError(null);
    txtMap.setError(null);
    txtIdcard.setError(null);
    txtPhone.setError(null);

    boolean cancel = false;
    View focusView = null;


    if(TextUtils.isEmpty(txtEmail.getText().toString())){
      txtEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
      focusView = txtEmail;
      cancel = true;
    }
    if(TextUtils.isEmpty(txtFirstName.getText().toString())){
      txtFirstName.setError(this.getBaseContext().getString(R.string.error_empty_name));
      focusView = txtFirstName;
      cancel = true;
    }
    if(TextUtils.isEmpty(txtLastName.getText().toString())){
      txtLastName.setError(this.getBaseContext().getString(R.string.error_empty_name));
      focusView = txtLastName;
      cancel = true;
    }
//    if(isValidateEmail(txtEmail.getText())){
//      txtEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
//      focusView = txtEmail;
//      cancel = true;
//    }
    if(TextUtils.isEmpty(txtAddress.getText().toString())){
      txtAddress.setError(this.getBaseContext().getString(R.string.error_empty_password));
      focusView = txtAddress;
      cancel = true;
    }

    if(txtMap.getText().toString().equals("Pilih Alamat Dari Peta")){
      txtMap.setError(this.getBaseContext().getString(R.string.error_empty_map));
      focusView = txtMap;
      cancel = true;
    }
    if(TextUtils.isEmpty(txtAddress.getText().toString())){
      txtAddress.setError(this.getBaseContext().getString(R.string.error_empty_address));
      focusView = txtAddress;
      cancel = true;
    }
    if(TextUtils.isEmpty(txtPhone.getText().toString())){
      txtPhone.setError(this.getBaseContext().getString(R.string.error_empty_phone));
      focusView = txtPhone;
      cancel = true;
    }
    if (cancel) {
      focusView.requestFocus();
    }else{

      if(!TextUtils.isEmpty(txtFirstName.getText().toString())){
        firstname = txtFirstName.getText().toString();
      }
      if(!TextUtils.isEmpty(txtLastName.getText().toString())){
         lastname = txtLastName.getText().toString();
      }
      if(TextUtils.isEmpty(txtFirstName.getText().toString())){
        firstname = dataLawyer.get(0).name;
      }
      if(TextUtils.isEmpty(txtLastName.getText().toString())){
        lastname = dataLawyer.get(0).user.username;
      }
      if(chkIndonesia.isChecked()){
        languageskillnew = 1;
      }
      if(chkInggris.isChecked()){
        languageskillnew = 2;
      }
      if(chkPerdata.isChecked()){
        jobskillnew = 2;
      }
      if(chkPidana.isChecked()){
        jobskillnew = 1;
      }

      lawyer.first_name = firstname;
      lawyer.last_name = lastname;
      lawyer.email = txtEmail.getText().toString();
      lawyer.address_1 = txtAddress.getText().toString();
      lawyer.address_2 = txtMap.getText().toString();
      lawyer.latitude = String.valueOf(latitudenew);
      lawyer.longitude = String.valueOf(longitudenew);
      lawyer.cellphone1 = txtPhone.getText().toString();
      lawyer.level = dataLawyer.get(0).level;
      lawyer.smartphone = dataLawyer.get(0).smartphone;
      if(dataLawyer.get(0).rateMin == null){
        lawyer.rateMin = "1.00";
      }
      if(dataLawyer.get(0).rateMax == null){
        lawyer.rateMax = "0.00";
      }
      if(dataLawyer.get(0).rateMin != null){
        lawyer.rateMin = dataLawyer.get(0).rateMin;
      }
      if(dataLawyer.get(0).rateMax != null){
        lawyer.rateMax = dataLawyer.get(0).rateMax;
      }

      lawyer.id = dataLawyer.get(0).id;
      lawyer.idnumber = dataLawyer.get(0).idnumber;
      lawyer.jobskill = jobskillnew;
      lawyer.languageskill = languageskillnew;

      Log.e(TAG, "validate: "+lawyer );

      presenter.update(lawyer,accessToken);

    }

  }

  @OnCheckedChanged(R.id.chkIndonesia)
  public void checkBahasaIndonesia(){
    chkInggris.setChecked(false);
//    chkIndonesia.setChecked(true);
  }
  @OnCheckedChanged(R.id.chkInggris)
  public void checkBahasaInggris(){
    chkIndonesia.setChecked(false);
//    chkInggris.setChecked(true);
  }
  @OnCheckedChanged(R.id.chkPerdata)
  public void checkBidangPerdata(){
    chkPidana.setChecked(false);
//    chkPerdata.setChecked(true);
  }
  @OnCheckedChanged(R.id.chkPidana)
  public void checkBidangPidana(){
    chkPerdata.setChecked(false);
//    chkPidana.setChecked(true);
  }

  public void showMain() {
    Toast.makeText(this, "Profile Berhasil Disimpan", Toast.LENGTH_SHORT).show();
    Intent i = new Intent(DetailProfileActivity.this, MainActivityCons.class);
    startActivity(i);
  }
}

package lawyerku.mitra.ui.detailperkara;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.mitra.MainActivityCons;
import lawyerku.mitra.R;
import lawyerku.mitra.api.model.PerkaraModel;
import lawyerku.mitra.base.BaseActivity;
import lawyerku.mitra.base.BaseApplication;
import lawyerku.mitra.ui.MessageActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class DetailPerkaraActivity extends BaseActivity implements OnCameraIdleListener,
    OnMapReadyCallback {

    @Inject
    DetailPerkaraPresenter presenter;

    @BindView(R.id.img_msg)
    ImageView imgMsg;

    @BindView(R.id.img_maps)
    ImageView imgMaps;

    @BindView(R.id.tv_nama_customer)
    TextView txtNamaCustomer;

    @BindView(R.id.tv_bidang_hukum)
    TextView txtbidangHukum;

    @BindView(R.id.tv_desc)
    TextView txtdescription;

    @BindView(R.id.tv_nama_lawyer)
    TextView txtnamaLawyer;

    @BindView(R.id.tv_telp)
    TextView txttelpLawyer;

    @BindView(R.id.tv_hp)
    TextView txthpLawyer;

    private GoogleMap mMap;
    private LocationManager lm;
    private Location mlocation;
    private double latitude = 0;
    private double longitude = 0;

    private static final int RC_LOC_PERM = 1001;
    public static int id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_perkara_cons);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        Log.e("detailperkara", "onCreate: "+bundle );
        id = Integer.parseInt(bundle.getString("id"));
        locationTask();
        transparentStatusBar();

        presenter.getProject(id);
    }

    @AfterPermissionGranted(RC_LOC_PERM)
    public void locationTask() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Have permission, do the thing!
//            onLaunchCamera();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.ijin_lokasi),
                    RC_LOC_PERM, perms);
        }
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new DetailPerkaraActivityModule(this))
                .inject(this);
    }

    private void transparentStatusBar(){
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
    public void onCameraIdle() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng indonesia = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
        Log.e("map", "initMap: " + indonesia);
//        initMap(indonesia);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 16));
        mMap.setOnCameraIdleListener(this);
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.addMarker(new MarkerOptions()
            .position(mMap.getCameraPosition().target)
            .title("Marker"));


    }

    private void initMap(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;

        String url =
            "http://maps.googleapis.com/maps/api/staticmap?zoom=17&size=800x400&maptype=roadmap%20&markers=color:red%7Clabel:S%7C"
                + latitude + "," + longitude + "+&sensor=false";
        Log.d("initmap", "url = " + url);
        Glide.with(this)
            .load(url)
            .placeholder(R.color.colorShadow2)
            .centerCrop()
            .dontAnimate()
            .into(imgMaps);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick(R.id.img_msg)
    public void onImgMsgClicked() {
        Intent intent = new Intent(DetailPerkaraActivity.this, MessageActivity.class);
        startActivity(intent);
    }



    @OnClick(R.id.img_maps)
    public void onViewClicked() {
    }

    public void initProject(List<PerkaraModel.Response.Data> data) {
        Log.e("DetailPerkara", "initProject: "+data );
        LatLng latLng = new LatLng(data.get(0).gps_latitude,data.get(0).gps_longitud);
        initMap(latLng);
        txtNamaCustomer.setText(data.get(0).customer.name);
        txtbidangHukum.setText(data.get(0).jobskill.name);
        txtdescription.setText(data.get(0).description);
        txtnamaLawyer.setText(data.get(0).lawyer.lawyername);
        txthpLawyer.setText(data.get(0).lawyer.lawyerPhone2);
        txttelpLawyer.setText(data.get(0).lawyer.lawyerPhone1);
    }

    @OnClick(R.id.btn_approve)
    public void approvePerkara(){
        presenter.approveProject(id,true);
    }

    @OnClick(R.id.btn_reject)
    public void rejectPerkara(){
        presenter.approveProject(id,false);
    }

    public void gotoMain(boolean approve) {
        if(approve){
            Toast.makeText(this, "Project Berhasil di Setujui, SIlahkan tunggu konfirmasi admin", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Project Berhasil di Batalkan", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(DetailPerkaraActivity.this, MainActivityCons.class);
        startActivity(intent);
    }
}
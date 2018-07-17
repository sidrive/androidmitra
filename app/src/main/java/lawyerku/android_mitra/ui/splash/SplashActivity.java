package lawyerku.android_mitra.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import javax.inject.Inject;

import lawyerku.android_mitra.MainActivityCons;
import lawyerku.android_mitra.R;
import lawyerku.android_mitra.base.BaseActivity;
import lawyerku.android_mitra.base.BaseApplication;
import lawyerku.android_mitra.preference.GlobalPreference;
import lawyerku.android_mitra.preference.PrefKey;
import lawyerku.android_mitra.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity {

  @Inject SplashPresenter presenter;


  private static int SPLASH_TIME_OUT = 3000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_cons);
    transparentStatusBar();
    initMain();
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getAppComponent()
            .plus(new SplashActivityModule(this))
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

  private void initMain() {
    new Handler().postDelayed(new Runnable() {

      /*
       * Showing splash screen with a timer. This will be useful when you
       * want to show case your app logo / company
       */

      @Override
      public void run() {
        // This method will be executed once the timer is over
        // Start your app main activity

        initActivity();

        // close this activity
        finish();
      }
    }, SPLASH_TIME_OUT);

  }

  public void initActivity(){

    boolean loggedIn = GlobalPreference.read(PrefKey.loggedIn,Boolean.class);

    if(!loggedIn){
//
      Intent i = new Intent(SplashActivity.this, LoginActivity.class);
      startActivity(i);
      Log.e("init", "initActivity: 1" );
    }
    if(loggedIn){
//
      Intent i = new Intent(SplashActivity.this, MainActivityCons.class);
      startActivity(i);
      Log.e("init", "initActivity: 2" );
    }


  }


}

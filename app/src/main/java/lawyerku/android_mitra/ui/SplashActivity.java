package lawyerku.android_mitra.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import lawyerku.android_mitra.MainActivity;
import lawyerku.android_mitra.R;

public class SplashActivity extends AppCompatActivity {


  private static int SPLASH_TIME_OUT = 3000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_cons);
    transparentStatusBar();
    initMain();
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
        Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
        startActivity(i);

        // close this activity
        finish();
      }
    }, SPLASH_TIME_OUT);

  }


}

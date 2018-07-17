package lawyerku.android_mitra.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_mitra.R;
import lawyerku.android_mitra.ui.login.LoginActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password_cons);
    ButterKnife.bind(this);
    transparentStatusBar();

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

  @OnClick(R.id.btn_reset)
  public void onBtnResetClicked() {
    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.tv_back)
  public void onTvBackClicked() {
    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
    startActivity(intent);
  }
}

package lawyerku.mitra.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.mitra.MainActivityCons;
import lawyerku.mitra.R;
import lawyerku.mitra.api.model.CredentialModel;
import lawyerku.mitra.base.BaseActivity;
import lawyerku.mitra.base.BaseApplication;
import lawyerku.mitra.ui.ForgotPasswordActivity;
import lawyerku.mitra.ui.register.RegisterActivity;

public class LoginActivity extends BaseActivity {

  @Inject
  LoginPresenter presenter;

  @BindView(R.id.et_email)
  TextView txtEmail;

  @BindView(R.id.et_password)
  TextView txtPassword;

  @BindView(R.id.view_progress)
  LinearLayout viewProgress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_cons);
    ButterKnife.bind(this);
    transparentStatusBar();
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getAppComponent()
            .plus(new LoginActivityModule(this))
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

  @OnClick(R.id.tv_forgot)
  public void onTvForgotClicked() {
    Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.tv_register_ask)
  public void onTvRegisterClicked(){
    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.btn_login)
  public void showLogin(){

    showLoading(true);

    CredentialModel.Request credential = new CredentialModel.Request();
    credential.email = txtEmail.getText().toString();
    credential.password = txtPassword.getText().toString();
    credential.role_id = "2";

    presenter.validateLogin(credential);

//        Intent i = new Intent(LoginActivity.this, MainActivityCustomer.class);
//        startActivity(i);
  }

    public void loginProses(){
        Intent i = new Intent(LoginActivity.this, MainActivityCons.class);
        startActivity(i);
    }

  public void showLoading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }
}

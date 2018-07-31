package lawyerku.mitra.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.mitra.R;
import lawyerku.mitra.api.model.CredentialModel;
import lawyerku.mitra.base.BaseActivity;
import lawyerku.mitra.base.BaseApplication;
import lawyerku.mitra.ui.login.LoginActivity;

public class RegisterActivity extends BaseActivity {

  @Inject
  RegisterPresenter presenter;

  @BindView(R.id.et_username)
  TextInputEditText txtUsername;

  @BindView(R.id.et_email)
  TextInputEditText txtEmail;

  @BindView(R.id.et_password_reg)
  TextInputEditText txtPassword;

  @BindView(R.id.et_password_confirm)
  TextInputEditText txtCpassword;

  @BindView(R.id.et_first_name)
  TextInputEditText txtFirstName;

  @BindView(R.id.et_last_name)
  TextInputEditText txtLastName;

  @BindView(R.id.et_address)
  TextInputEditText txtAddress;

  @BindView(R.id.et_phone_number)
  TextInputEditText txtPhone;

  @BindView(R.id.et_idnumber)
  TextInputEditText txtIdnumber;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration_cons);
    ButterKnife.bind(this);
    transparentStatusBar();

//    txtUsername.setText("wirosableng");
//    txtEmail.setText("wiro@sbaleng.com");
//    txtPassword.setText("123456");
//    txtCpassword.setText("123456");
//    txtFirstName.setText("wiro");
//    txtLastName.setText("sableng");
//    txtAddress.setText("gunung kelud");
//    txtPhone.setText("083123131");
//    txtIdnumber.setText("321451413131");

  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getAppComponent()
            .plus(new RegisterActivityModule(this))
            .inject(this);
  }

  @OnClick(R.id.btn_sign_up)
  public void onViewClicked() {
    validate();
  }

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

  private void validate(){
    CredentialModel.Request credential = new CredentialModel.Request();

    txtUsername.setError(null);
    txtEmail.setError(null);
    txtPassword.setError(null);
    txtCpassword.setError(null);
    txtFirstName.setError(null);
    txtLastName.setError(null);
    txtAddress.setError(null);
    txtPhone.setError(null);
    txtIdnumber.setError(null);

    boolean cancel = false;
    View focusView = null;

    if(TextUtils.isEmpty(txtUsername.getText().toString())){
      txtUsername.setError(this.getBaseContext().getString(R.string.error_empty_username));
      focusView = txtUsername;
      cancel = true;
    }
    if(TextUtils.isEmpty(txtEmail.getText().toString())){
      txtEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
      focusView = txtEmail;
      cancel = true;
    }
    if(isValidateEmail(txtEmail.getText())){
      txtEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
      focusView = txtEmail;
      cancel = true;
    }
    if(TextUtils.isEmpty(txtPassword.getText().toString())){
      txtPassword.setError(this.getBaseContext().getString(R.string.error_empty_password));
      focusView = txtPassword;
      cancel = true;
    }
    if(TextUtils.isEmpty(txtCpassword.getText().toString())){
      txtCpassword.setError(this.getBaseContext().getString(R.string.error_empty_cpassword));
      focusView = txtCpassword;
      cancel = true;
    }
    if(!txtPassword.getText().toString().equals(txtCpassword.getText().toString())){
      txtCpassword.setError(this.getBaseContext().getString(R.string.error_empty_cpassword));
      focusView = txtCpassword;
      cancel = true;
      Log.e("password", "validate: "+txtPassword.getText()+" & "+txtCpassword.getText() );
    }
    if(TextUtils.isEmpty(txtFirstName.getText().toString())){
      txtFirstName.setError(this.getBaseContext().getString(R.string.error_empty_name));
      focusView = txtFirstName;
      cancel = true;
    }
    if(TextUtils.isEmpty(txtLastName.getText().toString())){
      txtLastName.setError(this.getBaseContext().getString(R.string.error_empty_username));
      focusView = txtLastName;
      cancel = true;
    }
    if(TextUtils.isEmpty(txtIdnumber.getText().toString())){
      txtIdnumber.setError(this.getBaseContext().getString(R.string.error_empty_nik));
      focusView = txtIdnumber;
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
      credential.username = txtUsername.getText().toString();
      credential.email = txtEmail.getText().toString();
      credential.password = txtPassword.getText().toString();
      credential.c_password = txtCpassword.getText().toString();
      credential.first_name = txtFirstName.getText().toString();
      credential.last_name = txtLastName.getText().toString();
      credential.address = txtAddress.getText().toString();
      credential.cellphone_number = txtPhone.getText().toString();
      credential.role_id = "2";
      credential.cellphone_number_2 = "0";
      credential.nik = txtIdnumber.getText().toString();



      presenter.register(credential);

    }

  }

  public boolean isValidateEmail(CharSequence email){
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    if (!TextUtils.isEmpty(email)){
      if (matcher.matches()){
        return false;
      }

    }
    return true;
  }

  public void logogin(){
    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
    startActivity(i);
  }
}

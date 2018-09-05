package fr.esgi.moc.moviemeeting.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.esgi.moc.moviemeeting.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginField)   EditText loginField;
    @BindView(R.id.pswdField)    EditText pswdField;
    @BindView(R.id.errorMessage) TextView errorMessage;

    private final String TAG = this.getClass().getName();
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonCliked() {
        String login = loginField.getText().toString();
        String pswd = pswdField.getText().toString();
        String msg = presenter.userVerification(login, pswd);
        if (msg.equals("OK")) {
            goToApp();
        } else {
            Log.i(TAG, msg);
            errorMessage.setText(msg);
        }
    }

    private void goToApp() {
        Log.d(TAG, "Entering th app.");
        // TODO acces a l'application
    }
}

package fr.esgi.moc.moviemeeting.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.movies.MoviesListActivity;
import fr.esgi.moc.moviemeeting.navigation.NavigationActivity;
import fr.esgi.moc.moviemeeting.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginField)   EditText loginField;
    @BindView(R.id.pswdField)    EditText pswdField;
    @BindView(R.id.errorMessage) TextView errorMessage;

    @BindView(R.id.loginButton)
    Button loginButton;

    @BindView(R.id.buttonToRegister)
    Button registerButton;


    @BindView(R.id.loginProgressBar)
    ProgressBar loginProgressBar;



    private final String TAG = this.getClass().getName();
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);


        hideProgress();
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClicked() {
        String login = loginField.getText().toString();
        String pswd = pswdField.getText().toString();
        presenter.userVerification(login, pswd);
    }

    @OnClick(R.id.buttonToRegister)
    public void onRegisterClick() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // appelé par le presenter après connexion à l'api
    public void onLoginSuccess() {
        goToApp();
    }

    // appelé par le presenter après connexion à l'api
    public void onLoginFail(String msg) {
        Log.i(TAG, msg);
        errorMessage.setText(msg);
    }

    public void goToApp() {
        Log.d(TAG, "Entering the app.");
        // TODO acces a l'application
        Intent myIntent = new Intent(this, NavigationActivity.class);

        this.startActivity(myIntent);
    }

    public void showProgress() {
        loginButton.setVisibility(View.GONE);
        registerButton.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.VISIBLE);
    }


    public void hideProgress() {
        loginProgressBar.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
    }
}

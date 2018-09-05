package fr.esgi.moc.moviemeeting.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.esgi.moc.moviemeeting.R;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registerLoginField)      EditText registerLoginField;
    @BindView(R.id.registerPswdField)       EditText registerPswdField;
    @BindView(R.id.registerPswdRepeatField) EditText registerPswdRepeatField;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerPresenter = new RegisterPresenter(this);
    }

    @OnClick(R.id.registerButton)
    public void onRegisterButtonClick() {
        String chosenLogin = registerLoginField.getText().toString();
        String chosenPswd = registerPswdField.getText().toString();
        String pswdRepeat = registerPswdRepeatField.getText().toString();
        registerPresenter.userSubscription(chosenLogin, chosenPswd, pswdRepeat);
    }

    // appelé par le presenter après connexion à l'api
    public void onRegisterFail(String msg) {
        // TODO afficher le message d'erreur
    }

    // appelé par le presenter après connexion à l'api
    public void onRegisterSuccess() {
        // TODO retourner sur la page de login
    }
}

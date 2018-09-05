package fr.esgi.moc.moviemeeting.login;

public class LoginPresenter {

    private LoginActivity loginActivity;

    LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void userVerification(String login, String pswd) {
        // TODO verifier champs (champs vides)
        // TODO envoyer à l'api
        loginActivity.onLoginSuccess();
    }
}

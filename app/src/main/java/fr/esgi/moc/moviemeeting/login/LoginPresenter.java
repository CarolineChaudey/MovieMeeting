package fr.esgi.moc.moviemeeting.login;

public class LoginPresenter {

    private LoginActivity loginActivity;

    public LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public String userVerification(String login, String pswd) {
        // TODO verifier champs (champs vides)
        /// TODO envoyer à l'api
        return "OK";
    }
}

package fr.esgi.moc.moviemeeting.register;

public class RegisterPresenter {

    private RegisterActivity registerActivity;

    RegisterPresenter(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    public void userSubscription(String login, String pswd, String pswdRepeat) {
        if (!pswd.equals(pswdRepeat)) {
            registerActivity.onRegisterFail("Passwords must be identic.");
        }
        // TODO relier à l'api
        registerActivity.onRegisterSuccess();
    }
}

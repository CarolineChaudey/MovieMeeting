package fr.esgi.moc.moviemeeting.login;

import android.content.Context;
import android.util.Log;

import fr.esgi.moc.moviemeeting.data.SharedPreferencesManager;
import fr.esgi.moc.moviemeeting.data.api.MovieMeetingApiProvider;
import fr.esgi.moc.moviemeeting.data.dtos.Credentials;
import fr.esgi.moc.moviemeeting.data.dtos.Token;
import fr.esgi.moc.moviemeeting.data.dtos.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private static final String TAG = "LoginPresenter";

    private LoginActivity loginActivity;

    private MovieMeetingApiProvider provider = new MovieMeetingApiProvider();

    LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void userVerification(String login, String pswd) {
        // TODO verifier champs (champs vides)
        // TODO envoyer à l'api
        //loginView.showProgress();

        Credentials credentials = new Credentials(login, pswd);
        Call<User> call = provider.connect(credentials);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() >= 500) {
                    //onServerError("Server error.");
                } else if (response.code() >= 400) {
                   //onUsernameError("Wrong credentials.");
                } else {
                    User user = response.body();

                    if (user != null) {
                       // Log.i(TAG, "user = " + user.toString());
                        Token token = new Token(user.getToken());
                        saveUser(user);
                        onSuccess();
                    } else {
                     //   onServerError("Couldn't join server.");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, t.getMessage());
               // onServerError("Unknown error.");
            }
        });
    }

    public void saveUser(User user) {
        Context ctxt = (Context) this.loginActivity;
        SharedPreferencesManager.saveUserData(ctxt, user);
    }

    public void onSuccess() {
        loginActivity.goToApp();
    }
}

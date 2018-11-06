package fr.esgi.moc.moviemeeting.data;

import android.content.Context;
import android.content.SharedPreferences;



import fr.esgi.moc.moviemeeting.data.dtos.User;


public class SharedPreferencesManager {

    public static final String TAG = "SharedPrefManager";
    public static final String BASE_KEY = "com.esgi.mypunch";
    public static final String CONNEXION_TOKEN = "com.esgi.mypunch.token";
    public static final String ID = "com.esgi.mypunch.userId";
    public static final String PSEUDO = "com.esgi.mypunch.pseudo";


    public static User getUser(Context ctxt) {
        SharedPreferences prefs = ctxt.getSharedPreferences(SharedPreferencesManager.BASE_KEY, Context.MODE_PRIVATE);

        int id = prefs.getInt(ID, 0);
        String token = prefs.getString(CONNEXION_TOKEN, null);
        String pseudo = prefs.getString(PSEUDO, null);

        return new User(id, pseudo, token);
    }

    public static void saveUserData(Context ctxt, User user) {
        SharedPreferences prefs = ctxt.getSharedPreferences(SharedPreferencesManager.BASE_KEY, Context.MODE_PRIVATE);

        prefs.edit().putString(SharedPreferencesManager.CONNEXION_TOKEN, user.getToken()).apply();
        prefs.edit().putInt(SharedPreferencesManager.ID, user.getId()).apply();
        prefs.edit().putString(SharedPreferencesManager.PSEUDO, user.getPseudo()).apply();
    }

    public static void eraseUserData(Context ctxt) {
        SharedPreferences prefs = ctxt.getSharedPreferences(SharedPreferencesManager.BASE_KEY, Context.MODE_PRIVATE);

        prefs.edit().putString(SharedPreferencesManager.CONNEXION_TOKEN, null).apply();
        prefs.edit().putString(SharedPreferencesManager.ID, null).apply();
        prefs.edit().putString(SharedPreferencesManager.PSEUDO, null).apply();
;
    }
}

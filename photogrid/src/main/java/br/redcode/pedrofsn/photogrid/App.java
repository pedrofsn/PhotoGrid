package br.redcode.pedrofsn.photogrid;

import android.app.Application;
import android.content.Context;

/**
 * Created by User on 28/07/2016.
 */
public class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        App.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}

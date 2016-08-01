package br.redcode.pedrofsn.photogrid.sample;

import android.app.Application;
import android.content.Context;

/**
 * Created by pedrofsn on 01/08/2016.
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
        setContext(this);
    }
}
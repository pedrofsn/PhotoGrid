package br.redcode.pedrofsn.photogrid;

import android.util.Log;

import java.util.List;

/**
 * Created by pedrofsn on 28/07/2016.
 */
public class Utils {

    public static void log(String message) {
        log(Constantes.TAG, message);
    }

    public static void log(String aaa, String message) {
        if (message != null) {
            Log.e(aaa, message);
        }
    }

    public static boolean isNullOrEmpty(Object o) {
        if (o != null) {
            if (o instanceof String) {
                return ((String) o).isEmpty();
            } else if (o instanceof CharSequence) {
                return o.toString().isEmpty();
            } else if (o instanceof List && ((List) o).size() == 0) {
                return true;
            }
            return false;
        }
        return true;
    }
}

package br.redcode.pedrofsn.photogrid.sample.utils;

import java.util.List;

/**
 * Created by pedrofsn on 28/07/2016.
 */
public class Utils {

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

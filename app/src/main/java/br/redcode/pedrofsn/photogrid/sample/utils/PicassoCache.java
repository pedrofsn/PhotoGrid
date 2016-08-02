package br.redcode.pedrofsn.photogrid.sample.utils;

import android.content.Context;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import br.redcode.pedrofsn.photogrid.sample.App;

/**
 * Created by pedrofsn on 09/06/2016.
 */
public class PicassoCache {

    private static Picasso instance = null;

    private PicassoCache(Context context) {

        OkHttp3Downloader client = new OkHttp3Downloader(context, Integer.MAX_VALUE);

        instance = new Picasso.Builder(context)
                .downloader(client)
                .loggingEnabled(true)
                .build();
    }

    public static Picasso getInstance(Context context) {

        if (Utils.isNullOrEmpty(instance)) {
            new PicassoCache(context);
            return instance;
        }

        return instance;
    }

    public static void carregar(Object object, ImageView imageView) {
        if (!Utils.isNullOrEmpty(object)) {

            if (object instanceof File) {
                PicassoCache.getInstance(App.getContext())
                        .load(((File) object))
                        .error(getDrawableError())
                        .placeholder(getDrawablePlaceHolder())
                        .priority(getPriority())
                        .into(imageView);

            } else if (object instanceof String) {
                PicassoCache.getInstance(App.getContext())
                        .load(((String) object))
                        .error(getDrawableError())
                        .placeholder(getDrawablePlaceHolder())
                        .priority(getPriority())
                        .into(imageView);
            }
        }
    }

    private static Picasso.Priority getPriority() {
        return Picasso.Priority.NORMAL;
    }

    private static int getDrawablePlaceHolder() {
        return android.R.drawable.progress_indeterminate_horizontal;
    }

    private static int getDrawableError() {
        return android.R.drawable.star_on;
    }

}

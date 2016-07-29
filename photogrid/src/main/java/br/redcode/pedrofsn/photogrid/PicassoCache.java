package br.redcode.pedrofsn.photogrid;

import android.content.Context;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by pedrofsn on 09/06/2016.
 */
public class PicassoCache {

    private static Picasso picassoInstance = null;

    private PicassoCache(Context context) {

        OkHttp3Downloader client = new OkHttp3Downloader(context, Integer.MAX_VALUE);

        picassoInstance = new Picasso.Builder(context)
                .downloader(client)
                .loggingEnabled(true)
                .build();
    }

    public static Picasso getPicassoInstance(Context context) {

        if (Utils.isNullOrEmpty(picassoInstance)) {
            new PicassoCache(context);
            return picassoInstance;
        }

        return picassoInstance;
    }

    public static void carregar(Object object, ImageView imageView) {
        if (!Utils.isNullOrEmpty(object)) {

            if (object instanceof File) {
                PicassoCache.getPicassoInstance(App.getContext())
                        .load(((File) object))
                        .error(getDrawableError())
                        .placeholder(getDrawablePlaceHolder())
                        .priority(getPriority())
                        .into(imageView);

            } else if (object instanceof String) {
                PicassoCache.getPicassoInstance(App.getContext())
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
        return android.R.drawable.checkbox_on_background;
    }

    private static int getDrawableError() {
        return android.R.drawable.star_on;
    }

}

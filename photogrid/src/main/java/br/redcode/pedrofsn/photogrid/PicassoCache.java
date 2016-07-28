package br.redcode.pedrofsn.photogrid;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by pedrofsn on 09/06/2016.
 */
public class PicassoCache {

    private static Picasso picassoInstance = null;

    private PicassoCache(Context context) {

        Downloader downloader = new OkHttpDownloader(context, Integer.MAX_VALUE);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(downloader);

        picassoInstance = builder
//            .loggingEnabled(true)
                .build();
    }

    public static Picasso getPicassoInstance(Context context) {

        if (Utils.isNullOrEmpty(picassoInstance)) {
            new PicassoCache(context);
            return picassoInstance;
        }

        return picassoInstance;
    }

    public static void carregar(Object url, ImageView imageView) {
        carregar(App.getContext(), url, imageView);
    }

    public static void carregar(Object url, ImageView imageView, Callback callback) {
        carregar(App.getContext(), url, imageView, callback);
    }

    private static void carregar(Context context, Object object, ImageView imageView, Callback callback) {
        if (!Utils.isNullOrEmpty(object)) {

            if (object instanceof Integer) {
                PicassoCache.getPicassoInstance(context)
                        .load(((Integer) object))
                        .error(getDrawableError())
                        .placeholder(getDrawablePlaceHolder())
                        .priority(getPriority())
                        .into(imageView, callback);

            } else if (object instanceof String) {
                PicassoCache.getPicassoInstance(context)
                        .load(((String) object))
                        .error(getDrawableError())
                        .placeholder(getDrawablePlaceHolder())
                        .priority(getPriority())
                        .into(imageView, callback);
            }
        }
    }

    public static void carregar(Context context, Object url, ImageView imageView) {
        carregar(App.getContext(), url, imageView, null);
    }

    private static Picasso.Priority getPriority() {
        return Picasso.Priority.HIGH;
    }

    private static int getDrawablePlaceHolder() {
        return android.R.drawable.ic_menu_gallery;
    }

    private static int getDrawableError() {
        return android.R.drawable.stat_notify_error;
    }

}

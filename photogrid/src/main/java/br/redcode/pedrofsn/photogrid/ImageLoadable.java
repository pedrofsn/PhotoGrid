package br.redcode.pedrofsn.photogrid;

import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by User on 28/07/2016.
 */
public interface ImageLoadable {

    void loadImageView(String path, ImageView imageView);

    void loadImageView(Uri uri, ImageView imageView);
}

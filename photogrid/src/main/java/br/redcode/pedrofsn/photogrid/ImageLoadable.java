package br.redcode.pedrofsn.photogrid;

import android.widget.ImageView;

import java.io.File;

/**
 * Created by User on 28/07/2016.
 */
public interface ImageLoadable {

    void loadImageView(String path, ImageView imageView);

    void loadImageView(File uri, ImageView imageView);
}

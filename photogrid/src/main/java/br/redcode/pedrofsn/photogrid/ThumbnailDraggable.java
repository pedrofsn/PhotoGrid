package br.redcode.pedrofsn.photogrid;

import java.io.File;

/**
 * Created by pedrofsn on 27/07/2016.
 */
public class ThumbnailDraggable {

    private Object path;

    public ThumbnailDraggable(File file) {
        this.path = file;
    }

    public ThumbnailDraggable(String path) {
        this.path = path;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }
}

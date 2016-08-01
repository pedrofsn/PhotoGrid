package br.redcode.pedrofsn.photogrid.model;

/**
 * Created by User on 01/08/2016.
 */
public class ImageAlteravel {

    private Object path;
    private boolean cover;

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }
}

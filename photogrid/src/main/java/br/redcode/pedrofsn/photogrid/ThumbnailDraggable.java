package br.redcode.pedrofsn.photogrid;

import android.net.Uri;

/**
 * Created by pedrofsn on 27/07/2016.
 */
public class ThumbnailDraggable {

    private String url;
    private int posicao;
    private Uri uri;

    public ThumbnailDraggable(int posicao, String url) {
        this.posicao = posicao;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Object getPath() {
        return Utils.isNullOrEmpty(uri) ? url : uri;
    }
}

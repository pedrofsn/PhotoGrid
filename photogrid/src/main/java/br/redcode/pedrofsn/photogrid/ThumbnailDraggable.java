package br.redcode.pedrofsn.photogrid;

import java.io.File;

/**
 * Created by pedrofsn on 27/07/2016.
 */
public class ThumbnailDraggable {

    private String url;
    private int posicao;
    private File file;

    public ThumbnailDraggable(int posicao, String url) {
        this.posicao = posicao;
        this.url = url;
    }

    public ThumbnailDraggable(int posicao, File file) {
        this.posicao = posicao;
        this.file = file;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Object getPath() {
        return Utils.isNullOrEmpty(file) ? url : file;
    }
}

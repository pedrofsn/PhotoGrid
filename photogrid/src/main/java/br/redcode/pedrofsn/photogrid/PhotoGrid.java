package br.redcode.pedrofsn.photogrid;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by pedrofsn on 28/07/2016.
 */
public class PhotoGrid {

    private final RecyclerView recyclerView;
    private final List<ThumbnailDraggable> data;
    private final ImageLoadable callbackImageLoadable;
    private final boolean canChangeImage;

    private PhotoGrid(PhotoGridBuilder builder) {
        this.recyclerView = builder.recyclerView;
        this.data = builder.data;
        this.callbackImageLoadable = builder.callbackImageLoadable;
        this.canChangeImage = builder.canChangeImage;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public boolean canChangeImage() {
        return canChangeImage;
    }

    public List<ThumbnailDraggable> getData() {
        return data;
    }

    public ImageLoadable getCallbackImageLoadable() {
        return callbackImageLoadable;
    }

    public static class PhotoGridBuilder {

        private final RecyclerView recyclerView;
        private List<ThumbnailDraggable> data;
        private ImageLoadable callbackImageLoadable;
        private boolean canChangeImage;

        public PhotoGridBuilder(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        public PhotoGridBuilder data(List<ThumbnailDraggable> data) {
            this.data = data;
            return this;
        }

        public PhotoGridBuilder callbackImageLoadable(ImageLoadable callbackImageLoadable) {
            this.callbackImageLoadable = callbackImageLoadable;
            return this;
        }

        public PhotoGridBuilder canChangeImage(boolean canChangeImage) {
            this.canChangeImage = canChangeImage;
            return this;
        }

        public PhotoGrid build() {
            return new PhotoGrid(this);
        }
    }
}
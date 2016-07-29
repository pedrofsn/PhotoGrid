package br.redcode.pedrofsn.photogrid.utils;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.redcode.pedrofsn.photogrid.controller.ControllerImage;
import br.redcode.pedrofsn.photogrid.domain.CallbackItemChanged;
import br.redcode.pedrofsn.photogrid.domain.ImageLoadable;
import br.redcode.pedrofsn.photogrid.model.ThumbnailDraggable;

/**
 * Created by pedrofsn on 28/07/2016.
 */
public class PhotoGrid {

    private final RecyclerView recyclerView;
    private final List<ThumbnailDraggable> data;
    private final ImageLoadable callbackImageLoadable;
    private final boolean canChangeImage;
    private ControllerImage controllerImage;

    private PhotoGrid(PhotoGridBuilder builder) {
        this.recyclerView = builder.recyclerView;
        this.data = builder.data;
        this.callbackImageLoadable = builder.callbackImageLoadable;
        this.canChangeImage = builder.canChangeImage;
        this.controllerImage = new ControllerImage(this, builder.callbackItemChanged);
    }

    public ControllerImage getControllerImage() {
        return controllerImage;
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
        private CallbackItemChanged callbackItemChanged;
        private ImageLoadable callbackImageLoadable;
        private List<ThumbnailDraggable> data;
        private boolean canChangeImage;

        public PhotoGridBuilder(RecyclerView recyclerView, CallbackItemChanged callbackItemChanged) {
            this.callbackItemChanged = callbackItemChanged;
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

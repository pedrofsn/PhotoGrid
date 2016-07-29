package br.redcode.pedrofsn.photogrid.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import br.redcode.pedrofsn.photogrid.adapter.RecyclerViewAdapter;
import br.redcode.pedrofsn.photogrid.controller.ControllerImage;
import br.redcode.pedrofsn.photogrid.domain.CallbackItemChanged;
import br.redcode.pedrofsn.photogrid.domain.ImageLoadable;
import br.redcode.pedrofsn.photogrid.domain.MyOnItemClickListener;
import br.redcode.pedrofsn.photogrid.domain.OnStartDragListener;
import br.redcode.pedrofsn.photogrid.domain.SimpleItemTouchHelperCallback;
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
    private RecyclerViewAdapter adapter;
    private MyOnItemClickListener callback;
    private OnStartDragListener dragStartListener;
    private ItemTouchHelper.Callback simpleItemTouchHelperCallback;

    private PhotoGrid(PhotoGridBuilder builder) {
        this.recyclerView = builder.recyclerView;
        this.data = builder.thumbnailDraggables;
        this.callbackImageLoadable = builder.imageLoadable;
        this.canChangeImage = builder.canChangeImage;
        this.callback = builder.callback;
        this.dragStartListener = builder.dragStartListener;
        this.controllerImage = new ControllerImage(this, builder.callbackItemChanged);
        this.adapter = new RecyclerViewAdapter(builder.thumbnailDraggables, this.callback, this.dragStartListener, callbackImageLoadable);
        init();
    }

    private void init() {
        recyclerView.setAdapter(adapter);
        simpleItemTouchHelperCallback = new SimpleItemTouchHelperCallback(adapter);
        ;
    }

    public ItemTouchHelper.Callback getSimpleItemTouchHelperCallback() {
        return simpleItemTouchHelperCallback;
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
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
        private ImageLoadable imageLoadable;
        private List<ThumbnailDraggable> thumbnailDraggables;
        private boolean canChangeImage;
        private MyOnItemClickListener callback;
        private OnStartDragListener dragStartListener;

        public PhotoGridBuilder(RecyclerView recyclerView, CallbackItemChanged callbackItemChanged) {
            this.callbackItemChanged = callbackItemChanged;
            this.recyclerView = recyclerView;
        }

        public PhotoGridBuilder data(List<ThumbnailDraggable> data) {
            this.thumbnailDraggables = data;
            return this;
        }

        public PhotoGridBuilder callbackImageLoadable(ImageLoadable callbackImageLoadable) {
            this.imageLoadable = callbackImageLoadable;
            return this;
        }

        public PhotoGridBuilder callbackMyOnItemClickListener(MyOnItemClickListener callback) {
            this.callback = callback;
            return this;
        }

        public PhotoGridBuilder dragStartListener(OnStartDragListener dragStartListener) {
            this.dragStartListener = dragStartListener;
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

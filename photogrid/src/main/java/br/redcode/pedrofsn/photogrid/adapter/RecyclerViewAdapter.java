package br.redcode.pedrofsn.photogrid.adapter;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import br.redcode.pedrofsn.photogrid.R;
import br.redcode.pedrofsn.photogrid.adapter.viewholder.ItemViewHolder;
import br.redcode.pedrofsn.photogrid.domain.ImageLoadable;
import br.redcode.pedrofsn.photogrid.domain.ItemTouchHelperAdapter;
import br.redcode.pedrofsn.photogrid.domain.MyOnItemClickListener;
import br.redcode.pedrofsn.photogrid.domain.OnStartDragListener;
import br.redcode.pedrofsn.photogrid.model.ThumbnailDraggable;
import br.redcode.pedrofsn.photogrid.utils.Utils;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder> implements ItemTouchHelperAdapter {

    private OnStartDragListener dragStartListener;
    private List<ThumbnailDraggable> lista;
    private MyOnItemClickListener callback;
    private ImageLoadable callbackImageLoadable;

    public RecyclerViewAdapter() {

    }

    public RecyclerViewAdapter(List<ThumbnailDraggable> lista, MyOnItemClickListener callback, OnStartDragListener dragStartListener, ImageLoadable callbackImageLoadable) {
        this.lista = lista;
        this.callback = callback;
        this.dragStartListener = dragStartListener;
        this.callbackImageLoadable = callbackImageLoadable;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gridview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        if (lista.get(position) != null) {
            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.myOnItemClick(holder.frameLayout, position);
                }
            });

            holder.handleView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (MotionEvent.ACTION_DOWN == MotionEventCompat.getActionMasked(event)) {
                        dragStartListener.onStartDrag(holder);
                    }

                    return false;
                }

            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!Utils.isNullOrEmpty(lista.get(position)) && !Utils.isNullOrEmpty(callbackImageLoadable) && !Utils.isNullOrEmpty(holder.handleView)) {
                        Object obj = lista.get(position).getPath();
                        callbackImageLoadable.loadImageView(obj, holder.handleView);
                    }
                }
            }).run();
        }
    }

    @Override
    public void onItemDismiss(int position) {
        lista.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(lista, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(lista, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public OnStartDragListener getDragStartListener() {
        return dragStartListener;
    }

    public void setDragStartListener(OnStartDragListener dragStartListener) {
        this.dragStartListener = dragStartListener;
    }

    public List<ThumbnailDraggable> getLista() {
        return lista;
    }

    public void setLista(List<ThumbnailDraggable> lista) {
        this.lista = lista;
    }

    public MyOnItemClickListener getCallback() {
        return callback;
    }

    public void setCallback(MyOnItemClickListener callback) {
        this.callback = callback;
    }

    public ImageLoadable getCallbackImageLoadable() {
        return callbackImageLoadable;
    }

    public void setCallbackImageLoadable(ImageLoadable callbackImageLoadable) {
        this.callbackImageLoadable = callbackImageLoadable;
    }
}

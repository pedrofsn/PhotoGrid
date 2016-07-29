package br.redcode.pedrofsn.photogrid.adapter.viewholder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import br.redcode.pedrofsn.photogrid.R;
import br.redcode.pedrofsn.photogrid.domain.ItemTouchHelperViewHolder;

/**
 * Created by pedrofsn on 28/07/2016.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

    public FrameLayout frameLayout;
    public ImageView handleView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        handleView = (ImageView) itemView.findViewById(R.id.imageView);
        frameLayout = (FrameLayout) itemView.findViewById(R.id.frameLayout);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}

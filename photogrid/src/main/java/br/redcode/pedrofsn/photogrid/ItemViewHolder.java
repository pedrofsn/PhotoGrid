package br.redcode.pedrofsn.photogrid;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import br.redcode.pedrofsn.photogrid.helper.ItemTouchHelperViewHolder;

/**
 * Created by pedrofsn on 28/07/2016.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

    public final TextView textView;
    public final FrameLayout frameLayout;
    public final ImageView handleView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
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
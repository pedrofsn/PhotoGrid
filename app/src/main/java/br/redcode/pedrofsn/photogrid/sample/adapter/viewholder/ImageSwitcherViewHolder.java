package br.redcode.pedrofsn.photogrid.sample.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.redcode.pedrofsn.photogrid.ViewImageChange;
import br.redcode.pedrofsn.photogrid.sample.R;

/**
 * Created by pedrofsn on 01/08/2016.
 */
public class ImageSwitcherViewHolder extends RecyclerView.ViewHolder {

    public ViewImageChange viewImageChange;

    public ImageSwitcherViewHolder(View view) {
        super(view);
        viewImageChange = (ViewImageChange) view.findViewById(R.id.viewImageChange);
    }
}

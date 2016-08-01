package br.redcode.pedrofsn.photogrid.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.redcode.pedrofsn.photogrid.ImageViewLoadable;
import br.redcode.pedrofsn.photogrid.MyImageSwitcher;
import br.redcode.pedrofsn.photogrid.sample.R;
import br.redcode.pedrofsn.photogrid.sample.adapter.viewholder.ImageSwitcherViewHolder;
import br.redcode.pedrofsn.photogrid.sample.domain.MyOnItemClickListener;

/**
 * Created by pedrofsn on 01/08/2016.
 */
public class AdapterImageSwitcher extends RecyclerView.Adapter<ImageSwitcherViewHolder> {

    private List<MyImageSwitcher> lista = new ArrayList<>();
    private MyOnItemClickListener callback;
    private ImageViewLoadable callbackImage;

    public AdapterImageSwitcher(MyOnItemClickListener callback, ImageViewLoadable callbackImage, List<MyImageSwitcher> lista) {
        this.lista = lista;
        this.callback = callback;
        this.callbackImage = callbackImage;
    }

    @Override
    public ImageSwitcherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter, parent, false);
        ImageSwitcherViewHolder viewHolder = new ImageSwitcherViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageSwitcherViewHolder holder, final int position) {
        final MyImageSwitcher objeto = lista.get(position);

        callbackImage.load(objeto.getPath(), holder.viewImageChange.getImageView());

        holder.viewImageChange.getCheckBox().setChecked(objeto.isCover());

        holder.viewImageChange.getImageViewRemover().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remover(position);
            }
        });

        holder.viewImageChange.setChangeImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.myOnItemClick(view, position);
            }
        });

        holder.viewImageChange.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newStatus = holder.viewImageChange.getCheckBox().isChecked();
                holder.viewImageChange.getCheckBox().setChecked(newStatus);
                lista.get(position).setCover(newStatus);
            }
        });
    }

    private void remover(int position) {
        lista.get(position).setPath("_");
        lista.get(position).setCover(false);
        notifyItemChanged(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void edit(int position, MyImageSwitcher item) {
        if (position != -1 && lista.size() >= position) {
            lista.set(position, item);
            notifyItemChanged(position);
        }
    }

    public List<MyImageSwitcher> getLista() {
        return lista;
    }
}
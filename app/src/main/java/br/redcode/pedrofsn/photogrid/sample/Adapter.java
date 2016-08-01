package br.redcode.pedrofsn.photogrid.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.redcode.pedrofsn.photogrid.domain.MyOnItemClickListener;
import br.redcode.pedrofsn.photogrid.model.ImageAlteravel;
import br.redcode.pedrofsn.photogrid.ui.ViewImageChange;

/**
 * Created by User on 01/08/2016.
 */
public class Adapter extends RecyclerViewCustomAdapter<Adapter.ImageAlteravelViewHolder> {

    private List<ImageAlteravel> lista = new ArrayList<>();
    private Context context;
    private MyOnItemClickListener callback;

    public Adapter(Context context, List<ImageAlteravel> lista, MyOnItemClickListener callback) {
        this.context = context;
        this.lista = lista;
        this.callback = callback;
    }

    @Override
    public ImageAlteravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter, parent, false);
        ImageAlteravelViewHolder viewHolder = new ImageAlteravelViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageAlteravelViewHolder holder, final int position) {
        final ImageAlteravel objeto = lista.get(position);

        holder.viewImageChange.setContext(context);
        holder.viewImageChange.setPath(objeto.getPath());
        holder.viewImageChange.setCover(objeto.isCover());

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
                boolean novoStatus = holder.viewImageChange.getCheckBox().isChecked();
                holder.viewImageChange.getCheckBox().setChecked(novoStatus);
                lista.get(position).setCover(novoStatus);
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

    public void edit(int position, ImageAlteravel item) {
        if (position != -1 && lista.size() >= position) {
            lista.set(position, item);
            notifyItemChanged(position);
        }
    }

    public List<ImageAlteravel> getLista() {
        return lista;
    }

    class ImageAlteravelViewHolder extends RecyclerView.ViewHolder {

        public ViewImageChange viewImageChange;

        public ImageAlteravelViewHolder(View view) {
            super(view);
            viewImageChange = (ViewImageChange) view.findViewById(R.id.viewImageChange);
        }
    }
}

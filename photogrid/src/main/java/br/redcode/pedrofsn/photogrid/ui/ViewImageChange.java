package br.redcode.pedrofsn.photogrid.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import br.redcode.pedrofsn.photogrid.R;
import br.redcode.pedrofsn.photogrid.model.ImageAlteravel;
import br.redcode.pedrofsn.photogrid.utils.PicassoCache;

/**
 * Created by User on 01/08/2016.
 */
public class ViewImageChange extends LinearLayout {

    public View rootView;
    public Context context;

    public ImageView imageView;
    public ImageView imageViewRemover;
    private CheckBox checkBox;

    private ImageAlteravel imageAlteravel;

    public ViewImageChange(Context context) {
        super(context);
        init(context);
    }

    public ViewImageChange(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewImageChange(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        imageAlteravel = new ImageAlteravel();
        rootView = inflate(context, getLayout(), this);
        initView();
    }

    private void initView() {
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        checkBox = (CheckBox) rootView.findViewById(R.id.checkBox);
        imageViewRemover = (ImageView) rootView.findViewById(R.id.imageViewRemover);
    }

    public int getLayout() {
        return R.layout.ui_photo_change;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPath(Object object) {
        imageAlteravel.setPath(object);
        PicassoCache.carregar(object, imageView);
    }

    public void remover() {
        setPath("_");
    }

    public void setCover(boolean isCover) {
        imageAlteravel.setCover(isCover);
    }

    public void setChangeImageListener(OnClickListener onClickListener) {
        imageView.setOnClickListener(onClickListener);
    }

    public ImageAlteravel getImageAlteravel() {
        return imageAlteravel;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public ImageView getImageViewRemover() {
        return imageViewRemover;
    }
}

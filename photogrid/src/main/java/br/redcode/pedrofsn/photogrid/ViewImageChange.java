package br.redcode.pedrofsn.photogrid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by pedrofsn on 01/08/2016.
 */
public class ViewImageChange extends LinearLayout {

    public View rootView;

    public ImageView imageView;
    public ImageView imageViewRemover;
    private CheckBox checkBox;

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

    public void setChangeImageListener(OnClickListener onClickListener) {
        imageView.setOnClickListener(onClickListener);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public ImageView getImageViewRemover() {
        return imageViewRemover;
    }

    public ImageView getImageView() {
        return imageView;
    }
}

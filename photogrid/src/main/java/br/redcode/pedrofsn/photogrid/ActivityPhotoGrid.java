package br.redcode.pedrofsn.photogrid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.redcode.pedrofsn.photogrid.helper.OnStartDragListener;
import br.redcode.pedrofsn.photogrid.helper.SimpleItemTouchHelperCallback;


public class ActivityPhotoGrid extends ActivityGeneric implements Callback, OnStartDragListener {

    private static final int REQUEST_CODE_TAKE_PICTURE = 9393;
    private static final int REQUEST_CODE_ATTACH_PICTURE = 3939;
    private ItemTouchHelper mItemTouchHelper;
    private List<ThumbnailDraggable> lista = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        lista.add(new ThumbnailDraggable("http://www.blogwebdesignmicrocamp.com.br/wp-content/uploads/2015/09/carro.png"));
        lista.add(new ThumbnailDraggable(new File("/storage/emulated/0/Download/pedrofsn.jpg")));
        lista.add(new ThumbnailDraggable("http://caminhosdailuminacao.com.br/wp-content/uploads/2016/01/a-importancia-do-carro-para-os-homens.png"));
        lista.add(new ThumbnailDraggable("http://motoshopconsorcio.com.br/wp-content/uploads/photo-gallery/carro_top2.png"));
        lista.add(new ThumbnailDraggable(new File("/storage/emulated/0/Download/camaro.jpg")));
        lista.add(new ThumbnailDraggable(""));
        lista.add(new ThumbnailDraggable("http://mlb-s2-p.mlstatic.com/mini-buggy-utv-150-gaiola-mini-carro-quad-fapinha-534101-MLB20270035357_032015-O.jpg"));
        lista.add(new ThumbnailDraggable("http://blog.sossego.com.br/wp-content/uploads/2013/10/carro-bolt1.png"));

        photoGrid = new PhotoGrid.PhotoGridBuilder(recyclerView)
                .data(lista)
                .canChangeImage(false)
                .callbackImageLoadable(new ImageLoadable() {
                    @Override
                    public void loadImageView(Object o, ImageView imageView) {
                        PicassoCache.carregar(o, imageView);
                    }
                })
                .build();

        adapter = new RecyclerViewAdapter(photoGrid.getData(), new MyOnItemClickListener() {
            @Override
            public void myOnItemClick(View view, int position) {
                int clickedPosition = Utils.isNullOrEmpty(photoGrid.getData().get(position).getPath()) ? Constantes.VALOR_INVALIDO : position;

                if (Constantes.VALOR_INVALIDO == clickedPosition || photoGrid.canChangeImage()) {
                    tempPosition = position;
                    exibirAlert();
                    return;
                }

                tempPosition = Constantes.VALOR_INVALIDO;
                Toast.makeText(ActivityPhotoGrid.this, "Já existe imagem vinculada", Toast.LENGTH_SHORT).show();
            }
        }, this, photoGrid.getCallbackImageLoadable());

        photoGrid.getRecyclerView().setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(photoGrid.getRecyclerView());
    }

    public void exibirAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getString(R.string.selecionar_imagem));
        dialogBuilder.setItems(getResources().getStringArray(R.array.capturar_imagem), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        usarCamera();
                        break;
                    case 1:
                        usarGaleria();
                        break;
                }
            }
        });

        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void usarCamera() {
        Intent intent = getTakePictureIntent();
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    @Override
    public void usarGaleria() {
        Intent intent = getAttachPictureIntent();
        startActivityForResult(intent, REQUEST_CODE_ATTACH_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_ATTACH_PICTURE) {
            handleAttachPictureResult(data);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_TAKE_PICTURE) {
            handleTakePictureResult();
        }
    }

    @Override
    public void notifyItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }
}

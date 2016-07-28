package br.redcode.pedrofsn.photogrid;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.redcode.pedrofsn.photogrid.helper.OnStartDragListener;
import br.redcode.pedrofsn.photogrid.helper.SimpleItemTouchHelperCallback;


public class ActivityPhotoGrid extends Activity implements Callback, OnStartDragListener {

    private static final int REQUEST_CODE_TAKE_PICTURE = 9393;
    private static final int REQUEST_CODE_ATTACH_PICTURE = 3939;

    private ItemTouchHelper mItemTouchHelper;

    private List<ThumbnailDraggable> lista = new ArrayList<>();

    private String mTakePicturePath;
    private int tempPosition = Constantes.VALOR_INVALIDO;
    private RecyclerViewAdapter adapter;

    PhotoGrid photoGrid;

    private static File getNewImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maina);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        lista.add(new ThumbnailDraggable(0, "http://www.blogwebdesignmicrocamp.com.br/wp-content/uploads/2015/09/carro.png"));
        lista.add(new ThumbnailDraggable(1, "pera"));
        lista.add(new ThumbnailDraggable(2, "http://caminhosdailuminacao.com.br/wp-content/uploads/2016/01/a-importancia-do-carro-para-os-homens.png"));
        lista.add(new ThumbnailDraggable(3, "http://motoshopconsorcio.com.br/wp-content/uploads/photo-gallery/carro_top2.png"));
        lista.add(new ThumbnailDraggable(4, "pera"));
        lista.add(new ThumbnailDraggable(5, "pera"));
        lista.add(new ThumbnailDraggable(6, "pera"));
        lista.add(new ThumbnailDraggable(7, "pera"));
        lista.add(new ThumbnailDraggable(8, "http://mlb-s2-p.mlstatic.com/mini-buggy-utv-150-gaiola-mini-carro-quad-fapinha-534101-MLB20270035357_032015-O.jpg"));
        lista.add(new ThumbnailDraggable(9, "pera"));
        lista.add(new ThumbnailDraggable(10, "pera"));
        lista.add(new ThumbnailDraggable(11, "pera"));
        lista.add(new ThumbnailDraggable(12, "http://blog.sossego.com.br/wp-content/uploads/2013/10/carro-bolt1.png"));
        lista.add(new ThumbnailDraggable(13, "pera"));
        lista.add(new ThumbnailDraggable(14, "pera"));

        photoGrid = new PhotoGrid.PhotoGridBuilder(recyclerView)
                .data(lista)
                .callbackImageLoadable(new ImageLoadable() {
                    @Override
                    public void loadImageView(String path, ImageView imageView) {
                        PicassoCache.carregar(path, imageView);
                    }

                    @Override
                    public void loadImageView(Uri uri, ImageView imageView) {
                        PicassoCache.carregar(uri, imageView);
                    }
                })
                .build();

        adapter = new RecyclerViewAdapter(photoGrid.getData(), new MyOnItemClickListener() {
            @Override
            public void myOnItemClick(View view, int position) {
                int clickedPosition = (photoGrid.getData().get(position).getUrl() == null || "pera".equals(photoGrid.getData().get(position).getUrl())) ? position : Constantes.VALOR_INVALIDO;

                if (clickedPosition == tempPosition) {
                    tempPosition = Constantes.VALOR_INVALIDO;
                } else {
                    tempPosition = clickedPosition;
                }

                exibirAlert();
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

    private Intent getTakePictureIntent() {
        mTakePicturePath = null;
        File pictureFile = null;

        try {
            pictureFile = getNewImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (pictureFile == null)
            return null;

        mTakePicturePath = pictureFile.getAbsolutePath();
        Utils.log("Path: " + mTakePicturePath);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
        return takePictureIntent;
    }

    private Intent getAttachPictureIntent() {
        Intent attachPictureIntent = new Intent(Intent.ACTION_PICK);
        attachPictureIntent.setType("image/*");
        return attachPictureIntent;
    }

    private void handleTakePictureResult() {
        Utils.log("File Path: " + mTakePicturePath);

        addThumbnail(mTakePicturePath);
        addNewImageToGallery(this, mTakePicturePath);
    }

    private void handleAttachPictureResult(Intent data) {
        Uri uri = data.getData();
        addThumbnail(uri);
    }

    private void addNewImageToGallery(Context context, String filePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    private String getRealPathFromURI(Context context, Uri contentURI) {
        String result = null;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void addThumbnail(Object obj) {
        Utils.log("addThumbnail: " + obj);

        if (!Utils.isNullOrEmpty(obj)) {
            if (obj instanceof Uri) {
                photoGrid.getData().get(tempPosition).setUri(((Uri) obj));
            } else if (obj instanceof String) {
                photoGrid.getData().get(tempPosition).setUrl(((String) obj));
            }
            adapter.notifyDataSetChanged();
            tempPosition = Constantes.VALOR_INVALIDO;
        }
    }
}

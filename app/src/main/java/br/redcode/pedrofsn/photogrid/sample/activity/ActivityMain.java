package br.redcode.pedrofsn.photogrid.sample.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.redcode.pedrofsn.photogrid.ImageViewLoadable;
import br.redcode.pedrofsn.photogrid.MyImageSwitcher;
import br.redcode.pedrofsn.photogrid.sample.R;
import br.redcode.pedrofsn.photogrid.sample.adapter.AdapterImageSwitcher;
import br.redcode.pedrofsn.photogrid.sample.domain.MyOnItemClickListener;
import br.redcode.pedrofsn.photogrid.sample.utils.Constantes;
import br.redcode.pedrofsn.photogrid.sample.utils.PicassoCache;
import br.redcode.pedrofsn.photogrid.sample.utils.Utils;

/**
 * Created by pedrofsn on 29/07/2016.
 */
public class ActivityMain extends ActivityImageCapture implements MyOnItemClickListener, ImageViewLoadable {

    private AdapterImageSwitcher adapterImageSwitcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        List<MyImageSwitcher> lista = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            MyImageSwitcher ia = new MyImageSwitcher();
            ia.setCover(i == 5);
            ia.setPath(null);
            lista.add(ia);
        }

        adapterImageSwitcher = new AdapterImageSwitcher(this, this, lista);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(adapterImageSwitcher);
    }

    public void showAlertImagePicker() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getString(R.string.select_image));
        dialogBuilder.setItems(getResources().getStringArray(R.array.capturar_imagem), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        useCamera();
                        break;
                    case 1:
                        useGallery();
                        break;
                }
            }
        });

        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

    public void useCamera() {
        Intent intent = getTakePictureIntent();
        startActivityForResult(intent, Constantes.REQUEST_CODE_TAKE_PICTURE);
    }

    public void useGallery() {
        Intent intent = getAttachPictureIntent();
        startActivityForResult(intent, Constantes.REQUEST_CODE_ATTACH_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constantes.REQUEST_CODE_ATTACH_PICTURE) {
            handleAttachPictureResult(data);
        }
        if (resultCode == RESULT_OK && requestCode == Constantes.REQUEST_CODE_TAKE_PICTURE) {
            handleTakePictureResult();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemPrint:
                printDetails();
                showAlertCover();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void printDetails() {
        for (int i = 0; i < adapterImageSwitcher.getLista().size(); i++) {
            MyImageSwitcher myImageSwitcher = adapterImageSwitcher.getLista().get(i);
            Log.d("app", String.format(getString(R.string.print_message), i, myImageSwitcher.getPath(), myImageSwitcher.isCover()));
        }
    }

    private void showAlertCover() {
        MyImageSwitcher cover = adapterImageSwitcher.getCover();

        if (!Utils.isNullOrEmpty(cover)) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(getString(R.string.cover));

            if (cover.getPath() instanceof String) {
                alertDialog.setMessage(((String) cover.getPath()));

            } else if (cover.getPath() instanceof File) {
                alertDialog.setMessage(((File) cover.getPath()).getAbsolutePath());
            }

            alertDialog.show();
        }
    }

    @Override
    public void addThumbnail(Object obj) {
        MyImageSwitcher myImageSwitcher = adapterImageSwitcher.getLista().get(tempPosition);
        myImageSwitcher.setPath(obj);
        adapterImageSwitcher.edit(tempPosition, myImageSwitcher);
        tempPosition = Constantes.INVALID_VALUE;
    }

    @Override
    public void myOnItemClick(View view, int position) {
        tempPosition = position;
        showAlertImagePicker();
    }

    @Override
    public void load(Object path, ImageView imageView) {
        PicassoCache.carregar(path, imageView);
    }
}

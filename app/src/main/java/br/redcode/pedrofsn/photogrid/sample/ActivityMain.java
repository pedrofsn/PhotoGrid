package br.redcode.pedrofsn.photogrid.sample;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.redcode.pedrofsn.photogrid.domain.MyOnItemClickListener;
import br.redcode.pedrofsn.photogrid.model.ImageAlteravel;
import br.redcode.pedrofsn.photogrid.utils.Constantes;
import br.redcode.pedrofsn.photogrid.utils.Utils;

public class ActivityMain extends AcTivityGeneric {

    private Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        List<ImageAlteravel> lista = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ImageAlteravel ia = new ImageAlteravel();
            ia.setCover(false);
            ia.setPath(null);
            lista.add(ia);
        }

        adapter = new Adapter(this, lista, new MyOnItemClickListener() {

            @Override
            public void myOnItemClick(View view, int position) {
                tempPosition = position;
                exibirAlert();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

//        viewImageChange = (ViewImageChange) findViewById(R.id.viewImageChange);
//        viewImageChange.setContext(this);
//        viewImageChange.setChangeImageListener();
    }

    public void exibirAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getString(br.redcode.pedrofsn.photogrid.R.string.selecionar_imagem));
        dialogBuilder.setItems(getResources().getStringArray(br.redcode.pedrofsn.photogrid.R.array.capturar_imagem), new DialogInterface.OnClickListener() {
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

    public void usarCamera() {
        Intent intent = getTakePictureIntent();
        startActivityForResult(intent, Constantes.REQUEST_CODE_TAKE_PICTURE);
    }

    public void usarGaleria() {
        Intent intent = getAttachPictureIntent();
        startActivityForResult(intent, Constantes.REQUEST_CODE_ATTACH_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constantes.REQUEST_CODE_ATTACH_PICTURE) {
            handleAttachPictureResult(data);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == Constantes.REQUEST_CODE_TAKE_PICTURE) {
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
            case R.id.menuItemInfo:
                for (int i = 0; i < adapter.getLista().size(); i++) {
                    ImageAlteravel imageAlteravel = adapter.getLista().get(i);
                    Utils.log(imageAlteravel.getPath() + " = " + imageAlteravel.isCover());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addThumbnail(Object obj) {
        ImageAlteravel imageAlteravel = adapter.getLista().get(tempPosition);
        imageAlteravel.setPath(obj);
        adapter.edit(tempPosition, imageAlteravel);

        Utils.log("addThumbnail na posição " + tempPosition + ": " + obj);
//        photoGrid.getData().get(tempPosition).setPath(obj);
        tempPosition = Constantes.VALOR_INVALIDO;
    }
}

package br.redcode.pedrofsn.photogrid.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.redcode.pedrofsn.photogrid.ActivityPhotoGrid;
import br.redcode.pedrofsn.photogrid.PhotoGrid;
import br.redcode.pedrofsn.photogrid.ThumbnailDraggable;

public class MainActivity extends AppCompatActivity {

    private PhotoGrid photoGrid;
    private static final int COLUNAS = 3;
    private List<ThumbnailDraggable> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        setContentView(R.layout.activity_maina);

        RecyclerView recyclerView = (RecyclerView) findViewById(br.redcode.pedrofsn.photogrid.R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, COLUNAS);
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
                .callbackImageLoadable(null)
                .build();
*/
        startActivity(new Intent(MainActivity.this, ActivityPhotoGrid.class));
    }
}

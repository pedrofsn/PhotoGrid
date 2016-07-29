package br.redcode.pedrofsn.photogrid.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.redcode.pedrofsn.photogrid.activity.ActivityPhotoGrid;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(MainActivity.this, ActivityPhotoGrid.class));
    }
}

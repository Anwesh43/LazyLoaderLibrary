package com.anwesome.ui.lazyloaderdemo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.anwesome.ui.lazyloader.LazyLoader;

public class MainActivity extends AppCompatActivity {
    private ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView)findViewById(R.id.img1);
        final LazyLoader lazyLoader = new LazyLoader(img1);
        lazyLoader.load();

    }
}

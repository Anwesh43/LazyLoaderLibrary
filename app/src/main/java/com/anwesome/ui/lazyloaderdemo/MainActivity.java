package com.anwesome.ui.lazyloaderdemo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.anwesome.ui.lazyloader.LazyLoader;

public class MainActivity extends AppCompatActivity {
    private ImageView img1,img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView)findViewById(R.id.img1);
        final LazyLoader lazyLoader1 = new LazyLoader(img1);
        lazyLoader1.load();
        img2 = (ImageView)findViewById(R.id.img2);
        final LazyLoader lazyLoader2 = new LazyLoader(img2);
        lazyLoader2.unload();

    }
}

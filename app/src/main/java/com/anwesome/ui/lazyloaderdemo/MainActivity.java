package com.anwesome.ui.lazyloaderdemo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.anwesome.ui.lazyloader.LazyLoader;

public class MainActivity extends AppCompatActivity {
    private ImageView img1,img2,img3,img4;
    private LazyLoader lazyLoader1,lazyLoader2,lazyLoader3,lazyLoader4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView)findViewById(R.id.img1);
        lazyLoader1 = new LazyLoader(img1);
        lazyLoader1.load();
        img2 = (ImageView)findViewById(R.id.img2);
        lazyLoader2 = new LazyLoader(img2);
        lazyLoader2.load();
        img3 = (ImageView)findViewById(R.id.img3);
        lazyLoader3 = new LazyLoader(img3);
        lazyLoader3.load();
        img4 = (ImageView)findViewById(R.id.img4);
        lazyLoader4 = new LazyLoader(img4);
        lazyLoader4.load();
        stopLoading();
    }
    private void stopLoading() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }
                catch (Exception ex) {

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lazyLoader1.unload();
                        lazyLoader3.unload();
                    }
                });
                try {
                    Thread.sleep(3000);
                }
                catch (Exception ex) {

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lazyLoader2.unload();
                        lazyLoader4.unload();
                    }
                });

            }
        }).start();
    }
}

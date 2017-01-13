package com.anwesome.ui.lazyloader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 13/01/17.
 */
public class LazyLoader {
    private View view;
    private int w = 0,h = 0;
    private LazyLoaderView lazyLoaderView;
    private boolean loaded = true;
    private static Thread loadingThread;
    private static boolean isRunning = false;
    private static ConcurrentLinkedQueue<LazyLoaderView> lazyLoaderViews = new ConcurrentLinkedQueue<LazyLoaderView>();
    public static void startLoading() {
        if(!isRunning) {
            isRunning = true;
            loadingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRunning) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception ex) {

                        }
                        for (LazyLoaderView lazyLoaderView : lazyLoaderViews) {
                            lazyLoaderView.postInvalidate();
                        }
                    }
                }
            });
            loadingThread.start();
        }
    }
    public static void pause() {
        if(isRunning) {
            isRunning = false;
            while (true) {
                try {
                    loadingThread.join();
                    break;
                } catch (Exception ex) {

                }
            }
        }
    }
    public LazyLoader(final View view) {
        this.view = view;
    }
    private void loadLazily() {
        lazyLoaderView = new LazyLoaderView(view.getContext());
        lazyLoaderView.setTranslationX(view.getX()+w/4);
        lazyLoaderView.setTranslationY(view.getY());
        view.setAlpha(0.36f);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.addView(lazyLoaderView, new ViewGroup.LayoutParams(w/2, w/2));
        }
        loaded = true;
        lazyLoaderViews.add(lazyLoaderView);
    }
    public int hashCode() {
        return view.hashCode();
    }
    public void load() {
        loaded = false;
        this.view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!loaded) {
                    w = view.getWidth();
                    h = view.getHeight();
                    loadLazily();
                }
            }
        });
    }
    public void unload() {
        if(loaded) {
            view.setAlpha(1);
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(lazyLoaderView);
                lazyLoaderViews.remove(lazyLoaderView);
            }
            lazyLoaderView = null;
        }

    }
    private class LazyLoaderView extends View {
        private int deg = 0,prevDeg = 0;
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        public LazyLoaderView(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {
            deg = prevDeg;
            canvas.drawColor(Color.parseColor("#00FFFFFF"));
            int cw = canvas.getWidth(),ch = canvas.getHeight(),radius=cw/3;
            if(cw>ch) {
                radius = ch/3;
            }
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#009688"));
            paint.setStrokeWidth(radius/10);
            canvas.save();
            Path path = new Path();
            path.moveTo((float)(cw/2+radius*Math.cos(deg*Math.PI/180)),(float)(ch/2+radius*Math.sin(deg*Math.PI/180)));
            for(int i=deg;i<deg+60;i++) {
                float x = (float)(cw/2+radius*Math.cos(i*Math.PI/180)),y = (float)(ch/2+radius*Math.sin(i*Math.PI/180));
                path.lineTo(x,y);
            }
            canvas.drawPath(path,paint);
            canvas.restore();
            deg+=10;
            prevDeg = deg;
        }

    }
}

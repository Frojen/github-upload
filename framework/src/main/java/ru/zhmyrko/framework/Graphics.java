package ru.zhmyrko.framework;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Konstantin on 20.09.2017.
 */

public class Graphics {
    AssetManager assets;
    Resources resource;
    Bitmap frameBuffer;
    Canvas canvas;
    //Rect srcRect=new Rect();
    //Rect dstRect=new Rect();

    public Graphics(Resources resources, AssetManager assest, Bitmap frameBuffer){
        this.resource=resources;
        this.assets=assest;
        this.frameBuffer=frameBuffer;
        this.canvas=new Canvas(frameBuffer);
    }

    public Bitmap newBitmap(String fileName){
        InputStream in = null;
        Bitmap bitmap;
        try {
            in=assets.open(fileName);
            bitmap= BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Ошибка загрузки картинки из asset '"
                        + fileName + "'");
        } catch (IOException ex){
            throw new RuntimeException("Ошибка загрузки картинки из asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {

                }
            }
        }
        return bitmap;
    }

    public void clear(int color) {
        canvas.drawColor(color);
    }

    public int getWidth() {
        return frameBuffer.getWidth();
    }

    public int getHeight() {
        return frameBuffer.getHeight();
    }

    public Resources getResource() {
        return resource;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}

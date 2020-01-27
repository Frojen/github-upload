package ru.zhmyrko.testpuzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * Created by Konstantin on 15.09.2017.
 */

public class ElementPuzzle {

    Bitmap puzzle;
    int convX, convY;
    int width, height;
    private int right, bot;
    public boolean lS, rS, bS, tS;
    public Sprite sprite;

    public ElementPuzzle(ElementPuzzleMask element, Bitmap pict, int x, int y) {

        lS=element.lS;
        rS=element.rS;
        tS=element.tS;
        bS=element.bS;

        puzzle=Bitmap.createBitmap(element.getMaskB().getWidth(), element.getMaskB().getHeight(), Bitmap.Config.ARGB_8888);

        convX=element.getoLeft();
        convY=element.getoTop();

        Bitmap tmp= Bitmap.createBitmap(pict, x-convX, y-convY, element.getW(), element.getH());
        BitmapShader mbitmapShader=new BitmapShader(tmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Paint mPaint=new Paint();
        mPaint.setShader(mbitmapShader);
        Canvas canvas = new Canvas(puzzle);
        canvas.drawBitmap(element.getMaskB(), 0, 0, mPaint);

        width=element.getWidth();
        height=element.getHeight();

        right=element.getRight();
        bot=element.getBot();

    }

    public int getRight() {
        return right;
    }

    public int getBot() {
        return bot;
    }
}
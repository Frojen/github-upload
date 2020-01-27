package ru.zhmyrko.testpuzzle;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import static android.R.attr.bitmap;

/**
 * Created by Konstantin on 15.09.2017.
 */

public class ElementMask {

    private Bitmap mask;
    private ElementMask para;
    private int otstyp;
    static int baseOtstyp;


    public ElementMask(Resources resources, @DrawableRes int id) {

        int mSize=100*Assets.size/54;
        VectorDrawableCompat dre= VectorDrawableCompat.create(resources,id,null);;
        //VectorDrawableCompat dre = (VectorDrawableCompat) ResourcesCompat.getDrawable(resources,id,null);
        mask=Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(mask);
        dre.setBounds(0, 0, mSize, mSize);
        dre.draw(canvas);

        if (baseOtstyp==0){
            baseOtstyp=Otstyp();
            para=this;
        }
        otstyp=Otstyp();
    }

    public ElementMask getPara() {
        return para;
    }

    //Взаимная установка парного элемента
    public void setPara(ElementMask elem){
        para=elem;
        if (!(elem.getPara()==this)){
            para.setPara(this);
        }
    }

    public Bitmap getMask() {
        return mask;
    }

    public int getOtstyp() {
        return otstyp;
    }

    private int Otstyp(){
        for (int y=0; y<mask.getWidth(); y++){
            for (int x=0; x<mask.getHeight(); x++){
                if (Color.alpha(mask.getPixel(x,y))==255){//проверить что выдает getPixel
                    //Log.d("PUZZLE", "Otstyp: "+Integer.toString(mask.getPixel(x,y))+" "+ Integer.toString(Color.alpha(mask.getPixel(x,y))));
                    return y;
                }
            }
        }
        return 0;
    }

}

package ru.zhmyrko.testpuzzle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin on 14.09.2017.
 */
public class ElementPuzzleMask {
    private Bitmap mPazzle;
    private int oLeft;
    private int oTop;
    private int oBot;
    private int oRight;
    private Bitmap maskB, maskBRet;
    private Canvas maskC;
    private int x,y,w,h;
    private int width, height;
    private int right, bot;
    public boolean lS, rS, bS, tS;

    /*public ElementPuzzleMask(Bitmap mask, int oLeft, int oTop, int oRight, int oBot) {
        this.oLeft=oLeft;
        this.oTop=oTop;
        this.oRight=oRight;
        this.oBot=oBot;

        mPazzle=Bitmap.createBitmap(size, size, Bitmap.Config.ALPHA_8);

    }*/

    public void GenerateElementPuzzleMask(int left, int top, int right, int bot, List<ElementMask> masks) {

        if (left==0) lS=true;
        else lS=false;

        if (right==0) rS=true;
        else rS=false;

        if (top==0) tS=true;
        else tS=false;

        if (bot==0) bS=true;
        else bS=false;

        if (maskB==null) {
            maskB= Bitmap.createBitmap(masks.get(0).getMask().getWidth(), masks.get(0).getMask().getHeight(), Bitmap.Config.ALPHA_8);
            maskC= new Canvas(maskB);
        }
        maskB.eraseColor(0);

        float center=masks.get(0).getMask().getWidth()/2;

        maskC.drawBitmap(masks.get(top).getMask(), 0, 0, null);
        maskC.rotate(90f,center,center);
        maskC.drawBitmap(masks.get(right).getMask(), 0, 0, null);
        maskC.rotate(90f,center,center);
        maskC.drawBitmap(masks.get(bot).getMask(), 0, 0, null);
        maskC.rotate(90f,center,center);
        maskC.drawBitmap(masks.get(left).getMask(), 0, 0, null);

        oLeft=ElementMask.baseOtstyp-masks.get(left).getOtstyp();
        this.right=masks.indexOf(masks.get(right).getPara());
        oTop=ElementMask.baseOtstyp-masks.get(top).getOtstyp();
        this.bot=masks.indexOf(masks.get(bot).getPara());

        x=masks.get(left).getOtstyp();
        y=masks.get(top).getOtstyp();
        w=maskB.getWidth()-x-masks.get(right).getOtstyp();
        h=maskB.getHeight()-y-masks.get(bot).getOtstyp();

        maskC.rotate(90f,center,center);
        //return maskB;

        width=w-oLeft-(ElementMask.baseOtstyp-masks.get(right).getOtstyp());
        height=h-oTop-(ElementMask.baseOtstyp-masks.get(bot).getOtstyp());

    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getoLeft() {
        return oLeft;
    }

    public int getoTop() {
        return oTop;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Bitmap getMaskB() {
        //return maskB;
        return Bitmap.createBitmap(maskB, x, y, w, h);
    }

    public int getBot() {
        return bot;
    }

    public int getRight() {
        return right;
    }


}
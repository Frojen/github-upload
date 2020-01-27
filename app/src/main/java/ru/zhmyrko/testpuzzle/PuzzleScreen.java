package ru.zhmyrko.testpuzzle;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.zhmyrko.framework.*;

/**
 * Created by Konstantin on 20.09.2017.
 */

class PuzzleScreen extends Screen {

    public PuzzleScreen(ru.zhmyrko.framework.Game game) {
        super(game);
        ElementPuzzleMask element=new ElementPuzzleMask();
        List<ElementMask> masks=new ArrayList<>();
        final Random random = new Random();

        Graphics g=game.getGraphics();

        Assets.size=Assets.puzzleOriginal.getWidth()/Assets.wPice;

        masks.add(new ElementMask(g.getResource(),R.drawable.ic_rovnaya));

        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var1_vnut));
        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var1_nar));
        masks.get(masks.size()-2).setPara(masks.get(masks.size()-1));

        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var2_vnut));
        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var2_nar));
        masks.get(masks.size()-2).setPara(masks.get(masks.size()-1));

        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var3_vnut));
        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var3_nar));
        masks.get(masks.size()-2).setPara(masks.get(masks.size()-1));

        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var4_vnut));
        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var4_nar));
        masks.get(masks.size()-2).setPara(masks.get(masks.size()-1));

        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var5_vnut));
        masks.add(new ElementMask(g.getResource(),R.drawable.ic_var5_nar));
        masks.get(masks.size()-2).setPara(masks.get(masks.size()-1));

        for (int i=0; i<Assets.hPice; i++){
            for (int j=0; j<Assets.wPice; j++){
                //левый верхний угол
                if ((j==0)&(i==0)) {
                    element.GenerateElementPuzzleMask(0,0,random.nextInt(Assets.VARIANT) + 1,random.nextInt(Assets.VARIANT) + 1, masks);
                }
                //правый верхний угол
                else if ((Assets.wPice-1==j)&(i==0)) {
                    element.GenerateElementPuzzleMask(Assets.pazzles.get(Assets.pazzles.size()-1).getRight(),0,0,random.nextInt(Assets.VARIANT) + 1, masks);
                }
                //левый нижний угол
                else if ((Assets.hPice-1==i)&j==0) {
                    //pazzles.get((i-1)*wPice+j).getBot();
                    //pazzles.get(pazzles.size()-2).getRight();

                    element.GenerateElementPuzzleMask(0,Assets.pazzles.get((i-1)*Assets.wPice+j).getBot(),random.nextInt(Assets.VARIANT) + 1,0, masks);
                }
                //правый нижний угол
                else if ((Assets.hPice-1==i)&(Assets.wPice-1==j)) {
                    element.GenerateElementPuzzleMask(Assets.pazzles.get(Assets.pazzles.size()-1).getRight(),Assets.pazzles.get((i-1)*Assets.wPice+j).getBot(),0,0, masks);
                }
                //верхняя сторона
                else if (i==0) {
                    element.GenerateElementPuzzleMask(Assets.pazzles.get(Assets.pazzles.size()-1).getRight(),0,random.nextInt(Assets.VARIANT) + 1,random.nextInt(Assets.VARIANT) + 1, masks);
                }
                //левая сторона
                else if (j==0) {
                    element.GenerateElementPuzzleMask(0,Assets.pazzles.get((i-1)*Assets.wPice+j).getBot(),random.nextInt(Assets.VARIANT) + 1,random.nextInt(Assets.VARIANT) + 1, masks);
                }
                //правая сторона
                else if ((Assets.wPice-1==j)) {
                    element.GenerateElementPuzzleMask(Assets.pazzles.get(Assets.pazzles.size()-1).getRight(),Assets.pazzles.get((i-1)*Assets.wPice+j).getBot(),0,random.nextInt(Assets.VARIANT) + 1, masks);
                }
                //нижняя сторона
                else if (Assets.hPice-1==i) {
                    element.GenerateElementPuzzleMask(Assets.pazzles.get(Assets.pazzles.size()-1).getRight(),Assets.pazzles.get((i-1)*Assets.wPice+j).getBot(),random.nextInt(Assets.VARIANT) + 1,0, masks);
                }
                else {
                    element.GenerateElementPuzzleMask(Assets.pazzles.get(Assets.pazzles.size()-1).getRight(),Assets.pazzles.get((i-1)*Assets.wPice+j).getBot(),random.nextInt(Assets.VARIANT) + 1,random.nextInt(Assets.VARIANT) + 1, masks);
                }
                Assets.pazzles.add(new ElementPuzzle(element,Assets.puzzleOriginal,(j*Assets.size), (i*Assets.size)));
                Assets.sprites.add(new GroupSprites(random.nextInt(600)+600,random.nextInt(650)+40,Assets.pazzles.get(Assets.pazzles.size()-1)));
            }
        }
        ColorFilter filter = new LightingColorFilter(Color.DKGRAY, 1);
        fon.setColorFilter(filter);
    }

    GroupSprites lastSpr;
    float lastX, lastY;
    Paint contur=new Paint();
    int channel=0;
    Paint fon=new Paint();
    Sprite tmp;
    int countPuzlle=Assets.hPice*Assets.wPice;

    @Override
    public void update(float deltaTime) {
        boolean puzzleUp;
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        Log.d("Test", "update "+Float.toString(deltaTime));
        //game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int k = 0; k < len; k++){
            Input.TouchEvent event = touchEvents.get(k);
            if(event.type == Input.TouchEvent.TOUCH_DOWN){
                if (channel==1 && event.y>30){
                    channel=0;
                    break;
                }

                lastX = event.x;
                lastY = event.y;
                for (int i = Assets.sprites.size() - 1; i >= 0; i--) {
                    lastSpr = Assets.sprites.get(i);
                    if (lastSpr.isSelected(event.x, event.y)) {
                        if (i != Assets.sprites.size() - 1)
                            Assets.sprites.add(Assets.sprites.remove(i));
                        Assets.clickDown.play(1);
                        //Log.d("click", "update: "+Float.toString(event.x)+"" +Float.toString(event.y)+
                        //        " "+Float.toString(lastSpr.x)+" "+Float.toString(lastSpr.y));
                        break;
                    } else {
                        lastSpr = null;
                    }
                }
            } else if (event.type == Input.TouchEvent.TOUCH_UP){
                if (lastSpr != null) {
                    puzzleUp=false;

                    /*for (Sprite tmp : Assets.sprites){
                        if (lastSpr==tmp) continue;
                        lastSpr.check(tmp);
                    }*/

                    lastSpr.setDxDy(event.x - lastX,event.y - lastY);

                    for (Sprite SpriteGroupsAll : lastSpr.groupsprites){

                        if (!(SpriteGroupsAll.top)) {
                            tmp=Assets.pazzles.get(SpriteGroupsAll.topP).sprite;
                            if (tmp.grSpr!=lastSpr) {
                                if (tmp.y > (SpriteGroupsAll.y - tmp.getElement().height - 50)
                                        && (tmp.y < (SpriteGroupsAll.y - tmp.getElement().height + 50))
                                        && (SpriteGroupsAll.x > tmp.x - 50)
                                        && (SpriteGroupsAll.x < tmp.x + 50)) {
                                    lastSpr.setDxDy(tmp.x-SpriteGroupsAll.x,tmp.y+tmp.getElement().height-SpriteGroupsAll.y);
                                    lastSpr.update(0);
                                    lastSpr.addGroup(tmp.grSpr);
                                    //Assets.sprites.remove(tmp.grSpr);
                                    //tmp.bot=true;
                                    //SpriteGroupsAll.top=true;
                                    puzzleUp=true;
                                    break;
                                }
                            }
                        }

                        if (!(SpriteGroupsAll.bot)) {
                            tmp=Assets.pazzles.get(SpriteGroupsAll.botP).sprite;
                            if (tmp.grSpr!=lastSpr){
                                if (tmp.y > (SpriteGroupsAll.y + tmp.getElement().height - 50)
                                        && (tmp.y < (SpriteGroupsAll.y + tmp.getElement().height + 50))
                                        && (SpriteGroupsAll.x > tmp.x - 50)
                                        && (SpriteGroupsAll.x < tmp.x + 50)) {
                                    lastSpr.setDxDy(tmp.x-SpriteGroupsAll.x,tmp.y-tmp.getElement().height-SpriteGroupsAll.y);
                                    lastSpr.update(0);
                                    lastSpr.addGroup(tmp.grSpr);
                                    //Assets.sprites.remove(tmp.grSpr);
                                    //tmp.top=true;
                                    //SpriteGroupsAll.bot=true;
                                    puzzleUp=true;
                                    break;
                                }
                            }
                        }

                        if (!(SpriteGroupsAll.left)){
                            tmp=Assets.pazzles.get(SpriteGroupsAll.leftP).sprite;
                            if (tmp.grSpr!=lastSpr){
                                if (SpriteGroupsAll.x > (tmp.x + tmp.getElement().width - 50)
                                        && (SpriteGroupsAll.x < (tmp.x + tmp.getElement().width + 50))
                                        && (SpriteGroupsAll.y > tmp.y - 50)
                                        && (SpriteGroupsAll.y < tmp.y + 50)) {
                                    lastSpr.setDxDy(tmp.x + tmp.getElement().width-SpriteGroupsAll.x,tmp.y-SpriteGroupsAll.y);
                                    lastSpr.update(0);
                                    lastSpr.addGroup(tmp.grSpr);
                                    //Assets.sprites.remove(tmp.grSpr);
                                    //tmp.right=true;
                                    //SpriteGroupsAll.left=true;
                                    puzzleUp=true;
                                    break;
                                }
                            }
                        }

                        if (!(SpriteGroupsAll.right)){
                            tmp=Assets.pazzles.get(SpriteGroupsAll.rightP).sprite;
                            if (tmp.grSpr!=lastSpr){
                                if (SpriteGroupsAll.x > (tmp.x - tmp.getElement().width - 50)
                                        && (SpriteGroupsAll.x < (tmp.x - tmp.getElement().width + 50))
                                        && (SpriteGroupsAll.y > tmp.y - 50)
                                        && (SpriteGroupsAll.y < tmp.y + 50)) {
                                    lastSpr.setDxDy(tmp.x - tmp.getElement().width-SpriteGroupsAll.x,tmp.y-SpriteGroupsAll.y);
                                    lastSpr.update(0);
                                    lastSpr.addGroup(tmp.grSpr);
                                    //Assets.sprites.remove(tmp.grSpr);
                                    //tmp.left=true;
                                    //SpriteGroupsAll.right=true;
                                    puzzleUp=true;
                                    break;
                                }
                            }
                        }
                    }



                    if (puzzleUp){
                        if (lastSpr.groupsprites.size()==countPuzlle)
                            Assets.fanfari.play(1);
                        else
                            Assets.clickOk.play(1);
                    }
                    else {
                        Assets.clickUp.play(1);
                        lastSpr.update(0);
                    }

                }
                if (event.x>1285 && event.y<32) {
                    Assets.clickDown.play(1);
                    game.setScreen(new PuzzleSelect(game));
                }
                if (event.x>1220 && event.x<1275 && event.y<32) {
                    Assets.clickDown.play(1);
                    channel+=1;
                    if (channel==3)
                        channel=0;
                }
            } else if (event.type == Input.TouchEvent.TOUCH_MOVE){
                if (lastSpr != null){
                    lastSpr.setDxDy(event.x - lastX,event.y - lastY);
                    lastSpr.update(0);
                    lastX = event.x;
                    lastY = event.y;
                }

            }
        }
    }

    float startTime;
    @Override
    public void present(float deltaTime) {
        Graphics g=game.getGraphics();
        g.clear(Color.BLACK);
        contur.setColor(Color.YELLOW);
        contur.setStyle(Paint.Style.STROKE);
        g.getCanvas().drawRect(32,32,1334,784,contur);
        g.getCanvas().drawBitmap(Assets.back,1290,0,null);
        g.getCanvas().drawBitmap(Assets.help,1230,0,null);

        if (channel==0){
            for (GroupSprites tmp : Assets.sprites) {
                tmp.draw(g.getCanvas());
            }
        } else if (channel==1){
            g.getCanvas().drawBitmap(Assets.puzzleOriginal,295,150,null);
            startTime+=deltaTime;
            if (startTime>5){
                channel=0;
                startTime=0;
            }
        } else if (channel==2){
            g.getCanvas().drawBitmap(Assets.puzzleOriginal,33,33,fon);
            for (GroupSprites tmp : Assets.sprites) {
                tmp.draw(g.getCanvas());
            }
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        Assets.puzzleOriginal.recycle();
        Assets.pazzles.clear();
        Assets.sprites.clear();
    }
}

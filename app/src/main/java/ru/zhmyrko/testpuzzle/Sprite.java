package ru.zhmyrko.testpuzzle;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by Konstantin on 18.09.2017.
 */

public class Sprite {

    ElementPuzzle element;

    float x,y;

    int ax, ay;

    float dx, dy;

    boolean selected;

    GroupSprites grSpr;

    public boolean left, right, bot, top; //наличие детали у стороны
    public int leftP, rightP, botP, topP; //номер соседней детали, обязательна проверка наличия

    public Sprite(int x, int y, ElementPuzzle element, GroupSprites grSpr) {
        this.element=element;
        this.x = x;
        this.y = y;
        this.grSpr=grSpr;

        left=element.lS;
        right=element.rS;
        top=element.tS;
        bot=element.bS;

        int center = Assets.pazzles.indexOf(element); //номер текущей детали

        if ((center - Assets.wPice) >= 0)
            topP=center - Assets.wPice;

        if ((center + Assets.wPice) < (Assets.wPice * Assets.hPice))
            botP=center + Assets.wPice;

        if ((center % Assets.wPice) != 0)
            leftP=center-1;

        if (((center + 1) % Assets.wPice) != 0)
            rightP=center+1;

        element.sprite=this;

    }

    public Sprite chGroup(GroupSprites newGroup){
        grSpr=newGroup;
        return this;
    }

    public void update(long dt){
        this.x=this.dx+this.x;
        //this.dx=0;
        this.y=this.dy+this.y;
        //this.dy=0;
        //this.dx=this.dx+this.dx*this.ax*dt;
        //this.dy=this.dy+this.dy*this.ax*dt;

        //dx, dy вынести в настройки
        if (this.dx<1) {
            this.dx=0;
        }
        if (this.dy<1) {
            this.dy=0;
        }
    }

    public void update(float dx, float dy){
        this.x=this.x+dx;
        this.y=this.y+dy;
        this.dx=0;
        this.dy=0;
    }

    public boolean isSelected (float x, float y){
        selected=false;

        if (x>this.x && x<(this.x+element.width) && y>this.y && y<(this.y+element.height)){
            selected=true;
        }
        return selected;
    }

    void draw (Canvas c){
        c.drawBitmap(element.puzzle, x-element.convX, y-element.convY, null);
    }

    public ElementPuzzle getElement() {
        return element;
    }
}

package ru.zhmyrko.testpuzzle;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Konstantin on 04.10.2017.
 */

public class GroupSprites{

    float x,y;
    float dx,dy;

    float xEnd, yEnd;
    float tmp;

    public ArrayList<Sprite> groupsprites=new ArrayList<>();

    public GroupSprites(int x, int y, ElementPuzzle element) {
        groupsprites.add(new Sprite(x,y,element,this));
        this.x=x;
        this.y=y;
        this.xEnd=x+element.width;
        this.yEnd=y+element.height;
    }

    public boolean isSelected(float x, float y) {
        for (int i = groupsprites.size() - 1; i >= 0; i--) {
            if (groupsprites.get(i).isSelected(x,y)){
                return true;
            }
        }
        return false;
    }

    public void update(long dt){
        for (int i = groupsprites.size() - 1; i >= 0; i--) {
            groupsprites.get(i).update(dx,dy);
        }
    }

    Sprite chSpr, chGr;

    void draw (Canvas c){
        for (int i = groupsprites.size() - 1; i >= 0; i--) {
            groupsprites.get(i).draw(c);
        }
    }

    public void addGroup (GroupSprites group){

        if (group.x<x){
            x=group.x;
        }
        if (group.y<y) {
            y=group.y;
        }
        if (group.yEnd>yEnd) {
            yEnd=group.yEnd;
        }
        if (group.xEnd>xEnd) {
            xEnd=group.xEnd;
        }
        for (int i=group.groupsprites.size()-1;i>=0;i--) {
            groupsprites.add(group.groupsprites.remove(i).chGroup(this));
        }

    }

    public void setDxDy(float dxx, float dyy){

        if (x+dxx<=34){
            dx=34-x;
        }
        else if (xEnd+dxx>=1334){
            dx=1334-xEnd;
        }
        else {
            dx=dxx;
        }
        x=x+dx;
        xEnd=xEnd+dx;

        if (y+dyy<=34){
            dy=34-y;
        }
        else if (yEnd+dyy>=784){
            dy=784-yEnd;
        }
        else {
            dy=dyy;
        }
        y=y+dy;
        yEnd=yEnd+dy;

    }

    public ElementPuzzle getElement(){
        return groupsprites.get(0).getElement();
    }

}

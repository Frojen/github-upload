package ru.zhmyrko.framework;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin on 19.09.2017.
 */

public class TouchHandler implements View.OnTouchListener{
    boolean[] isTouched=new boolean[20];
    int[] touchX=new int [20];
    int[] touchY=new int [20];
    Pool<Input.TouchEvent> touchEventPool;

    List<Input.TouchEvent> touchEvents=new ArrayList<>();
    List<Input.TouchEvent> touchEventsBuffer=new ArrayList<>();

    float scaleX;
    float scaleY;

    public TouchHandler(View view, float scaleX, float scaleY){
        Pool.PoolObjectFactory<Input.TouchEvent> factory=new Pool.PoolObjectFactory<Input.TouchEvent>() {
            @Override
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }
        };
        touchEventPool=new Pool<>(factory,100);
        view.setOnTouchListener(this);

        this.scaleX=scaleX;
        this.scaleY=scaleY;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        synchronized (this){
            Log.d("click", "onTouch: X="+motionEvent.getX() +" Y="+motionEvent.getY());
            int action=motionEvent.getAction()&MotionEvent.ACTION_MASK;
            int pointerIndex=(motionEvent.getAction()&
                    MotionEvent.ACTION_POINTER_INDEX_MASK)
                    >>MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            int pointerId=motionEvent.getPointerId(pointerIndex);
            Input.TouchEvent touchEvent;

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchEvent=touchEventPool.newObject();
                    touchEvent.type= Input.TouchEvent.TOUCH_DOWN;
                    touchEvent.pointer=pointerId;
                    touchEvent.x=touchX[pointerId]=(int) (motionEvent.getX(pointerIndex)*scaleX);
                    touchEvent.y=touchY[pointerId]=(int) (motionEvent.getY(pointerIndex)*scaleY);
                    isTouched[pointerId]=false;
                    touchEventsBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchEvent=touchEventPool.newObject();
                    touchEvent.type= Input.TouchEvent.TOUCH_UP;
                    touchEvent.pointer=pointerId;
                    touchEvent.x=touchX[pointerId]=(int) (motionEvent.getX(pointerIndex)*scaleX);
                    touchEvent.y=touchY[pointerId]=(int) (motionEvent.getY(pointerIndex)*scaleY);
                    isTouched[pointerId]=false;
                    touchEventsBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int pointerCount = motionEvent.getPointerCount();
                    for (int i = 0; i < pointerCount; i++) {
                        pointerIndex = i;
                        pointerId = motionEvent.getPointerId(pointerIndex);
                        if (isTouched[i] && (touchEventsBuffer.size()>0)&&(touchEventsBuffer.get(touchEventsBuffer.size()-1).pointer==i)&&
                                (touchEventsBuffer.get(touchEventsBuffer.size()-1).type==Input.TouchEvent.TOUCH_MOVE)){
                            touchEvent=touchEventsBuffer.get(touchEventsBuffer.size()-1);
                            touchEvent.x = touchX[pointerId]=(int) (motionEvent.getX(pointerIndex)*scaleX);
                            touchEvent.y = touchY[pointerId]=(int) (motionEvent.getY(pointerIndex)*scaleY);
                        } else {
                            touchEvent = touchEventPool.newObject();
                            touchEvent.type = Input.TouchEvent.TOUCH_MOVE;
                            touchEvent.pointer = pointerId;
                            touchEvent.x = touchX[pointerId]=(int) (motionEvent.getX(pointerIndex)*scaleX);
                            touchEvent.y = touchY[pointerId]=(int) (motionEvent.getY(pointerIndex)*scaleY);
                            touchEventsBuffer.add(touchEvent);
                        }
                    }
                    break;
            }
            return true;
        }

    }

    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= 20)
                return false;
            else
                return isTouched[pointer];
        }
    }

    public int getTouchX(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= 20)
                return 0;
            else
                return touchX[pointer];
        }
    }

    public int getTouchY(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= 20)
                return 0;
            else
                return touchY[pointer];
        }
    }

    public List<Input.TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++)
                touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

}

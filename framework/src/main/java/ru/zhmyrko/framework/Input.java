package ru.zhmyrko.framework;

import java.util.List;

/**
 * Created by Konstantin on 19.09.2017.
 */

public interface Input {

    boolean isTouchDown(int keyCode);

    int getTouchX(int pointer);
    int getTouchY(int pointer);
    List<TouchEvent> getTouchEvents();

    /**
     * Created by Konstantin on 20.09.2017.
     */

    static class TouchEvent {
        public static final int TOUCH_DOWN=0;
        public static final int TOUCH_UP=1;
        public static final int TOUCH_MOVE=2;

        public int type;
        public int x,y;
        public int pointer;
    }
}
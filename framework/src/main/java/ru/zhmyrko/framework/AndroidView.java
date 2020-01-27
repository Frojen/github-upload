package ru.zhmyrko.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static java.lang.Thread.sleep;

/**
 * Created by Konstantin on 20.09.2017.
 */

public class AndroidView extends SurfaceView implements Runnable  {
    AndroidGame game;
    Bitmap framebuffer;
    Thread renderThread=null;
    SurfaceHolder holder;
    volatile boolean running=false;

    public AndroidView(AndroidGame game, Bitmap framebuffer) {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder=getHolder();
    }

    public void resume() {
        running=true;
        renderThread=new Thread(this);
        renderThread.start();
    }

    public void pause() {
        running = false;
        while (true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e){

            }
        }
    }

    @Override
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while(running) {
            if(!holder.getSurface().isValid())
                continue;

            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().present(deltaTime);
            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(framebuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);
            long sleepTime=50000-(System.nanoTime()-startTime);
            try {
                if (sleepTime>0)
                    sleep(sleepTime);
            } catch (Exception e){

            }

        }
    }

}

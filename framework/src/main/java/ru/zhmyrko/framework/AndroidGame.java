package ru.zhmyrko.framework;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Konstantin on 20.09.2017.
 */

public abstract class AndroidGame extends AppCompatActivity implements Game {
    AndroidView render;
    Graphics graphics;
    Screen screen;
    Input input;
    Audio audio;

    public AndroidGame() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //boolean isLandscape = getResources().getConfiguration().;
        int frameBufferWidth = 1345;//isLandscape ? 480 : 320;
        int frameBufferHeight = 810;//isLandscape ? 320 : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Bitmap.Config.RGB_565);
        float scaleX = (float) frameBufferWidth/getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight/getWindowManager().getDefaultDisplay().getHeight();

        render=new AndroidView(this, frameBuffer);
        graphics=new Graphics(getResources(),getAssets(), frameBuffer);
        audio=new AndroidAudio(this);
        input=new AndroidInput(this, render, scaleX, scaleY);
        screen=getStartScreen();
        setContentView(render);
    }


    @Override
    protected void onResume() {
        super.onResume();
        screen.resume();
        render.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        render.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen не может быть null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }
}

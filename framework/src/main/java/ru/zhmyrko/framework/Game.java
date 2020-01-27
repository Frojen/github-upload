package ru.zhmyrko.framework;

import java.util.Scanner;

/**
 * Created by Konstantin on 19.09.2017.
 */

public interface Game {
    public Input getInput();
    public Graphics getGraphics();
    public Audio getAudio();
    public void setScreen(Screen screen);
    public Screen getCurrentScreen();
    public Screen getStartScreen();
}

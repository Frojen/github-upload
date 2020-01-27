package ru.zhmyrko.testpuzzle;

import ru.zhmyrko.framework.AndroidGame;
import ru.zhmyrko.framework.Screen;

/**
 * Created by Konstantin on 20.09.2017.
 */

public class PuzzleGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        return new PuzzleSelect(this);
    }

}

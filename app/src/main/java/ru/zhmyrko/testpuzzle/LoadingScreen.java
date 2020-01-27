package ru.zhmyrko.testpuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.zhmyrko.framework.*;

/**
 * Created by Konstantin on 20.09.2017.
 */

public class LoadingScreen extends Screen {
    public LoadingScreen(ru.zhmyrko.framework.Game game) {
        super(game);
        Graphics g=game.getGraphics();
        Assets.puzzleOriginal=g.newBitmap("puzzles/puzzle1.png");

    }

    @Override
    public void update(float deltaTime) {

        game.setScreen(new PuzzleScreen(game));
    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

package ru.zhmyrko.testpuzzle;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import ru.zhmyrko.framework.*;

/**
 * Created by Konstantin on 22.09.2017.
 */

class PuzzleSelect extends Screen {
    private List<Bitmap> puzzleMenu= new ArrayList<>();
    private boolean ready=false;
    public PuzzleSelect(ru.zhmyrko.framework.Game game) {
        super(game);
        Graphics g=game.getGraphics();
        puzzleMenu.add(Bitmap.createScaledBitmap(g.newBitmap("puzzles/puzzle1.png"), 240, 150, true));
        puzzleMenu.add(Bitmap.createScaledBitmap(g.newBitmap("puzzles/puzzle2.png"), 240, 150, true));
        puzzleMenu.add(Bitmap.createScaledBitmap(g.newBitmap("puzzles/puzzle3.png"), 240, 150, true));
        puzzleMenu.add(Bitmap.createScaledBitmap(g.newBitmap("puzzles/puzzle4.png"), 240, 150, true));

        Assets.clickDown=game.getAudio().newSound("sound/clickDown.wav");
        Assets.clickOk=game.getAudio().newSound("sound/clickOk.wav");
        Assets.clickUp=game.getAudio().newSound("sound/clickUp.wav");
        Assets.fanfari=game.getAudio().newSound("sound/fanfari.wav");
        Assets.back=g.newBitmap("graphics/back.png");
        Assets.help=g.newBitmap("graphics/help.png");
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        Graphics g=game.getGraphics();
        for(int k = 0; k < len; k++){
            Input.TouchEvent event = touchEvents.get(k);
            if(event.type == Input.TouchEvent.TOUCH_UP){
                if (event.x>290&&event.x<540&&event.y>166&&event.y<316) {
                    Assets.clickDown.play(1);
                    Assets.puzzleOriginal = g.newBitmap("puzzles/puzzle1.png");
                    for (Bitmap tmp : puzzleMenu) {
                        tmp.recycle();
                    }
                    game.setScreen(new PuzzleScreen(game));
                } else if (event.x>820&&event.x<1060&&event.y>166&&event.y<316){
                    Assets.clickDown.play(1);
                    Assets.puzzleOriginal = g.newBitmap("puzzles/puzzle2.png");
                    for (Bitmap tmp : puzzleMenu) {
                        tmp.recycle();
                    }
                    game.setScreen(new PuzzleScreen(game));
                } else if (event.x>290&&event.x<540&&event.y>482&&event.y<632){
                    Assets.clickDown.play(1);
                    Assets.puzzleOriginal = g.newBitmap("puzzles/puzzle3.png");
                    for (Bitmap tmp : puzzleMenu) {
                        tmp.recycle();
                    }
                    game.setScreen(new PuzzleScreen(game));
                } else if (event.x>820&&event.x<1060&&event.y>482&&event.y<632){
                    Assets.clickDown.play(1);
                    Assets.puzzleOriginal = g.newBitmap("puzzles/puzzle4.png");
                    for (Bitmap tmp : puzzleMenu) {
                        tmp.recycle();
                    }
                    game.setScreen(new PuzzleScreen(game));
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        if (!ready) {
            Graphics g=game.getGraphics();
            g.clear(Color.BLACK);
            g.getCanvas().drawBitmap(puzzleMenu.get(0),290,166,null);
            g.getCanvas().drawBitmap(puzzleMenu.get(1),820,166,null);
            g.getCanvas().drawBitmap(puzzleMenu.get(2),290,482,null);
            g.getCanvas().drawBitmap(puzzleMenu.get(3),820,482,null);
            ready=true;
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

    }
}

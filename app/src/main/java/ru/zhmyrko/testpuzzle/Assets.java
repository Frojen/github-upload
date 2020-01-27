package ru.zhmyrko.testpuzzle;

import android.graphics.Bitmap;

import java.util.ArrayList;

import ru.zhmyrko.framework.Sound;

/**
 * Created by Konstantin on 20.09.2017.
 */

public class Assets {

    public static Bitmap puzzleOriginal;
    public static Bitmap help;
    public static Bitmap back;

    public static int size;
    public static final int VARIANT=5;
    public static int wPice=8;
    public static int hPice=5;

    public static Sound clickUp;
    public static Sound clickDown;
    public static Sound clickOk;
    public static Sound fanfari;

    public static ArrayList<ElementPuzzle> pazzles=new ArrayList<>();
    public static ArrayList<GroupSprites> sprites=new ArrayList<>();

}

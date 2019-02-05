package com.example.jayeonyi.minesweeper;

import android.content.Context;
import android.graphics.Typeface;

import static java.security.AccessController.getContext;

public class FontAwesome {
    static String path = "fonts/fasolid900.ttf";
    public FontAwesome(){ }
    public static Typeface FAInstance(Context context){
        return Typeface.createFromAsset(context.getAssets(),path);
    }
}

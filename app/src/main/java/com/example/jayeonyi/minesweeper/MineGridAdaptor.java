package com.example.jayeonyi.minesweeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.AttributedCharacterIterator;

/**
 * TODO: See GameplaySquare.
 */
public class MineGridAdaptor extends BaseAdapter{

    private Context mContext;
    private int width,height;
    private MineGridData mineGridData;
    public MineGridAdaptor(Context context, int w, int h){
        mContext = context;
        width = w;
        height = h;
        mineGridData = new MineGridData(w,h,w*h/6);
    }

    @Override
    public int getCount() {
        return width*height;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*GameplaySquare gameplaySquare;
        if(convertView==null){
            gameplaySquare = new GameplaySquare(mContext,);
            gameplaySquare.setLayoutParams(new ViewGroup.LayoutParams(10,10));
        }
        else gameplaySquare = (GameplaySquare) convertView;
        return gameplaySquare;*/
        return convertView;
    }
}
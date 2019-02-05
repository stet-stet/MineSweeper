package com.example.jayeonyi.minesweeper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO: document your custom view class.
 */
public class GameplaySquare extends AppCompatImageView {
    private Drawable[] gameplaySquares = new Drawable[4];
    private byte content; // -1 == MINE
    private final int defaultSize=20;
    private final int unopenedIndex=10;
    private final int flaggedIndex=11;
    private final int hatenaIndex=12;
    private final squareStates defaultState = squareStates.OPEN;
    enum squareStates{
        OPEN, CLOSED, HATENA, FLAGGED
    };
    private squareStates squareState;

    public GameplaySquare(Context context) {
        super(context);
        init(null, 0, -1);
    }

    public GameplaySquare(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0,-1);
    }

    public GameplaySquare(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle,-1);
    }

    public GameplaySquare(Context context, AttributeSet attrs, int defStyle, int defaultVal) {
        super(context, attrs, defStyle);
        init(attrs, defStyle, defaultVal);

    }

    private void init(AttributeSet attrs, int defStyle, int defaultValue) {
        // Load attributes

        if(defaultValue==-1) defaultValue=9;
        final TypedArray a = getResources().obtainTypedArray(R.array.GameplaySquare);
        try{
            gameplaySquares[0] = a.getDrawable(defaultValue);
            gameplaySquares[1] = a.getDrawable(unopenedIndex);
            gameplaySquares[2] = a.getDrawable(flaggedIndex);
            gameplaySquares[3] = a.getDrawable(hatenaIndex);
        }finally{
            a.recycle();
        }
        setContent((byte)defaultValue);
        setSquareState(defaultState);
        resizeImage(defaultSize);
    }



    private void resetImage(){
        if(getSquareState()==squareStates.OPEN){
            setImageDrawable(gameplaySquares[0]);
        } else if(getSquareState()==squareStates.CLOSED){
            setImageDrawable(gameplaySquares[1]);
        } else if(getSquareState()==squareStates.FLAGGED){
            setImageDrawable(gameplaySquares[2]);
        } else if(getSquareState()==squareStates.HATENA){
            setImageDrawable(gameplaySquares[3]);
        }
    }
    public void resizeImage(int size){
        setAdjustViewBounds(true);
//        setLayoutParams(new ViewGroup.LayoutParams(size,size));
        setMaxHeight(size);
        setMaxWidth(size);
        setMinimumHeight(size);
        setMinimumWidth(size);
        setScaleType(ScaleType.CENTER_INSIDE);
    }
    public int getContent(){return content;}
    private void setContent(byte s){
        content=s;
    }
    public squareStates getSquareState(){return squareState;}
    public void setSquareState(squareStates s){
        squareState=s;
        resetImage();
    }
    public void buryMine(){
        setContent((byte)9);
    }
    public boolean squareHasMine(){
        return content==9;
    }
    public boolean reveal(){
        setSquareState(squareStates.OPEN);
        resetImage();
        return squareHasMine();
    }
    public void handleFButtonPress(){
        if(getSquareState()==squareStates.CLOSED) setSquareState(squareStates.FLAGGED);
        else if(getSquareState()==squareStates.FLAGGED) setSquareState(squareStates.HATENA);
        else setSquareState(squareStates.CLOSED);
    }
    public void handleCButtonPress(){
        if(getSquareState()!=squareStates.OPEN) reveal();
    }
}

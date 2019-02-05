package com.example.jayeonyi.minesweeper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.w3c.dom.Text;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class Square extends AppCompatTextView {
    private GradientDrawable sd;
    private Context ct;
    private int n;
    static Typeface fa;
    private int state = 10;
    private int locX,locY;
    final static int defaultSize=20;
    static int[] stringID={
            R.string.g_empty,
            R.string.g_one,
            R.string.g_two,
            R.string.g_three,
            R.string.g_four,
            R.string.g_five,
            R.string.g_six,
            R.string.g_seven,
            R.string.g_eight,
            R.string.g_mine,
            R.string.g_empty,
            R.string.g_flag,
            R.string.g_hatena
    };
    static int[] colorID={
            R.color.g_empty,
            R.color.g_one,
            R.color.g_two,
            R.color.g_three,
            R.color.g_four,
            R.color.g_five,
            R.color.g_six,
            R.color.g_seven,
            R.color.g_eight,
            R.color.g_mine,
            R.color.g_empty,
            R.color.g_flag,
            R.color.g_hatena
    };
    private int colorClosed,colorOpen,Black,Red;
    public Square(Context context) {
        super(context);
        ct =context;
        init(null, 0);
    }

    public Square(Context context, AttributeSet attrs) {
        super(context, attrs);
        ct = context;
        init(attrs, 0);
    }

    public Square(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ct =context;
        init(attrs, defStyle);
    }

//    @Override
//    protected void onFinishInflate() {
//        sd = (GradientDrawable) getBackground();
//        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"faregular400.ttf");
//        setTypeface(tf);
//    }

    private void init(AttributeSet attrs, int defStyle) {
        fa = FontAwesome.FAInstance(ct);
        setTypeface(fa);
        sd = (GradientDrawable) ContextCompat.getDrawable(ct,R.drawable.ms_border);
        setBackground(sd);
        setGravity(Gravity.CENTER);
        sd.setColor(ContextCompat.getColor(ct,R.color.g_open));
        resize(defaultSize);
    }
    public void resize(int size){
        setMaxHeight(size);
        setMaxWidth(size);
        setMinimumHeight(size);
        setMinimumWidth(size);
    }

    public void setContents(int content){
        if(content==-1) content=9;
        n = content;
    }
    public void setCenterText(int indicator){
        if(indicator==-1) indicator=9;
        setText(stringID[indicator]);
        setTextColor(ContextCompat.getColor(ct,colorID[indicator]));
    }

    public void setHighlight(){
        sd.setStroke(1, ContextCompat.getColor(ct,R.color.g_highlightBorder));
    }
    public void unsetHighlight(){
        sd.setStroke(1, ContextCompat.getColor(ct,R.color.black));
    }

    public boolean open(){
        setCenterText(n);
        sd.setColor(ContextCompat.getColor(ct,R.color.g_open));
        state=0;
        if(n==9) return false;
        else return true;
    }

    public void close(){
        setCenterText(0);
        sd.setColor(ContextCompat.getColor(ct,R.color.g_closed));
        state=10;
    }

    public void rightClick(){
        if(state==10) {setCenterText(11);state=11;}
        else if(state==11) {setCenterText(12);state=12;}
        else if(state==12) {setCenterText(10);state=10;}
    }

    public boolean isOpen(){
        return state<10;
    }
    public boolean isClosed(){
        return state>=10;
    }
    public boolean hasZeroAsContent(){
        return n==0;
    }
    public boolean hasMine(){
        return n==9;
    }
    public void setLocX(int x){
        locX=x;
    }
    public void setLocY(int y){
        locY=y;
    }
    public int getLocX(){return locX;}
    public int getLocY(){return locY;}

}

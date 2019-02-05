package com.example.jayeonyi.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    private int cursorLoc=0,cursorLocLimit=2;
    private enum ToggleFactor{
        SHOW,HIDE
    }

    private final int[] imageID ={
            R.id.EasyArrow,
            R.id.HardArrow,
            R.id.HelpArrow
    };
    private final int[] boardWidths = {4,30};
    private final int[] boardHeights = {4,20};
    public static final String EXTRA_WIDTH = "com.example.jayeonyi.minesweeper.WIDTH";
    public static final String EXTRA_HEIGHT = "com.example.jayeonyi.minesweeper.HEIGHT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button upButton = (Button)findViewById(R.id.imageViewUp);
        Button downButton = (Button)findViewById(R.id.imageViewDown);
        Button CButton = (Button)findViewById(R.id.ClickButton);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCursorUp();
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCursorDown();
            }
        });
        CButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonPress(v);
            }
        });
    }
    private void reRenderCursor(){
        for(int i=0;i<=cursorLocLimit;i++) {
            ImageView temp = (ImageView) findViewById(imageID[i]);
            if(i==cursorLoc) temp.setVisibility(VISIBLE);
            else {
                System.out.println("INVISIBLE!"+i);
                temp.setVisibility(INVISIBLE);
            }
        }
        if(cursorLoc==cursorLocLimit) toggleHelp(ToggleFactor.SHOW);
        else toggleHelp(ToggleFactor.HIDE);
    }
    public void moveCursorUp(){
        if(cursorLoc==0) cursorLoc=cursorLocLimit;
        else --cursorLoc;
        reRenderCursor();
    }
    public void moveCursorDown(){
        if(cursorLoc==cursorLocLimit) cursorLoc=0;
        else ++cursorLoc;
        reRenderCursor();
    }

    public void toggleHelp(ToggleFactor tf){
        int vis = (tf == ToggleFactor.SHOW ? VISIBLE : INVISIBLE);
        ((TextView) findViewById(R.id.helpPlusShapedKeypad)).setVisibility(vis);
        ((TextView) findViewById(R.id.helpObjective)).setVisibility(vis);
        ((TextView) findViewById(R.id.helpButtonF)).setVisibility(vis);
        ((TextView) findViewById(R.id.helpButtonC)).setVisibility(vis);
    }

    public void handleButtonPress(View v){
        if(cursorLoc==cursorLocLimit) return;
        else startGame(v);
    }

    public void startGame(View view){
        Intent intent = new Intent(this, GameplayScreen.class);
        intent.putExtra(EXTRA_WIDTH,boardWidths[cursorLoc]);
        intent.putExtra(EXTRA_HEIGHT,boardHeights[cursorLoc]);
        startActivity(intent);
    }
}

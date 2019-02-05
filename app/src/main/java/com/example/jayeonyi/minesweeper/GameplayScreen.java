package com.example.jayeonyi.minesweeper;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO: Test this game on my phone
 * TODO: Make this run faster
 * TODO: Implement game clear & game over views
 * TODO: Implement share functions
 */
public class GameplayScreen extends AppCompatActivity {

    private MineGridData mineGridData;
    private int curX,curY;
    private int nonMineCounter=0;
    private int mineSquareNo;
    private Square[][] mineGridBoard;
    private int w,h;
    static int[] dx={1,1,1,0,0,-1,-1,-1};
    static int[] dy={0,-1,1,-1,1,0,-1,1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_screen);

        Intent intent = getIntent();
        int width=intent.getIntExtra(MainActivity.EXTRA_WIDTH,30);
        int height=intent.getIntExtra(MainActivity.EXTRA_HEIGHT,20);
        System.out.println(width);
        System.out.println(height);
        initGridLayout(width,height);
        attachSignalHandlers();
    }

//    private void initGridLayout(int width,int height){
//        mineGridData = new MineGridData(width,height);
//        GridLayout mineGridLayout = findViewById(R.id.MineGridLayout);
//        mineGridLayout.setColumnCount(width);
//        mineGridLayout.setRowCount(height);
//        Point dims = new Point();
//        getWindowManager().getDefaultDisplay().getSize(dims);
//        int mineSize = Math.min(17*dims.x/width/20,17*dims.y/height/20);
//        for(int y=0;y < height;++y)
//            for (int x = 0; x<width ; ++x) {
//                GameplaySquare tempSquare = new GameplaySquare(this,null,0,mineGridData.getContent((byte)y,(byte)x));
//                tempSquare.resizeImage(mineSize);
//                GridLayout.Spec xSpec = GridLayout.spec(x,1);
//                GridLayout.Spec ySpec = GridLayout.spec(y,1);
//                //GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(xSpec,ySpec);
//                mineGridLayout.addView(tempSquare);
//            }
//        System.out.println(mineGridLayout.getMeasuredHeight());
//        System.out.println(mineGridLayout.getHeight());
//    }

    private void initGridLayout(int width,int height){
        mineSquareNo = width*height/6;
        mineGridData = new MineGridData(width,height,mineSquareNo);
        mineGridBoard = new Square[height][width];
        w=width; h=height;

        GridLayout mineGridLayout = findViewById(R.id.MineGridLayout);
        mineGridLayout.setColumnCount(width);
        mineGridLayout.setRowCount(height);
        Point dims = new Point();
        getWindowManager().getDefaultDisplay().getSize(dims);
        int mineSize = Math.min(17*dims.x/width/20,17*dims.y/height/20);
        for(int y=0;y < height;++y)
            for (int x = 0; x<width ; ++x) {
                mineGridBoard[y][x] = new Square(this);
                mineGridBoard[y][x].setLocX(x);
                mineGridBoard[y][x].setLocY(y);
                mineGridBoard[y][x].setCenterText(mineGridData.getContent((byte)y,(byte)x));
                mineGridBoard[y][x].setContents(mineGridData.getContent((byte)y,(byte)x));
                mineGridBoard[y][x].resize(mineSize);
                mineGridBoard[y][x].close();
                mineGridBoard[y][x].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveFocusTo((Square)v);
                    }
                });
                mineGridLayout.addView(mineGridBoard[y][x]);
            }
        System.out.println(mineGridLayout.getMeasuredHeight());
        System.out.println(mineGridLayout.getHeight());

        curX=width/2;
        curY=height/2;
        mineGridBoard[curY][curX].setHighlight();
    }

    private void attachSignalHandlers(){
        Button upButton = findViewById(R.id.imageViewUp);
        Button downButton = findViewById(R.id.imageViewDown);
        Button leftButton = findViewById(R.id.imageViewLeft);
        Button rightButton = findViewById(R.id.imageViewRight);
        Button CButton = findViewById(R.id.ClickButton);
        Button FButton = findViewById(R.id.FlagButton);

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveFocus(0,-1);
            }
        });
        downButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveFocus(0,1);
                }
            }
        );
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveFocus(-1, 0);
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveFocus(1, 0);
            }
        });
        CButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCButtonPress(curX,curY);
            }
        });
        FButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFButtonPress();
            }
        });

    }

    private void moveFocus(int dx,int dy){
        mineGridBoard[curY][curX].unsetHighlight();
        if(dx+curX>=0 && dx+curX<w) curX+=dx;
        if(dy+curY>=0 && dy+curY<h) curY+=dy;
        mineGridBoard[curY][curX].setHighlight();
    }

    private void moveFocusTo(Square v){
        mineGridBoard[curY][curX].unsetHighlight();
        v.setHighlight();
        curY = v.getLocY();
        curX = v.getLocX();
    }

    private void handleCButtonPress(int x,int y){
        if(mineGridBoard[y][x].hasMine()) gameOver();
        else if(mineGridBoard[y][x].isOpen()) return;
        else {
            ++nonMineCounter;
            mineGridBoard[y][x].open();
            if (mineGridBoard[y][x].hasZeroAsContent()) {
                for (int i = 0; i < 8; ++i) {
                    if (isValidSquare(x + dx[i], y + dy[i])
                            && mineGridBoard[y + dy[i]][x + dx[i]].isClosed()) {
                        handleCButtonPress(x + dx[i], y + dy[i]);
                    }
                }
            }
        }
        System.out.println(nonMineCounter);
        if( nonMineCounter + mineSquareNo == w*h) gameClear();
    }

    private boolean isValidSquare(int x,int y){
        return isValidWidth(x)&&isValidHeight(y);
    }
    private boolean isValidWidth(int x){
        return (0<=x&&x<w);
    }
    private boolean isValidHeight(int y){
        return (0<=y&&y<h);
    }
    private void handleFButtonPress(){
        mineGridBoard[curY][curX].rightClick();
    }

    private void gameOver(){
        System.out.println("Game OVER!");
    }
    private void gameClear(){
        System.out.println("Game CLEAR!");
    }

}

package com.example.jayeonyi.minesweeper;

import android.content.Context;
import android.widget.GridLayout;
import com.example.jayeonyi.minesweeper.GameplaySquare.squareStates;

import java.util.Random;

public class MineGridData{
    static int dx[]={1,1,1,0,0,-1,-1,-1};
    static int dy[]={1,0,-1,1,-1,1,0,-1};
    private int[][] mineGrid;
    private squareStates[][] accessibility;
    public MineGridData(){
        init(30,20,100);
    }
    public MineGridData(int squareSide){
        init(squareSide,squareSide,squareSide*squareSide/6);
    }
    public MineGridData(int width,int height,int mineNumbers){
        init(width,height,mineNumbers);
    }
    private void init(int width, int height, int mineNumbers){
        mineGrid = new int[height][width];
        accessibility = new squareStates[height][width];

        Random random = new Random();
        for(int iter=0,x,y;iter<mineNumbers;++iter){
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            }while(hasMine(y,x));
            buryMine((byte)y,(byte)x);
        }

        for(byte x=0;x<width;++x){
            for(byte y=0;y<height;++y){
                if(!hasMine(y,x)){
                    setContent(y,x,countSurroundingMine(y,x,height,width));
                }
            }
        }

        for(byte y=0;y<height;++y){
            for(byte x=0;x<width;++x){
                setAccessibility(y,x,squareStates.CLOSED);
               //System.out.print(mineGrid[y][x]);
            }
            //System.out.println();
        }
    }
    private int countSurroundingMine(int y,int x,int height,int width){
        int count=0;
        for(int iter=0;iter<8;++iter)
            count += countMine(y+dy[iter],x+dx[iter],height,width);
        return count;
    }
    private int countMine(int y,int x,int height,int width){
        if(x<0 || y<0 || y>=height || x>=width) return 0;
        if(hasMine(y,x)) return 1;
        return 0;
    }
    public boolean hasMine(int y, int x){
        return mineGrid[y][x]==-1;
    }
    public void buryMine(byte y,byte x){
        mineGrid[y][x]=-1;
    }
    public void setContent(byte y,byte x,int content){
        mineGrid[y][x]=content;
    }
    public int getContent(byte y,byte x){
        return mineGrid[y][x];
    }
    public void setAccessibility(byte y, byte x, squareStates state){
        accessibility[y][x]=state;
    }
    public squareStates getAccessibility(byte x,byte y) {
        return accessibility[x][y];
    }
}

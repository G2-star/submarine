package cn.tedu.submarine;

import javax.swing.*;

public class Bomb extends SeaObject{


    public Bomb(int x,int y){
        super(9,12,x,y,3);
    }
    public void move(){
        y+=speed;

    }
    public ImageIcon getImage(){
        return Images.bomb;
    }

    /**检测深水炸弹是否越界*/
    public boolean isOutOfBounds(){
        return y>=World.HEIGHT;
    }
}

package cn.tedu.submarine;

import javax.swing.*;

//
public class Mine extends SeaObject {

    public Mine(int x,int y){
        super(11,11,x,y,1);//水雷的坐标不能固定，得根据水雷潜艇坐标确定
    }
    public void move(){
        y-=speed;

    }
    public ImageIcon getImage(){
        return Images.mine;
    }

    /**检测水雷越界*/
    public boolean isOutOfBounds(){
        return y<=150-this.height;
    }
}

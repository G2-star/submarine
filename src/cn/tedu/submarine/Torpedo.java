package cn.tedu.submarine;

import javax.swing.*;

public class Torpedo extends SeaObject{
    public Torpedo(int x,int y){
        super(11,11,x,y,1);//鱼雷的坐标不能固定，得根据鱼雷潜艇坐标确定
    }
    public void move(){
        y-=speed;

    }
    public ImageIcon getImage(){
        return Images.torpedo;
    }

    /**检测水雷越界*/
    public boolean isOutOfBounds(){
        return y<=150-this.height;
    }
}

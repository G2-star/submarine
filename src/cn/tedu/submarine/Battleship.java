package cn.tedu.submarine;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Battleship extends SeaObject{
    private int life = 5;

    public Battleship(){
        super(66,26,270,124,20);
    }
    public void move(){

    }

    public ImageIcon getImage(){
        return Images.battleship;
    }

    /**生成深水炸弹对象*/
    public Bomb shootBomb(){
        return new Bomb(this.x,this.y);//深水炸弹的坐标就是战舰的坐标
    }

    /**战舰左移*/
    public void moveLeft(){
        x-=speed;
    }
    /**战舰右移*/
    public void moveRight(){
        x+=speed;
    }

    /**战舰增加命*/
    public void addLife(int num){
        life+=num;
    }

    /**战舰增减命*/
    public void subtractLife(int num){
        life-=num;
    }


    /**获取命数*/
    public int getLife(){
        return life;
    }

}

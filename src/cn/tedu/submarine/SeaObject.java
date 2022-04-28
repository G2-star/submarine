package cn.tedu.submarine;

import javax.swing.*;
import java.util.Random;
import java.awt.Graphics;

public abstract class SeaObject extends Object {
    public  static final int LIVE=0;
    public  static final int DEAD=1;
    protected  int state=LIVE;


   protected int width;
   protected int height;
   protected int x;
   protected int y;
   protected int speed;

     

    //给三种潜艇提供的构造方法及参数
    public SeaObject(int width,int height) {
        this.width = width;
        this.height=height;
        x=-width;
        Random rand=new Random();
        y=rand.nextInt(World.HEIGHT-height-150+1)+150;
        speed=rand.nextInt(3)+1;
    }

    //给战舰、炸弹、水雷的提供的构造方法及参数
    public SeaObject(int width,int height,int x, int y,int speed){
        this.width=width;
        this.height=height;
        this.x=x;
        this.y=y;
        this.speed=speed;
    }

    //抽象的移动方法，子类需要重写移动参数
    public abstract void  move();

    //抽象的获取图片方法，子类需要重写获取图片的名字
    public  abstract ImageIcon getImage();

    //若为true，表示活着
    public  boolean isLive(){
        return state==LIVE;
    }
    //若为true，表示死了
    public  boolean isDead(){
        return state==DEAD;
    }
    //根据状态死活选择是否画图
    public  void paintImage(Graphics g){
        if (isLive()){
            this.getImage().paintIcon(null,g,this.x,this.y);
        }
    }

    /**检测潜艇越界*/
    public boolean isOutOfBounds(){
        return x>=World.WIDTH;
    }

    /**检测碰撞*/

    public boolean isHit(SeaObject other){
        int x1=this.x-other.width;
        int x2=this.x+this.width;
        int y1=this.y-other.height;
        int y2=this.y+this.height;
        int x=other.x;
        int y=other.y;
        return x>=x1 && x<=x2 && y>=y1 && y<=y2;
    }

    /**海洋对象状态修改*/
    public void goDead(){
        state=DEAD;
    }

//     public  boolean isHit2(SeaObject other){
//        int x1= other.x-this.width;
//        int x2= other.x+other.width;
//        int y1= other.y-this.height;
//        int y2= other.y+other.height;
//        int x=this.x;
//        int y=this.y;
//        return x>=x1 && x<=x2 && y>=y1 && y<=y2
//     }



}

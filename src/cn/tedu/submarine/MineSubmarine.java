package cn.tedu.submarine;


import javax.swing.*;

public class MineSubmarine extends SeaObject implements EnemyLife{
    @Override
    public int getLife() {
        return 1;
    }

    public MineSubmarine(){
        super(63,19);
    }


    public void move(){
        x+=speed;

    }
    public ImageIcon getImage(){
        return Images.minesubm;
    }

    /**生成水雷*/
    public Mine shootMine(){
        int x=this.x+this.width;
        int y=this.y-5;
        return new Mine(x,y);
    }
}

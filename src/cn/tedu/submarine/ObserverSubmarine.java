package cn.tedu.submarine;

import javax.swing.*;

public class ObserverSubmarine extends SeaObject implements EnemyScore{

    @Override
    public int getScore() {
        return 10;
    }

    public ObserverSubmarine(){
        super(63,19);
    }


    public void move() {
        x+=speed;

    }
    public ImageIcon getImage(){
        return Images.obsersubm;
    }


}

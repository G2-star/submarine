package cn.tedu.submarine;


import javax.swing.*;

public class TorpedoSubmarine extends SeaObject implements EnemyScore {

    @Override
    public int getScore() {
        return 40;
    }

    public TorpedoSubmarine(){
        super(64,20);
    }
    public void move(){
        x+=speed;

    }
    public ImageIcon getImage(){
        return Images.torpesubm;
    }

    /**生成鱼雷*/
    public Torpedo shootTorpedo(){
        int x=this.x+this.width;
        int y=this.y-5;
        return new Torpedo(x,y);
    }
}

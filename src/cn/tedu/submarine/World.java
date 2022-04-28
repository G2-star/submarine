package cn.tedu.submarine;



import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static cn.tedu.submarine.Images.gameover;


public class World extends JPanel {

    public static final int WIDTH = 641;//窗口的宽
    public static final int HEIGHT = 479;//窗口的高

    public static final int RUNNING = 0;
    public static final int PAUSE = 1;
    public static final int GAME_OVER = 2;
    private int state = RUNNING;//默认状态

    private Battleship ship = new Battleship();
    private SeaObject[] submarines = {};
    private Torpedo[] torpedos = {};
    private Mine[] mines = {};
    private Bomb[] bombs = {};


    /**
     * 生成潜艇对象
     */
    private SeaObject nextSubmarine() {
        Random rand = new Random();
        int type = rand.nextInt(20);
        if (type < 9) {
            return new ObserverSubmarine();
        } else if (type < 15) {
            return new TorpedoSubmarine();
        } else {
            return new MineSubmarine();
        }
    }

    /**
     * 潜艇进场
     */
    private int subEnterIndex = 0;

    private void SubmarineEnterAction() {
        subEnterIndex++;
        if (subEnterIndex % 40 == 0) {
            SeaObject obj = nextSubmarine();  //用变量obj接收生成的潜艇对象
            submarines = Arrays.copyOf(submarines, submarines.length + 1);//扩容
            submarines[submarines.length - 1] = obj;   //将obj放入数组最后一位
        }
    }

    /**
     * 水雷进场
     */
    private int mineEnterIndex = 0;

    private void mineEnterAction() {
        mineEnterIndex++;
        if (mineEnterIndex % 100 == 0) {
            for (int i = 0; i < submarines.length; i++) {
                if (submarines[i] instanceof MineSubmarine) {//判断潜艇数组生成的对象是否是水雷潜艇
                    Mine obj = ((MineSubmarine) submarines[i]).shootMine();//潜艇数组强转得到水雷潜艇调用射击水雷方法，用obj接收
                    mines = Arrays.copyOf(mines, mines.length + 1);//扩容
                    mines[mines.length - 1] = obj;//将obj放入数组最后一位
                }

            }

        }
    }

    /**
     * 鱼雷进场
     */
    private int torpedoEnterIndex = 0;

    private void torpedoEnterAction() {
        torpedoEnterIndex++;
        if (torpedoEnterIndex % 100 == 0) {
            for (int i = 0; i < submarines.length; i++) {
                if (submarines[i] instanceof TorpedoSubmarine) {//判断潜艇数组生成的对象是否是水雷潜艇
                    Torpedo obj = ((TorpedoSubmarine) submarines[i]).shootTorpedo();//潜艇数组强转得到水雷潜艇调用射击水雷方法，用obj接收
                    torpedos = Arrays.copyOf(torpedos, torpedos.length + 1);//扩容
                    torpedos[torpedos.length - 1] = obj;//将obj放入数组最后一位
                }

            }

        }
    }

    /**
     * 对象移动
     */
    private void moveAction() {
        for (int i = 0; i < submarines.length; i++) {
            submarines[i].move();
        }
        for (int i = 0; i < mines.length; i++) {
            mines[i].move();
        }
        for (int i = 0; i < bombs.length; i++) {
            bombs[i].move();
        }
        for (int i = 0; i < torpedos.length; i++) {
            torpedos[i].move();
        }
    }


    /**
     * 碰撞方法
     */
    private int score = 0;

    private void bombBangAction() {
        for (int i = 0; i < bombs.length; i++) {
            Bomb b = bombs[i];
            for (int j = 0; j < submarines.length; j++) {
                SeaObject s = submarines[j];
                if (b.isLive() && s.isLive() && s.isHit(b)) {
                    s.goDead();
                    b.goDead();

                    if (s instanceof EnemyScore) {
                        EnemyScore es = ((EnemyScore) s);
                        score += es.getScore();
                    }
                    if (s instanceof EnemyLife) {
                        EnemyLife el = ((EnemyLife) s);
                        int num = el.getLife();
                        ship.addLife(num);
                    }

                }
            }
        }
    }

    /**
     * 敌人鱼雷和水雷碰撞战舰
     */
    private void bulletBangAction() {
        for (int i = 0; i < mines.length; i++) {
            Mine m = mines[i];
            if (m.isLive() && ship.isLive() && m.isHit(ship)) {
                m.goDead();
                ship.subtractLife(1);//战舰减命1
            }
        }
        for (int i = 0; i < torpedos.length; i++) {
            Torpedo t = torpedos[i];
            if (t.isLive() && ship.isLive() && t.isHit(ship)) {
                t.goDead();
                ship.subtractLife(2);//战舰减命2
            }
        }

    }

    /**
     * 检测游戏结束
     */
    private void checkGameOverAction() {
        if (ship.getLife() <= 0) {
            state = GAME_OVER;
        }
    }


    private void action() {
        /**深水炸弹事件触发*/
        KeyAdapter k = new KeyAdapter() {

            /**重写KeyReleased()按键弹起事件*/
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (state == RUNNING) {
                        state = PAUSE;
                    } else if (state == PAUSE) {
                        state = RUNNING;
                    }
                }
                if (state != RUNNING) {
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Bomb obj = ship.shootBomb();
                    bombs = Arrays.copyOf(bombs, bombs.length + 1);
                    bombs[bombs.length - 1] = obj;

                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    ship.moveLeft();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    ship.moveRight();
                }

            }
        };
        this.addKeyListener(k);

        Timer timer = new Timer();//定时器对象
        int interval = 10;//定时间隔（以毫秒为单位）
        timer.schedule(new TimerTask() {
            @Override
            public void run() {//定时需要运行的事（每10毫秒自动执行）
                if (state == RUNNING) {
                    SubmarineEnterAction();
                    mineEnterAction();
                    torpedoEnterAction();
                    moveAction();
                    outOfBoundsAction();
                    bombBangAction();
                    bulletBangAction();
                    checkGameOverAction();
                }
                repaint();//重画
            }
        }, interval, interval);//第一个interval指执行到第一次发生的间隔，第二个interval指往后每次间隔
    }

    /**
     * 删除越界对象
     */
    private void outOfBoundsAction() {
        for (int i = 0; i < submarines.length; i++) {
            if (submarines[i].isOutOfBounds() || submarines[i].isDead()) {
                submarines[i] = submarines[submarines.length - 1];
                submarines = Arrays.copyOf(submarines, submarines.length - 1);
            }
        }
        for (int i = 0; i < bombs.length; i++) {
            if (bombs[i].isOutOfBounds()) {
                bombs[i] = bombs[bombs.length - 1];
                bombs = Arrays.copyOf(bombs, bombs.length - 1);
            }
        }
        for (int i = 0; i < mines.length; i++) {
            if (mines[i].isOutOfBounds()) {
                mines[i] = mines[mines.length - 1];
                mines = Arrays.copyOf(mines, mines.length - 1);
            }
        }
        for (int i = 0; i < torpedos.length; i++) {
            if (torpedos[i].isOutOfBounds()) {
                torpedos[i] = torpedos[torpedos.length - 1];
                torpedos = Arrays.copyOf(torpedos, torpedos.length - 1);
            }
        }
    }

    /**
     * 重写来自超类JPanel的方法，用于画图
     */
    public void paint(Graphics g) {
        Images.sea.paintIcon(null, g, 0, 0);//画海洋图
        ship.paintImage(g);
        for (int i = 0; i < submarines.length; i++) {
            submarines[i].paintImage(g);
        }
        for (int i = 0; i < mines.length; i++) {
            mines[i].paintImage(g);
        }
        for (int i = 0; i < bombs.length; i++) {
            bombs[i].paintImage(g);
        }
        for (int i = 0; i < torpedos.length; i++) {
            torpedos[i].paintImage(g);
        }
        g.drawString("SCORE:" + score, 200, 50);
        g.drawString("LIFE:" + ship.getLife(), 400, 50);

        if (state == GAME_OVER) {
            gameover.paintIcon(null, g, 0, 0);

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("潜艇大战");
        World world = new World();
        world.setFocusable(true);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH + 16, HEIGHT + 39);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);//自动调用paint方法
        world.action();




    }
}
package com.sjw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

// 游戏主面板
public class GamePane extends JPanel implements KeyListener, ActionListener {

    // 定义蛇的长度
    int length;
    int[] snakeX = new int[600];
    int[] snakeY = new int[500];
    // 初始方向
    String fx;
    // 游戏当前状态
    boolean isStart = false;

    boolean isFail = false;

    Timer timer = new Timer(100, this);

    // 食物坐标
    int foodx;
    int foody;
    Random random = new Random();

    public GamePane() {
        init();
        // 获取焦点
        this.setFocusable(true);
        // 获取键盘
        this.addKeyListener(this);
    }

    // 初始化方法
    public void init() {
        length = 3;
        fx = "R";
        // 蛇头的坐标
        snakeX[0] = 100; snakeY[0] = 100;
        // 第一节身体
        snakeX[1] = 75; snakeY[1] = 100;
        // 第三节
        snakeX[2] = 50; snakeY[2] = 100;

        foodx = 25 + 25*random.nextInt(34);
        foody = 75 + 25*random.nextInt(24);

        timer.start();
    }



    // 使用画笔
    @Override
    protected void paintComponent(Graphics g) {
        // 父级方法有清屏的作用
        super.paintComponent(g);
        this.setBackground(Color.white);
        // 广告位
        Data.header.paintIcon(this, g, 25, 11);
        //  画一个矩形
        g.fillRect(25, 75, 850, 600);

        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑", Font.BOLD, 15));
        g.drawString("长度" + length,750, 35);
        g.drawString("分数" + (length - 3) *10,750, 50);

        // 把小蛇画上去, 蛇头默认向右
        switch (fx) {
            case "R":
                Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "L":
                Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "U":
                Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "D":
                Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
        }

        // 画身体
        for (int i=1; i < length; i++) {
            Data.body.paintIcon(this,g, snakeX[i], snakeY[i]);
        }
        // 画食物
        Data.food.paintIcon(this,g,foodx, foody);
        // 游戏状态
        if(!isStart) {
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("按下空格开始游戏",300, 300);
        }

        if(isFail) {
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("失败，按下空格重新开始",300, 300);
        }

    }



    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            if(isFail) {
                // 重新开始
                isFail = false;
                init();
            } else {
                isStart = !isStart;
            }
            // 重新绘制
            repaint();
        }
        if(keyCode == KeyEvent.VK_UP) {
            // 上
            fx = "U";
        } else if (keyCode == KeyEvent.VK_DOWN) {
            // 下
            fx = "D";
        } else if(keyCode == KeyEvent.VK_LEFT) {
            fx = "L";
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            fx = "R";
        }



    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart && !isFail) {
            if(snakeX[0] == foodx && snakeY[0] == foody) {
                length++;
                // 再次生成食物
                foodx = 25 + 25*random.nextInt(34);
                foody = 75 + 25*random.nextInt(24);
            }

            // 右移
            for (int i = length -1; i >0; i--) {
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }


            //走向
            if(fx.equals("R")) {
                snakeX[0] = snakeX[0] + 25;
                if(snakeX[0] > 850) {
                    snakeX[0] = 25;
                }
            } else if(fx.equals("L")) {
                snakeX[0] = snakeX[0] - 25;
                if(snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            } else if (fx.equals("U")) {
                snakeY[0] = snakeY[0] - 25;
                if(snakeY[0] < 75) {
                    snakeY[0] = 650;
                }
            } else if (fx.equals("D")) {
                snakeY[0] = snakeY[0] + 25;
                if(snakeY[0] > 650) {
                    snakeY[0] = 75;
                }
            }

            // 失败判断
            for (int i=1; i < length; i++) {
                if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFail = true;
                }
            }


            repaint();
        }
        timer.start();
    }
}

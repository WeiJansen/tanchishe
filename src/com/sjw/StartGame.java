package com.sjw;

import javax.swing.*;

public class StartGame {
    public static void main(String[] args) {
        // 设置主窗口
        JFrame frame = new JFrame();
        frame.setTitle("贪吃蛇小游戏");
        frame.setBounds(10,10,900,750);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 窗口大小不可变
        frame.setResizable(false);

        frame.add(new GamePane());
        frame.setVisible(true);

    }
}

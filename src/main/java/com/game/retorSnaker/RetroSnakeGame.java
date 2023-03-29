package com.game.retorSnaker;

import javax.swing.*;

public class RetroSnakeGame {

    public static void main(String[] args) {

        // 创建窗口
        JFrame frame = new JFrame("贪吃蛇大作战（无尽模式）");
        // 固定窗口大小
        frame.setResizable(false);
        // 设置窗口出现位置和大小
        frame.setBounds(300,300,675,675);
        // 让窗口可关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加游戏面板的组件
        frame.add(new GamePanel());
        // 设置窗口可见性
        frame.setVisible(true);
    }
}

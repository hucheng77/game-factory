package com.game.retorSnaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    // 设置蛇头的方向
    String snakeHead;
    // 蛇的长度
    int length;
    int[] snakeX;
    int[] snakeY;
    // 游戏状态:开始=true - 停止=false
    boolean isStart = false;
    // 定时器：第一个参数就是定时执行时间
    Timer timer = new Timer(170, this);
    // 食物
    int foodX;
    int foodY;
    // 让食物出现的位置随机
    Random random = new Random();
    // 游戏是否结束:结束为=true - 游戏中=false
    boolean isFail = false;
    // 游戏是否胜利：胜利为true - 没胜利为false
    boolean isVictory = false;
    // 游戏分数
    int score;

    public GamePanel() {

        // 初始化面板
        init();
        // 设置焦点事件
        this.setFocusable(true);
        // 给面板添加键盘监听事件
        this.addKeyListener(this);
        timer.start();
    }

    public void init() {

        // 初始化蛇头向右
        snakeHead = "right";
        // 初始化蛇有三节，包括小脑袋
        length = 3;
        snakeX = new int[1000];
        snakeY = new int[1000];
        snakeX[0] = 75;
        snakeY[0] = 75;
        snakeX[1] = 50;
        snakeY[1] = 75;
        snakeX[2] = 25;
        snakeY[2] = 75;
        // 初始化实物数据
        foodX = 75 + 25 * random.nextInt(21);
        foodY = 75 + 25 * random.nextInt(19);
        // 让生成的食物不会和蛇重合
        for (int i = 0; i < length; i++) {

            if (foodX == snakeX[i] && foodY == snakeY[i]) {

                foodX = 75 + 25 * random.nextInt(21);
                foodY = 100 + 25 * random.nextInt(19);
                i = 0;
            }
        }

        // 初始化游戏分数
        score = 0;
    }

    // 画组件
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        // 设置面板背景色
        this.setBackground(Color.white);
        // 绘制游戏区域
        g.fillRect(25, 50, 600, 575);
        // 绘制蛇头
        if (snakeHead.equals("up")) {

            Data.getUp().paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (snakeHead.equals("down")) {

            Data.getDown().paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (snakeHead.equals("left")) {

            Data.getLeft().paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (snakeHead.equals("right")) {

            Data.getRight().paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        // 绘制蛇的身体
        for (int i = 1; i < length; i++) {

            Data.getBody().paintIcon(this, g, snakeX[i], snakeY[i]);
        }
        // 画食物
        Data.getFood().paintIcon(this, g, foodX, foodY);
        // 绘制局内游戏信息
        g.setColor(Color.black);
        g.setFont(new Font("宋体", Font.BOLD, 15));
        g.drawString("长度：" + length, 25, 20);
        g.drawString("分数：" + score, 25, 40);
        // 绘制游戏暂停时的 提示信息
        if (!isStart) {

            g.setColor(Color.white);
            g.setFont(new Font("楷体", Font.BOLD, 40));
            g.drawString("按下空格开始游戏！", 125, 300);
        }
        // 绘制游戏失败时的 提示信息
        if (isFail) {

            g.setColor(Color.red);
            g.setFont(new Font("楷体", Font.BOLD, 40));
            g.drawString("失败，按下空格重新开始", 125, 300);
        }
        // 绘制游戏胜利时的 提示信息

        if (isVictory) {

            g.setColor(Color.red);
            g.setFont(new Font("宋体", Font.BOLD, 35));
            g.drawString("胜利，你是为数不多的天才", 125, 300);
            g.drawString("按下空格重新开始新一轮游戏", 100, 350);
        }
    }

    // 键盘监听事件，某个键按下执行方法
    @Override
    public void keyPressed(KeyEvent e) {

        /* 当用户按下空格时：先判断游戏有没有结束， 如果游戏结束那么就重新开始 如果游戏没有结束那么就继续原来的游戏 */
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            if (isFail || isVictory) {

                isFail = false;
                isVictory = false;
                init();
            } else {

                isStart = !isStart;
            }
            repaint();
        }
        // 让用户用键盘的上下左右键来操作蛇头，
        // 并且让蛇不能直接回头，当蛇在往上走，那么就不能立刻往下走
        if (e.getKeyCode() == KeyEvent.VK_UP) {

            snakeHead = snakeHead.equals("down") ? "down" : "up";
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            snakeHead = snakeHead.equals("up") ? "up" : "down";
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            snakeHead = snakeHead.equals("right") ? "right" : "left";
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            snakeHead = snakeHead.equals("left") ? "left" : "right";
        }
    }

    // 定时执行的操作
    @Override
    public void actionPerformed(ActionEvent e) {

        // 如果游戏处于开始状态，并且没有结束，则小蛇可以移动
        if (isStart && !isFail && !isVictory) {

            // 身体移动：右移：即让后一个移到前一个的位置即可！
            for (int i = length - 1; i > 0; i--) {

                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            // 头部移动,通过方向控制，；
            if (snakeHead.equals("right")) {

                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 600) snakeX[0] = 25;
            } else if (snakeHead.equals("left")) {

                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25) snakeX[0] = 600;
            } else if (snakeHead.equals("up")) {

                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 50) snakeY[0] = 600;
            } else if (snakeHead.equals("down")) {

                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 600) snakeY[0] = 50;
            }
            // 吃食物：当蛇头和食物的坐标一致时，算吃到食物
            if (snakeX[0] == foodX && snakeY[0] == foodY) {

                length++;
                // 每吃一个食物，增加10积分
                score = score + 10;
                // 长度等于300，游戏获胜
                // 当length到达一定的值，蛇移动的速度就变快
                if (length == 300) {

                    isVictory = true;
                } else if (length == 25) {

                    timer = new Timer(145, this);
                } else if (length == 50) {

                    timer = new Timer(125, this);
                } else if (length == 100) {

                    timer = new Timer(100, this);
                } else if (length == 200) {

                    timer = new Timer(70, this);
                }
                // 重新生成食物
                foodX = 75 + 25 * random.nextInt(21);
                foodY = 75 + 25 * random.nextInt(19);
                // 让生成的食物不会和蛇重合
                for (int i = 0; i < length; i++) {

                    if (foodX == snakeX[i] && foodY == snakeY[i]) {

                        foodX = 75 + 25 * random.nextInt(21);
                        foodY = 75 + 25 * random.nextInt(19);
                        i = 0;
                    }
                }
            }
            // 结束判断，头和身体的坐标一致时，游戏结束
            for (int i = 2; i < length; i++) {

                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {

                    System.out.println(Arrays.toString(snakeX));
                    System.out.println(Arrays.toString(snakeY));
                    isFail = true;
                }
            }
            // 需要不断的更新页面实现动画
            repaint();
        }
        // 让时间动起来！
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyReleased(KeyEvent e) {


    }

}

package com.game.retorSnaker.app;

import javax.swing.*;
import java.net.URL;

public class Data {

    private static URL bodyURL = Data.class.getResource("/com/game/retorSnaker/statics/body.png");
    private static ImageIcon body = new ImageIcon(bodyURL);
    private static ImageIcon food = new ImageIcon(Data.class.getResource("/com/game/retorSnaker/statics/food.png"));

    private static ImageIcon up = new ImageIcon(Data.class.getResource("/com/game/retorSnaker/statics/up.png"));
    private static ImageIcon down = new ImageIcon(Data.class.getResource("/com/game/retorSnaker/statics/down.png"));
    private static ImageIcon left = new ImageIcon(Data.class.getResource("/com/game/retorSnaker/statics/left.png"));
    private static ImageIcon right = new ImageIcon(Data.class.getResource("/com/game/retorSnaker/statics/right.png"));

    public static ImageIcon getBody() {

        return body;
    }

    public static void setBody(ImageIcon body) {

        Data.body = body;
    }

    public static ImageIcon getUp() {

        return up;
    }

    public static void setUp(ImageIcon up) {

        Data.up = up;
    }

    public static ImageIcon getDown() {

        return down;
    }

    public static void setDown(ImageIcon down) {

        Data.down = down;
    }

    public static ImageIcon getLeft() {

        return left;
    }

    public static void setLeft(ImageIcon left) {

        Data.left = left;
    }

    public static ImageIcon getRight() {

        return right;
    }

    public static void setRight(ImageIcon right) {

        Data.right = right;
    }

    public static ImageIcon getFood() {

        return food;
    }

    public static void setFood(ImageIcon food) {

        Data.food = food;
    }
}

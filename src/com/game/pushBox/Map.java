package com.game.pushBox;

public class Map {
    int manX = 0;
    int manY = 0;
    byte map[][];
    int grade;

    public Map(int manX,int manY,byte[][] map){
        this.manX = manX;
        this.manY = manY;
        int row = map.length;
        int column = map[0].length;
        byte temp[][] = new byte[row][column];

        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                temp[i][j] = map[i][j];
            }
        }
        this.map = temp;//避免直接引用

    }

    public Map(int manX,int manY,byte[][] map,int grade){
        this(manX,manY,map);
        this.grade = grade;
    }

    //人物X坐标
    public int getManX(){
        return manX;
    }

    //人物Y坐标
    public int getManY(){
        return manY;
    }

    //地图数组
    public byte[][] getMap(){
        return map;
    }

    //关卡
    public int getGrade(){
        return grade;
    }
}




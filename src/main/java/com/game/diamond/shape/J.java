package com.game.diamond.shape;

import com.game.diamond.App.Tetris;
import com.game.diamond.block.Cell;
import com.game.diamond.block.Tetromino;

/**
 * @author xiaoZhao
 * @date 2022/5/11
 * @describe
 */
public class J extends Tetromino {
    public J() {
        cells[0] = new Cell(0,4, Tetris.J);
        cells[1] = new Cell(0,3, Tetris.J);
        cells[2] = new Cell(0,5, Tetris.J);
        cells[3] = new Cell(1,5, Tetris.J);

        states=new State[4];
        states[0]=new State(0,0,0,-1,0,1,1,1);
        states[1]=new State(0,0,-1,0,1,0,1,-1);
        states[2]=new State(0,0,0,1,0,-1,-1,-1);
        states[3]=new State(0,0,1,0,-1,0,-1,1);
    }
}

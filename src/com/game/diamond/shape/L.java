package com.game.diamond.shape;

import com.game.diamond.App.Tetris;
import com.game.diamond.block.Cell;
import com.game.diamond.block.Tetromino;

/**
 * @author xiaoZhao
 * @date 2022/5/11
 * @describe
 */
public class L extends Tetromino {
    public L() {
        cells[0] = new Cell(0,4, Tetris.L);
        cells[1] = new Cell(0,3, Tetris.L);
        cells[2] = new Cell(0,5, Tetris.L);
        cells[3] = new Cell(1,3, Tetris.L);

        states=new State[4];
        states[0]=new State(0,0,0,-1,0,1,1,-1);
        states[1]=new State(0,0,-1,0,1,0,-1,-1);
        states[2]=new State(0,0,0,1,0,-1,-1,1);
        states[3]=new State(0,0,1,0,-1,0,1,1);
    }
}

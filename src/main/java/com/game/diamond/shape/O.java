package com.game.diamond.shape;

import com.game.diamond.App.Tetris;
import com.game.diamond.block.Cell;
import com.game.diamond.block.Tetromino;

/**
 * @author xiaoZhao
 * @date 2022/5/11
 * @describe
 */
public class O extends Tetromino {
    public O() {
        cells[0] = new Cell(0, 4, Tetris.O);
        cells[1] = new Cell(0, 5, Tetris.O);
        cells[2] = new Cell(1, 4, Tetris.O);
        cells[3] = new Cell(1, 5, Tetris.O);

        //无旋转状态
        states = new State[0];
    }
}

package com.game.pushBox;

import javax.sound.midi.*;

public class Sound {

    String path = new String("resources/pushBoxImg/daoxiang.wav");
    String file = new String("daoxiang.wav");
    Sequence seq;
    Sequencer midi;
    boolean sign;

    //加载音乐
    public void loadSound(){
        new AudioPlayTest(path);
        sign = true;
    }

    void mystop(){
        midi.stop();
        midi.close();
        sign=false;
    }
    boolean isplay(){
        return sign;
    }
    void setMusic(String e){
        file=e;
    }

}



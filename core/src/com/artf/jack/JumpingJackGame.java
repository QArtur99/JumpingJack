package com.artf.jack;

public class JumpingJackGame extends BaseGame {

    int messageId;

    @Override
    public void create() {
        super.create();
        //setActiveScreen(new LevelScreen(this));
        setScreen(new MapFinalScreen(this, 1));
    }

    public void start(){
        setActiveScreen(new LevelScreen(this));
    }

    public void showMapFinalScreen(int messageId) {
        this.messageId = messageId;
        setScreen(new MapFinalScreen(this, this.messageId));
    }


}
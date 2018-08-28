package com.artf.jack;


import com.badlogic.gdx.scenes.scene2d.Stage;

public class Springboard extends BaseActor {
    public Springboard(float x, float y, float rotation, Stage s) {
        super(x, y, rotation, s);
        loadAnimationFromSheet("assets/items/springboard.png", 1, 3, 0.2f, true);
    }
}
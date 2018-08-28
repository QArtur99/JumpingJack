package com.artf.jack;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Flag extends BaseActor {
    public Flag(float x, float y, float rotation, Stage s) {
        super(x, y, rotation, s);
        loadAnimationFromSheet("assets/items/flag.png", 1, 2, 0.2f, true);
    }
}
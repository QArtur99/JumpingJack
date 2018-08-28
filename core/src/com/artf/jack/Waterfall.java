package com.artf.jack;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Waterfall extends BaseActor {
    public Waterfall(float x, float y, float rotation, Stage s, String id) {
        super(x, y, rotation, s);
        loadAnimationFromSheet(String.format("assets/waterfall/waterfall_%s.png", id), 1, 4, 0.15f, true);
    }
}

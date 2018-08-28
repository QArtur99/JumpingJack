package com.artf.jack;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Coin extends BaseActor {
    public Coin(float x, float y, float rotation, Stage s) {
        super(x, y, rotation, s);
        loadAnimationFromSheet("assets/items/coin.png", 1, 6, 0.12f, true);
    }
}
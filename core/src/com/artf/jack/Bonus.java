package com.artf.jack;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Bonus extends BaseActor
{
    public Bonus(float x, float y, float rotation, Stage s)
    {
        super(x, y, rotation, s);
        loadTexture("assets/items/gem2.png");
    }
}

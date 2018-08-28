package com.artf.jack;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Platform extends Solid
{
    public Platform(float x, float y, float rotation, float width, float height, Stage s)
    { 
        super(x, y, rotation, width, height,s);
        //loadTexture("assets/items/platform.png");
    }
}
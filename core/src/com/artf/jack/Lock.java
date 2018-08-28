package com.artf.jack;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Lock extends Solid
{
    public Lock(float x, float y, float r,Stage s)
    { 
        super(x,y, r,32,32,s);
        loadTexture("assets/items/lock.png");
    }
}
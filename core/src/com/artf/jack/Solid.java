package com.artf.jack;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Solid extends BaseActor {
    public final float top;
    public final float bottom;
    public final float left;
    public final float right;
    private boolean enabled;

    public Solid(float x, float y, float rotation, float width, float height, Stage s) {
        super(x, y, rotation, s);
        this.top = getY() + height;
        this.bottom = getY();
        this.left = getX();
        this.right = getX() + width;

        setSize(width, height);
        setOrigin(0, 15);
        setRotation(rotation);
        setBoundaryRectangle();
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean b) {
        enabled = b;
    }
}
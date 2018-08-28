package com.artf.jack;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

public class Enemy extends BaseActor {

    final long startTime;
    final float bobOffset;
    private final Solid solid;
    public Vector2 position;
    public int health;
    private Enums.Direction direction;
    private Animation stand;
    private Animation walk;

    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;

    public Enemy(Solid solid, float rotation, Stage mainStage) {
        super(solid.left, solid.top, rotation, mainStage);
        this.solid = solid;

        stand = loadTexture("assets/koala/stand.png");

        String[] walkFileNames =
                {"assets/koala/walk-1.png", "assets/koala/walk-2.png",
                        "assets/koala/walk-3.png", "assets/koala/walk-2.png"};

        walk = loadAnimationFromFiles(walkFileNames, 0.2f, true);
        startTime = TimeUtils.nanoTime();
        bobOffset = MathUtils.random();
        direction = Enums.Direction.RIGHT;
        position = new Vector2(solid.left, solid.top + Constants.ENEMY_CENTER.y);

        maxHorizontalSpeed = 50;
        walkAcceleration = 200;
        walkDeceleration = 200;
        gravity = 200;
        maxVerticalSpeed = 1000;

        setBoundaryPolygon(8);

    }

    public void act(float delta) {
        super.act(delta);



        switch (direction) {
            case LEFT:
                position.x = -200;
                accelerationVec.add(-walkAcceleration, 0);
                //position.x -= Constants.ENEMY_MOVEMENT_SPEED * delta;
                break;
            case RIGHT:
                position.x = 200;
                accelerationVec.add(walkAcceleration, 0);
               // position.x += Constants.ENEMY_MOVEMENT_SPEED * delta;
        }

        // apply gravity
        accelerationVec.add(0, -gravity);
        velocityVec.add(accelerationVec.x * delta, accelerationVec.y * delta);
        velocityVec.x = MathUtils.clamp(velocityVec.x, -maxHorizontalSpeed, maxHorizontalSpeed);

        // reset acceleration
        accelerationVec.set(0, 0);

        if (getX() < solid.left) {
            setScaleX(1);
            velocityVec.x = 10;
            direction = Enums.Direction.RIGHT;
        } else if (getX() + 32 > solid.right) {
            setScaleX(-1);
            velocityVec.x = -10;
            direction = Enums.Direction.LEFT;
        }

        // manage animations
        setAnimation(walk);


        if (this.overlaps(solid)) {
            Vector2 offset = this.preventOverlap(solid);


            if (offset != null) {
                // collided in X direction
                if (Math.abs(offset.x) > Math.abs(offset.y))
                    this.velocityVec.x = 0;
                else { // collided in Y direction
                    //this.velocityVec.y = 0;
                    final float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);;
                    final float bobMultiplier = 1 + MathUtils.sin(MathUtils.PI2 * (bobOffset + elapsedTime / Constants.ENEMY_BOB_PERIOD));
                    //velocityVec.y = Constants.ENEMY_CENTER.y + Constants.ENEMY_BOB_AMPLITUDE * bobMultiplier;
                    velocityVec.y = 100;
                }
            }
        }
        moveBy(velocityVec.x * delta, velocityVec.y * delta);


    }


}

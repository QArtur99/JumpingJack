package com.artf.jack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

public class LevelScreen extends Control {
    boolean gameOver;
    int coins;
    float time;
    boolean keydown = false;
    JumpingJackGame jumpingJackGame;

    Label coinLabel;
    Label timeLabel;
    Label messageLabel;
    Button button;
    Table keyTable;

    ArrayList<Color> keyList;

    public LevelScreen(JumpingJackGame jumpingJackGame) {
        this.jumpingJackGame = jumpingJackGame;
    }


    public void initialize() {

        TilemapActor tma = new TilemapActor("assets/map_x3.tmx", mainStage);


        for (MapObject obj : tma.getRectangleList("Solid")) {
            MapProperties props = obj.getProperties();
            Solid solid = new Solid(Float.valueOf(props.get("x").toString()),
                    Float.valueOf(props.get("y").toString()),
                    props.containsKey("rotation") ? Float.valueOf(props.get("rotation").toString()) : 0,
                    Float.valueOf(props.get("width").toString()),
                    Float.valueOf(props.get("height").toString()),
                    mainStage);

            if (props.containsKey("enemyName")) {
                Enemy enemy = new Enemy(solid
                        , props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0
                        , mainStage);
                enemy.toFront();
            }


        }

//        for (MapObject obj : tma.getEnemyPlatformList("Enemy")) {
//            MapProperties props = obj.getProperties();
//            Enemy enemy = new Enemy(Float.valueOf(props.get("x").toString()),
//                    Float.valueOf(props.get("y").toString()),
//                    Float.valueOf(props.get("width").toString()),
//                    Float.valueOf(props.get("height").toString()),
//                    mainStage);
//            enemy.toFront();
//        }


        for (MapObject obj : tma.getTileList("Waterfall")) {
            MapProperties props = obj.getProperties();
            String id = props.get("idd").toString();
            new Waterfall(Float.valueOf(props.get("x").toString()), Float.valueOf(props.get("y").toString()),
                    props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0,
                    mainStage, id);
        }

        for (MapObject obj : tma.getTileList("Flag")) {
            MapProperties props = obj.getProperties();
            new Flag(Float.valueOf(props.get("x").toString()), Float.valueOf(props.get("y").toString()),
                    props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0,
                    mainStage);
        }

        for (MapObject obj : tma.getTileList("Coin")) {
            MapProperties props = obj.getProperties();
            new Coin(Float.valueOf(props.get("x").toString()), Float.valueOf(props.get("y").toString()),
                    props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0,
                    mainStage);
        }

        for (MapObject obj : tma.getTileList("Timer")) {
            MapProperties props = obj.getProperties();
            new Timer(Float.valueOf(props.get("x").toString()), Float.valueOf(props.get("y").toString()),
                    props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0,
                    mainStage);
        }

        for (MapObject obj : tma.getTileList("Springboard")) {
            MapProperties props = obj.getProperties();
            new Springboard(Float.valueOf(props.get("x").toString()), Float.valueOf(props.get("y").toString()),
                    props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0,
                    mainStage);
        }

        for (MapObject obj : tma.getRectangleList("Platform")) {
            MapProperties props = obj.getProperties();
            MapProperties cc = props;
            new Platform(Float.valueOf(props.get("x").toString()),
                    Float.valueOf(props.get("y").toString()),
                    props.containsKey("rotation") ? Float.valueOf(props.get("rotation").toString()) : 0,
                    Float.valueOf(props.get("width").toString()),
                    Float.valueOf(props.get("height").toString()),
                    mainStage);
        }

        for (MapObject obj : tma.getTileList("Platform")) {
            MapProperties props = obj.getProperties();
            new Platform(Float.valueOf(props.get("x").toString()),
                    Float.valueOf(props.get("y").toString()),
                    props.containsKey("rotation") ? Float.valueOf(props.get("rotation").toString()) : 0,
                    Float.valueOf(props.get("width").toString()),
                    Float.valueOf(props.get("height").toString()),
                    mainStage);
        }

        for (MapObject obj : tma.getTileList("Key")) {
            MapProperties props = obj.getProperties();
            Key key = new Key(Float.valueOf(props.get("x").toString()), Float.valueOf(props.get("y").toString()),
                    props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0,
                    mainStage);
            String color = (String) props.get("color");
            if (color.equals("red"))
                key.setColor(Color.RED);
            else // default color
                key.setColor(Color.WHITE);
        }

        for (MapObject obj : tma.getTileList("Bonus")) {
            MapProperties props = obj.getProperties();
            new Bonus(Float.valueOf(props.get("x").toString()), Float.valueOf(props.get("y").toString()),
                    props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0,
                    mainStage);
        }

        for (MapObject obj : tma.getTileList("Lock")) {
            MapProperties props = obj.getProperties();
            Lock lock = new Lock(Float.valueOf(props.get("x").toString()), Float.valueOf(props.get("y").toString()),
                    props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0,
                    mainStage);
            String color = (String) props.get("color");
            if (color.equals("red"))
                lock.setColor(Color.RED);
            else // default
                lock.setColor(Color.WHITE);
        }


        gameOver = false;
        coins = 0;
        time = 300;

        coinLabel = new Label("Coins: " + coins, BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);
        keyTable = new Table();
        timeLabel = new Label("Time: " + (int) time, BaseGame.labelStyle);
        timeLabel.setColor(Color.LIGHT_GRAY);
        messageLabel = new Label("Message", BaseGame.labelStyle);
        messageLabel.setVisible(false);


        uiTable.pad(20);
        uiTable.add(coinLabel);


        uiTable.add(keyTable).expandX();
        uiTable.add(timeLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY();

        keyList = new ArrayList<Color>();

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties props = startPoint.getProperties();
        jack = new Koala(Float.valueOf(props.get("x").toString()), Float.valueOf(props.get("y").toString()),
                props.containsKey("Rotation") ? Float.valueOf(props.get("Rotation").toString()) : 0,
                mainStage, keyTable);
        jack.toFront();

//        Control onscreenControls = new Control();
//        Gdx.input.setInputProcessor(onscreenControls);
//        onscreenControls.jack = jack;
    }

    public void update(float dt) {

        if (gameOver)
            return;

        for (BaseActor flag : BaseActor.getList(mainStage, "Flag")) {
            if (jack.overlaps(flag)) {
                messageLabel.setText("You Win!");
                messageLabel.setColor(Color.LIME);
                messageLabel.setVisible(true);
                jack.remove();

                for (Actor timer : uiStage.getActors()) {
                    timer.addAction(Actions.removeActor());
                }

                for (Actor timer : uiTable.getChildren()) {
                    timer.addAction(Actions.removeActor());
                }

                BaseActor youWinMessage = new BaseActor(0, 0, 0, uiStage);
                youWinMessage.loadTexture("assets/bg_cover/bg_cover_40.png");
                //youWinMessage.centerAtPosition(uiStage.getWidth() / 2, uiStage.getHeight() / 2);
                youWinMessage.setSize(uiStage.getWidth(), uiStage.getWidth());
                youWinMessage.setOpacity(0);
                youWinMessage.addAction(Actions.delay(0.5f));
                youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));

                BaseActor youWinMessage2 = new BaseActor(0, 0, 0, uiStage);
                youWinMessage2.loadTexture("assets/you-win.png");
                float ratio = uiStage.getHeight() / 4 / youWinMessage2.getHeight();
                youWinMessage2.setHeight(uiStage.getHeight() / 4);
                youWinMessage2.setWidth(youWinMessage2.getWidth() * ratio);
                youWinMessage2.centerAtPosition(uiStage.getWidth() / 2, uiStage.getHeight() / 4 * 3);
                youWinMessage2.setOpacity(0);
                youWinMessage2.addAction(Actions.delay(0.5f));
                youWinMessage2.addAction(Actions.after(Actions.fadeIn(1)));

                createPlayAgainButton();
                //youWinMessage.addAction( Actions.after( Actions.fadeIn(1) ) );
//                BaseActor.setWorldBounds(youWinMessage);

                //jumpingJackGame.showMapFinalScreen(1);

                gameOver = true;
            }
        }

        for (BaseActor coin : BaseActor.getList(mainStage, "Coin")) {
            if (jack.overlaps(coin)) {
                coins++;
                coinLabel.setText("Coins: " + coins);
                coin.remove();
            }
        }

        time -= dt;
        timeLabel.setText("Time: " + (int) time);

        for (BaseActor timer : BaseActor.getList(mainStage, "Timer")) {
            if (jack.overlaps(timer)) {
                time += 20;
                timer.remove();
            }
        }

        if (time <= 0) {
            messageLabel.setText("Time Up - Game Over");
            messageLabel.setColor(Color.RED);
            messageLabel.setVisible(true);
            jack.remove();
            gameOver = true;
            //jumpingJackGame.showMapFinalScreen(2);
        }

        for (BaseActor springboard : BaseActor.getList(mainStage, "Springboard")) {
            if (jack.belowOverlaps(springboard) && jack.isFalling()) {
                jack.spring();
            }
        }

        for (BaseActor actor : BaseActor.getList(mainStage, "Solid")) {
            Solid solid = (Solid) actor;

            if (solid instanceof Platform) {
                // disable the solid when jumping up through
                if (jack.isJumping() && jack.overlaps(solid))
                    solid.setEnabled(false);

                // when jumping, after passing through, re-enable the solid
                if (jack.isJumping() && !jack.overlaps(solid))
                    solid.setEnabled(true);

                // disable the solid when jumping down through: code is in keyDown method

                // when falling, after passing through, re-enable the solid
                if (jack.isFalling() && !jack.overlaps(solid) && !jack.belowOverlaps(solid))
                    solid.setEnabled(true);
            }

            if (solid instanceof Lock && jack.overlaps(solid)) {
                Color lockColor = solid.getColor();
                if (keyList.contains(lockColor)) {
                    solid.setEnabled(false);
                    solid.addAction(Actions.fadeOut(0.5f));
                    solid.addAction(Actions.after(Actions.removeActor()));
                }
            }

            if (jack.overlaps(solid) && solid.isEnabled()) {
                Vector2 offset = jack.preventOverlap(solid);

                if (offset != null) {
                    // collided in X direction
                    if (Math.abs(offset.x) > Math.abs(offset.y))
                        jack.velocityVec.x = 0;
                    else // collided in Y direction
                        jack.velocityVec.y = 0;

                }
            }
        }

        for (BaseActor key : BaseActor.getList(mainStage, "Key")) {
            if (jack.overlaps(key)) {
                Color keyColor = key.getColor();
                key.remove();

                BaseActor keyIcon = new BaseActor(0, 0, 0, uiStage);
                keyIcon.loadTexture("assets/key-icon.png");
                keyIcon.setColor(keyColor);
                keyTable.add(keyIcon);

                keyList.add(keyColor);
            }
        }

        for (BaseActor key : BaseActor.getList(mainStage, "Bonus")) {
            if (jack.overlaps(key)) {
                Color keyColor = key.getColor();
                key.remove();

                BaseActor keyIcon = new BaseActor(0, 0, 0, uiStage);
                keyIcon.loadTexture("assets/items/gem2.png");
                keyIcon.setColor(keyColor);
                keyTable.add(keyIcon);
                jack.jumpBoost(keyIcon);

            }
        }

    }

    public boolean keyDown(int keyCode) {
        if (gameOver)
            return false;

        if (keyCode == Keys.SPACE) {
            // if down arrow is held while jump is pressed and koala is above a platform,
            //   then the koala can fall down through it.
            if (Gdx.input.isKeyPressed(Keys.DOWN)) {
                for (BaseActor actor : BaseActor.getList(mainStage, "Platform")) {
                    Platform platform = (Platform) actor;
                    if (jack.belowOverlaps(platform)) {
                        platform.setEnabled(false);
                    }
                }
            } else if (jack.isOnSolid()) {
                jack.jump();
            }
        }
        return false;
    }

    @Override
    public JumpingJackGame getJumpingJackGame() {
        return jumpingJackGame;
    }
}
package com.artf.jack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;


public abstract class Control extends BaseScreen {

    public static final String TAG = Control.class.getName();
    public final Viewport viewport;
    public Koala jack;
    private int controlPanelId = 1;
    private Vector2 moveSetCenter;
    private Vector2 moveLeftCenter;
    private Vector2 moveRightCenter;
    private Vector2 shootCenter;
    private Vector2 jumpCenter;
    private float BUTTON_SIZE;
    private float BUTTON_SIZE2;
    private float buttonWidthRadious;


    private Vector2 button1Center;
    private float buttonWidth;
    private float buttonHeight;


    public Control() {
        this.viewport = uiStage.getViewport();
        BUTTON_SIZE = (viewport.getScreenHeight() / 5) * 2;
        BUTTON_SIZE2 = viewport.getScreenHeight() / 6;
        buttonWidthRadious = BUTTON_SIZE / 6;

        moveSetCenter = new Vector2();
        moveLeftCenter = new Vector2();
        moveRightCenter = new Vector2();
        shootCenter = new Vector2();
        jumpCenter = new Vector2();
        recalculateButtonPositions();

        createButton(moveSetCenter, "assets/ui/transparentDark07.png", BUTTON_SIZE);
        // createButton(moveLeftCenter, "assets/ui/transparentDark22.png");
        createButton(jumpCenter, "assets/ui/transparentDark24.png", BUTTON_SIZE2);
        createButton(shootCenter, "assets/ui/transparentDark23.png", BUTTON_SIZE2);
    }

    private void createButton(Vector2 center, String path, float BUTTON_SIZE) {
        TextureRegion myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(path)));
        Button button = new Button(new TextureRegionDrawable(myTextureRegion));
        button.setSize(BUTTON_SIZE, BUTTON_SIZE);
        button.setPosition(center.x - BUTTON_SIZE / 2, center.y - BUTTON_SIZE / 2);
        uiStage.addActor(button);
    }

    public void createPlayAgainButton() {
        controlPanelId = 2;
        buttonWidth = viewport.getScreenWidth() / 3;
        buttonHeight = viewport.getScreenHeight() / 5;
        button1Center = new Vector2();
        recalculatePlayAgainButtonPositions();
        createPlayAgainButton(button1Center, "assets/ui/button.png");
    }

    private void recalculatePlayAgainButtonPositions() {
        button1Center.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 - buttonHeight / 2);
    }

    private void createPlayAgainButton(Vector2 center, String path) {
        TextureRegion myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(path)));
        ImageButton imageButton = new ImageButton(new TextureRegionDrawable(myTextureRegion));
        imageButton.getImage().setFillParent(true);

        TextButton.TextButtonStyle textButtonStyle = BaseGame.textButtonStyle;
        textButtonStyle.fontColor = Color.GOLD;
        TextButton button = new TextButton("Play again!", textButtonStyle);
        button.setColor(new Color(0x40C4FFff));
        //Button button = new Button(new TextureRegionDrawable(myTextureRegion));
        button.setSize(buttonWidth, buttonHeight);
        button.setPosition(center.x - buttonWidth / 2, center.y - buttonHeight / 2);

        button.getColor().a = 0;
        button.addAction(Actions.delay(0.5f));
        button.addAction(Actions.after(Actions.fadeIn(1)));
//        imageButton.addListener(new InputListener(){
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//
//                return super.touchDown(event, x, y, pointer, button);
//            }
//        });
        uiStage.addActor(button);
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));


        switch (controlPanelId) {
            case 1:
                if (viewportPosition.dst(shootCenter) < BUTTON_SIZE2 / 2) {
                    //gigaGal.shoot();

                }

                if (viewportPosition.dst(jumpCenter) < BUTTON_SIZE2 / 2) {
                    if (jack.isOnSolid()) {
                        jack.jump();
                    } else {
                        for (BaseActor actor : BaseActor.getList(mainStage, "Platform")) {
                            Platform platform = (Platform) actor;
                            if (jack.belowOverlaps(platform)) {
                                platform.setEnabled(false);
                            }
                        }
                    }
                }

                if (viewportPosition.dst(moveLeftCenter) < buttonWidthRadious) {
                    jack.moveLeft = true;

                } else if (viewportPosition.dst(moveRightCenter) < buttonWidthRadious) {
                    jack.moveRight = true;
                }

                break;
            case 2:
                if (Math.abs(viewportPosition.x - button1Center.x) < buttonWidth / 2 && Math.abs(viewportPosition.y - button1Center.y) < buttonHeight / 2 ) {
                    getJumpingJackGame().start();
                }
                break;
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        switch (controlPanelId) {
            case 1:
                if (viewportPosition.dst(moveRightCenter) < buttonWidthRadious) {
                    jack.moveLeft = false;
                    jack.moveRight = true;
                } else if (viewportPosition.dst(moveLeftCenter) < buttonWidthRadious) {
                    jack.moveRight = false;
                    jack.moveLeft = true;
                } else {
                    jack.moveRight = false;
                    jack.moveLeft = false;
                }
                break;
            case 2:
                break;
        }

        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));
        if(pointer == 0) {
            switch (controlPanelId) {
                case 1:
                    if (viewportPosition.dst(moveLeftCenter) < buttonWidthRadious) {
                        jack.moveLeft = false;

                    } else if (viewportPosition.dst(moveRightCenter) < buttonWidthRadious) {
                        jack.moveRight = false;
                    }
                    break;
                case 2:


                    break;
            }
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }


    public void recalculateButtonPositions() {
        moveSetCenter.set((BUTTON_SIZE * 5 / 8), (BUTTON_SIZE * 5 / 8));
        moveLeftCenter.set((BUTTON_SIZE * 2.5f / 8), BUTTON_SIZE * 5 / 8);
        moveRightCenter.set((BUTTON_SIZE * 7.5f / 8), BUTTON_SIZE * 5 / 8);
        //jumpCenter.set(BUTTON_SIZE * 1.375f, BUTTON_SIZE * 2.75f);
        jumpCenter.set(viewport.getWorldWidth() - BUTTON_SIZE2 * 0.75f, BUTTON_SIZE2 * 2.0f);
        shootCenter.set(viewport.getWorldWidth() - BUTTON_SIZE2 * 1.25f, BUTTON_SIZE2 * 0.75f);


//        moveLeftCenter.set(BUTTON_SIZE * 5 / 8, BUTTON_SIZE * 1.625f);
//        moveRightCenter.set((BUTTON_SIZE * 5 / 8) + (BUTTON_SIZE * 9 / 8), BUTTON_SIZE * 1.625f);
//        //jumpCenter.set(BUTTON_SIZE * 1.375f, BUTTON_SIZE * 2.75f);
//        jumpCenter.set(viewport.getWorldWidth() - BUTTON_SIZE * 1.0f, BUTTON_SIZE * 2.75f);
//
//        shootCenter.set(viewport.getWorldWidth() - BUTTON_SIZE * 1.0f, BUTTON_SIZE * 1.625f);

    }

    public abstract JumpingJackGame getJumpingJackGame();

}

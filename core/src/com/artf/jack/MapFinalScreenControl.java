package com.artf.jack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class MapFinalScreenControl extends BaseScreen {

    public final Viewport viewport;
    private Vector2 button1Center;
    private float buttonWidth;
    private float buttonHeight;

    public MapFinalScreenControl() {
        this.viewport = uiStage.getViewport();
        buttonWidth = viewport.getScreenWidth() / 2;
        buttonHeight = viewport.getScreenHeight() / 5;

        button1Center = new Vector2();
        recalculateButtonPositions();

        createButton(button1Center, "assets/ui/button.png");
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if (Math.abs(viewportPosition.x - button1Center.x) < buttonWidth / 2 && Math.abs(viewportPosition.y - button1Center.y) < buttonHeight / 2 ) {
            getJumpingJackGame().start();

        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    private void createButton(Vector2 center, String path) {
        TextureRegion myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal(path)));
        ImageButton imageButton = new ImageButton(new TextureRegionDrawable(myTextureRegion));
        imageButton.getImage().setFillParent(true);

        TextButton.TextButtonStyle textButtonStyle = BaseGame.textButtonStyle;
        textButtonStyle.fontColor = Color.GOLD;
        TextButton button = new TextButton("Restart", textButtonStyle);
        button.setColor(Color.FOREST);
        //Button button = new Button(new TextureRegionDrawable(myTextureRegion));

        button.setSize(buttonWidth, buttonHeight);
        button.setPosition(center.x - buttonWidth / 2, center.y - buttonHeight / 2);
//        imageButton.addListener(new InputListener(){
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//
//                return super.touchDown(event, x, y, pointer, button);
//            }
//        });
        uiStage.addActor(button);
    }

    public void recalculateButtonPositions() {
        button1Center.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2 - buttonHeight /2);
    }

    public abstract JumpingJackGame getJumpingJackGame();


}

package com.artf.jack;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MapFinalScreen extends MapFinalScreenControl {

    JumpingJackGame jumpingJackGame;
    int messageId;
    BaseActor space;


    public MapFinalScreen(JumpingJackGame jumpingJackGame, int messageId) {
        super();
        this.jumpingJackGame = jumpingJackGame;
        this.messageId = messageId;
        mainStage.getCamera().viewportHeight = 720;
        mainStage.getCamera().viewportWidth = 1200;

//        space = new BaseActor(0, 0, 0, mainStage);
//        space.loadTexture("assets/map4.png");
//        space.setSize(800, 600);
//        BaseActor.setWorldBounds(space);

        Label coinLabel = new Label("Coins: " + 0, BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);
        Label coinLabel2 = new Label("Coins: " + 0, BaseGame.labelStyle);
        coinLabel2.setColor(Color.GOLD);
        Label messageLabel = new Label("Message", BaseGame.labelStyle);
        switch (messageId){
            case 1:
                messageLabel.setText("You Win!");
                messageLabel.setColor(Color.LIME);
                break;
            case 2:
                messageLabel.setText("Time Up - Game Over");
                messageLabel.setColor(Color.RED);
                break;
        }

        uiTable.pad(20);
        uiTable.add().colspan(3).expandY();
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY().top();
        uiTable.row();
        uiTable.add().colspan(3).expandY();
        uiTable.row();
        uiTable.add().colspan(3).expandY();

    }


    @Override
    public void initialize() {

    }

    @Override
    public void update(float dt) {



        //space.alignCamera();


    }

    @Override
    public JumpingJackGame getJumpingJackGame() {
        return jumpingJackGame;
    }
}

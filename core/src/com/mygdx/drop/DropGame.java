package com.mygdx.drop;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class DropGame extends Game implements InputProcessor {

    private TextureAtlas textureAtlas;
    private Sprite tankSprite;


    private Stage stage;
    private Tank tank;


    @Override
    public void create() {
        stage = new Stage();
        tank = new Tank();
        stage.addActor(tank);
        stage.addListener(new InputListener(){
            @Override
            public boolean mouseMoved(InputEvent event, float xPos, float yPos){
                tank.rotateTurret(xPos, yPos);
                return true;
            }
            @Override
            public void touchDragged(InputEvent event, float xPos, float yPos, int input){
                tank.rotateTurret(xPos, yPos);
            }
            @Override
            public boolean touchDown(InputEvent event, float xPos, float yPos, int pointer, int button){
                tank.fire(xPos, yPos);
               return true;
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.print("keyDown");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.print("keyUp");
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.print("keyTyped");
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}


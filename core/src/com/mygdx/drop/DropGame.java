package com.mygdx.drop;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
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
                Vector3 stageCoords = stage.getCamera().project(new Vector3(xPos, yPos, 0));
                tank.handleMouseMove(stageCoords.x, stageCoords.y);
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


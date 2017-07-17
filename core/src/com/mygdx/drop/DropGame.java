package com.mygdx.drop;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class DropGame extends Game implements InputProcessor {

    private Stage stage;
    private Tank tank;
    private TiledMapRenderer mapRenderer;
    private TiledMap map;
    private OrthographicCamera mainCamera;



    @Override
    public void create() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();


        stage = new Stage();

        mainCamera = (OrthographicCamera) stage.getCamera();
        mainCamera.setToOrtho(false, width, height);
        mainCamera.update();
        map = new TmxMapLoader().load("Map/TW.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

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

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(Keys.isForwardKey(keycode)){
                    tank.moveForward();
                } else if (Keys.isBackwardKey(keycode)) {
                    tank.moveBackward();
                } else if (Keys.isLeftKey(keycode)) {
                    tank.moveLeft();
                } else if (Keys.isRightKey(keycode)){
                    tank.moveRight();
                }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(Keys.isForwardKey(keycode)){
                    tank.stopMovingForward();
                } else if (Keys.isBackwardKey(keycode)){
                    tank.stopMovingBackward();
                } else if (Keys.isLeftKey(keycode)){
                    tank.stopMovingLeft();
                } else if (Keys.isRightKey(keycode)){
                    tank.stopMovingRight();
                }
                return super.keyDown(event, keycode);
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Actions.removeActor();
        stage.act();
        mainCamera.update();
        mapRenderer.setView(mainCamera);
        mapRenderer.render();
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}


package com.mygdx.drop;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class DropGame extends Game {

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Sprite tankSprite;


    private Stage stage;


    @Override
    public void create() {
        stage = new Stage();
        Tank tank = new Tank();
        stage.addActor(tank);
//        tank.setPosition(600, 350);
        batch = new SpriteBatch();
//       textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheet/tanksprite.atlas"));
//       TextureRegion region = textureAtlas.findRegion("tankBeige");
//       tankSprite = new Sprite(region);
//       tankSprite.setPosition(400, 300);
    }

    @Override
    public void dispose() {
    }

    public void draw(Batch batch, float alpha){

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stage.draw();
        batch.end();
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


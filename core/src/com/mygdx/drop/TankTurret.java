package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by akshaysingh on 09/07/17.
 */
public class TankTurret extends Actor{
    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheet/tanksprite.atlas"));
    private TextureRegion region = textureAtlas.findRegion("barrelBeige");

    public TankTurret(){
        setBounds(0,0,region.getRegionWidth(),region.getRegionHeight());
        center();
    }

    public void center(){
        setOrigin(getWidth()/2, 0);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

    public Vector2 getOrigin() {
        return localToParentCoordinates(new Vector2(getOriginX(), getOriginY()));
    }
}

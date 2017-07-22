package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by akshaysingh on 09/07/17.
 */
public class TankBody extends PhysicsActor {
    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheet/tanksprite.atlas"));
    private TextureRegion region = textureAtlas.findRegion("tankBeige");

    public TankBody(){
        setBounds(0,0,region.getRegionWidth(),region.getRegionHeight());
        setOrigin(getWidth()/2, getHeight()/2);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }
}

package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by akshaysingh on 09/07/17.
 */
public class TankTurret extends Actor{
    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheet/tanksprite.atlas"));
    private TextureRegion region = textureAtlas.findRegion("barrelBeige");

    public TankTurret(){
        setBounds(0,0,region.getRegionWidth(),region.getRegionHeight());
    }

    public void draw(Batch batch, float alpha, float xPos, float yPos){
        batch.draw(region, xPos, yPos, getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
        System.out.println("Turret Widths are " + getWidth() + " " + region.getRegionWidth());
        System.out.println("body Heights are " + getHeight() + " " + region.getRegionHeight());
    }
}

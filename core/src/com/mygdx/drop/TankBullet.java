package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


/**
 * Created by akshaysingh on 11/07/17.
 */
public class TankBullet extends Actor{
    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheet/tanksprite.atlas"));
    private TextureRegion region = textureAtlas.findRegion("bulletBeige");

    public TankBullet(Vector2 position, Vector2 origin, float angle){
        setBounds(0,0,region.getRegionWidth(),region.getRegionHeight());
        setInitialParameters(position, origin, angle);
    }

    public TankBullet(){
        setBounds(0,0,region.getRegionWidth(),region.getRegionHeight());
    }

    public void setInitialParameters(Vector2 position, Vector2 origin, float angle){
        setPosition(position.x, position.y);
        setOrigin(origin.x, origin.y);
        setRotation(angle);
    }

    public void center(){
        setOrigin(getWidth()/2, 0);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta){
        Array<Action> actions = getActions();
        if(actions.size == 0 ) {
           //The bullet has reached its destination and is no longer needed. Bullets don't float in mid air.
            this.remove();
            return;
        }
    }
}

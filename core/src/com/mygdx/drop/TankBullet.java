package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;


/**
 * Created by akshaysingh on 11/07/17.
 */
public class TankBullet extends Actor{
    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheet/tanksprite.atlas"));
    private TextureRegion region = textureAtlas.findRegion("bulletBeige");

    public TankBullet(float height){
        setBounds(0,0,region.getRegionWidth(),region.getRegionHeight());
    }

    public TankBullet(){
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

    @Override
    public void act(float delta){
        super.act(delta);
        System.out.println("Acting");
        Array<Action> actions = getActions();
        if(actions.size == 0 ) {
           //Crossed border
            this.remove();
            return;
        }
        MoveToAction moveToAction = (MoveToAction)getActions().get(0);
        System.out.println( moveToAction.getX() + " " + getX());
    }
}

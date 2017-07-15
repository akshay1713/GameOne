package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Vector2 movement = new Vector2();
    Vector2 destination = new Vector2();

    public TankBullet(){
        setBounds(0,0,region.getRegionWidth(),region.getRegionHeight());
    }

    public TankBullet(float angle, Vector2 origin, Vector2 position, Vector2 clickPoint){
        this();
        setInitialParameters(angle, origin, position);
        System.out.println("RECEIVED " + clickPoint);

        MoveToAction fireAction = new MoveToAction();
        float currentX = getX();
        float currentY = getY();
        float newX = 2000;
        float newY = newX*currentY/currentX;
        if(signShouldChange(currentX, currentY)){
            newX = -newX;
            newY = -newY;
        }
        Vector2 local = parentToLocalCoordinates(clickPoint);
        fireAction.setPosition(newX, newY);
        fireAction.setDuration(2f);
        addAction(fireAction);
    }

    private boolean signShouldChange(float positionX, float positionY){
        if(positionX < 0 || (positionY < 0 && positionX < 0)){
            return true;
        }
       return false;
    }

    private void setInitialParameters(float angle, Vector2 origin, Vector2 position){
       setRotation(angle);
       setOrigin(origin.x, origin.y);
       setPosition(position.x, position.y);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta){
        super.act(delta);
        Array<Action> actions = getActions();
        if(actions.size == 0 ) {
           //Crossed border
            this.remove();
            return;
        }
        MoveToAction moveToAction = (MoveToAction)getActions().get(0);
        Vector2 local = localToStageCoordinates(new Vector2(getX(), getY()));
//        System.out.println( moveToAction.getX() + " " + getX() + " " + Gdx.graphics.getWidth() + " " + local.x);
    }
}

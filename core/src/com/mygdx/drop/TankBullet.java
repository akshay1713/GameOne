package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;


/**
 * Created by akshaysingh on 11/07/17.
 */
public class TankBullet extends Actor{
    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheet/tanksprite.atlas"));
    private TextureRegion region = textureAtlas.findRegion("bulletBeige");
    private  float angle;

    public TankBullet(){
        setBounds(0,0,region.getRegionWidth(),region.getRegionHeight());
        setScale(0.5f);
    }

    public TankBullet(float angle, Vector2 origin, Vector2 position, Vector2 clickVector){
        this();
        setInitialParameters(angle, origin, position);
        setAngle(clickVector);
    }

    private void setAngle(Vector2 clickVector) {
        Vector2 originInStage = localToStageCoordinates(new Vector2(getOriginX(), getOriginY()));
        Vector2 xDiff = clickVector.sub(originInStage);
        this.angle = xDiff.angle();
    }

    private void setFireAction(){
        MoveToAction fireAction = new MoveToAction();
        Vector2 destination = getDestinationVector();
        fireAction.setPosition(destination.x, destination.y);
        fireAction.setDuration(2f);
        addAction(fireAction);
    }

    //TODO: What the hell are you doing here? Use a proper way to get the destination vector
    private Vector2 getDestinationVector(){
        Vector2 destination = new Vector2();
        float currentX = getX();
        float currentY = getY();
        destination.x = 2000;
        destination.y = destination.x * currentY/currentX;
        if(signShouldChange(currentX, currentY)){
            destination.x = -destination.x;
            destination.y = -destination.y;
        }
        destination = destination.rotate(60f);
        if (destination.x < 0){
            destination = destination.rotate(-15f);
        }
        return localToStageCoordinates(destination);
    }

    private boolean signShouldChange(float positionX, float positionY){
        if(positionX < 0 || (positionY < 0 && positionX < 0)){
            return true;
        }
        return false;
    }

    private void setInitialParameters(float angle, Vector2 origin, Vector2 position){
//        origin = stageToLocalCoordinates(origin);
       setOrigin(origin.x, origin.y);
       setPosition(position.x, position.y);
       setRotation(angle);
    }

    private int getQuadrant(float angle){
        int angle2 = (int)angle;
        return (angle2/90)%4+1;
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta){
        super.act(delta);
        Vector2 current = new Vector2(getX(), getY());
        int quadrant = getQuadrant(angle);
        double quadrantAngle = angle % 90;
        if (quadrant == 2 || quadrant == 4){
            quadrantAngle = 90 - quadrantAngle;
        }
        float deltaDistance = 15;
        float deltaX = (float) Math.cos(Math.toRadians(quadrantAngle)) * deltaDistance;
        float deltaY = (float) Math.sin(Math.toRadians(quadrantAngle)) * deltaDistance;
        float x2 = current.x + deltaX;
        float y2 = current.y + deltaY;
        if (quadrant == 2 || quadrant == 3){
            x2 = current.x - deltaX;
        }
        if (quadrant == 3 || quadrant == 4){
            y2 = current.y - deltaY;
        }
        setPosition(x2, y2);
//        Array<Action> actions = getActions();
//        if(actions.size == 0 ) {
//           //The bullet has crossed the border, hehehe....
//            this.remove();
//            return;
//        }
    }
}

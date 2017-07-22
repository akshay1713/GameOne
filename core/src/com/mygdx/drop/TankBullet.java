package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by akshaysingh on 11/07/17.
 */
public class TankBullet extends PhysicsActor{
    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("Spritesheet/tanksprite.atlas"));
    private TextureRegion region = textureAtlas.findRegion("bulletBeige");
    private float quadrantAngle;
    private int quadrant;
    private static float distance = 15f;
    private static float width = Gdx.graphics.getWidth();
    private static float height = Gdx.graphics.getHeight();

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
        float angle = xDiff.angle();
        quadrantAngle = angle % 90;
        quadrant = getQuadrant(angle);
        if (quadrant == 2 || quadrant == 4){
            quadrantAngle = 90 - quadrantAngle;
        }
    }


    private boolean signShouldChange(float positionX, float positionY){
        if(positionX < 0 || (positionY < 0 && positionX < 0)){
            return true;
        }
        return false;
    }

    private void setInitialParameters(float angle, Vector2 origin, Vector2 position){
       setOrigin(origin.x, origin.y);
       setPosition(position.x, position.y);
       setRotation(angle);
    }

    private int getQuadrant(float angle){
        int angle2 = (int)angle;
        return (angle2/90)%4+1;
    }

    private Vector2 getNextPosition(Vector2 current){
        float deltaX = (float) Math.cos(Math.toRadians(quadrantAngle)) * distance;
        float deltaY = (float) Math.sin(Math.toRadians(quadrantAngle)) * distance;
        float x2 = current.x + deltaX;
        float y2 = current.y + deltaY;
        if (quadrant == 2 || quadrant == 3){
            x2 = current.x - deltaX;
        }
        if (quadrant == 3 || quadrant == 4){
            y2 = current.y - deltaY;
        }
        return new Vector2(x2, y2);
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
        if(current.x < 0 || current.x > width || current.y < 0 || current.y > height){
            remove();
            return;
        }
        Vector2 nextPosition = getNextPosition(current);
        setPosition(nextPosition.x, nextPosition.y);
    }

    //TODO: Use this code and and action instead of manually updating the position
//    private void setFireAction(){
//        MoveToAction fireAction = new MoveToAction();
//        Vector2 destination = getDestinationVector();
//        fireAction.setPosition(destination.x, destination.y);
//        fireAction.setDuration(2f);
//        addAction(fireAction);
//    }
//
//    private Vector2 getDestinationVector(){
//        Vector2 destination = new Vector2();
//        float currentX = getX();
//        float currentY = getY();
//        destination.x = 2000;
//        destination.y = destination.x * currentY/currentX;
//        if(signShouldChange(currentX, currentY)){
//            destination.x = -destination.x;
//            destination.y = -destination.y;
//        }
//        destination = destination.rotate(60f);
//        if (destination.x < 0){
//            destination = destination.rotate(-15f);
//        }
//        return localToStageCoordinates(destination);
//    }
}

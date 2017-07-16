package com.mygdx.drop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

/**
 * Created by akshaysingh on 09/07/17.
 */
public class Tank extends Group{
    private TankBody body;
    private TankTurret turret;

    private boolean movingForward = false;
    private boolean movingBackward = false;
    private boolean movingRight = false;
    private boolean movingLeft = false;

    public Tank(){
        body = new TankBody();
        turret = new TankTurret();
        turret.setPosition(body.getWidth()/2 - turret.getWidth()/2, body.getHeight()/2);
        addActor(body);
        addActor(turret);
        setPosition(400, 300);
        setScale(0.5f);
    }

    public void rotateTurret(float screenX, float screenY) {
        Vector2 turretStageCoords = turret.localToStageCoordinates(new Vector2(turret.getOriginX(), turret.getOriginY()));
        Vector2 touchVector = new Vector2(screenX, screenY);
        Vector2 xDiff = touchVector.sub(turretStageCoords);
        turret.setRotation(xDiff.angle() - 90);
    }

    public void fire(float xPos, float yPos){
        Vector2 bulletMove = getBulletPosition();
        Vector2 bulletOrigin = getBulletOrigin();
        float bulletAngle = getBulletAngle();
        TankBullet bullet = new TankBullet(bulletAngle, bulletOrigin, bulletMove);
        getParent().addActor(bullet);
    }

    public void moveForward(){
        movingForward = true;
    }

    public void stopMovingForward(){
        movingForward = false;
    }

    public void moveBackward() {
        movingBackward = true;
    }

    public void stopMovingBackward(){
        movingBackward = false;
    }

    public void moveRight() {
        movingRight = true;
    }

    public void stopMovingRight(){
        movingRight = false;
    }

    public void moveLeft() {
        movingLeft = true;
    }

    public void stopMovingLeft(){
        movingLeft = false;
    }

    private Vector2 getBulletPosition(){
        Vector2 leftCoords = turret.localToParentCoordinates(new Vector2(turret.getX(Align.topLeft), turret.getY(Align.topLeft)));
        Vector2 rightCoords = turret.localToParentCoordinates(new Vector2(turret.getX(Align.topRight), turret.getY(Align.topRight)));
        float moveX = (leftCoords.x + rightCoords.x)/2  - body.getWidth();
        float moveY = (rightCoords.y + leftCoords.y)/2 - body.getHeight();
        return localToStageCoordinates(new Vector2(moveX, moveY));
    }

    private Vector2 getBulletOrigin(){
        return turret.getOrigin();
    }

    private float getBulletAngle(){
       return turret.getRotation();
    }

    private void updateY(){
        if(movingForward && !movingBackward){
            setY(getY() + 5);
        } else if (movingBackward && !movingForward){
            setY(getY() - 5);
        }
    }

    private void updateX(){
       if(movingRight && !movingLeft){
           setX(getX() + 5);
       } else if (movingLeft && !movingRight){
           setX(getX() - 5);
       }
    }

    private void updateAngle(){
        if(movingRight && !movingLeft){
            body.setRotation(90f);
        } else if (movingLeft && !movingRight){
            body.setRotation(270);
        } else if (movingForward && !movingBackward) {
            body.setRotation(0f);
        } else if (movingBackward && !movingForward) {
            body.setRotation(180);
        }
    }

    @Override
    public void act(float delta){
        super.act(delta);
        updateY();
        updateX();
        updateAngle();
    }

}

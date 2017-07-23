package com.mygdx.drop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Align;

/**
 * Created by akshaysingh on 09/07/17.
 */
public class Tank extends PhysicsGroup{
    private TankBody tankBody;
    private TankTurret turret;

    private boolean movingForward = false;
    private boolean movingBackward = false;
    private boolean movingRight = false;
    private boolean movingLeft = false;

    public Tank(){
        tankBody = new TankBody();
        turret = new TankTurret();
        turret.setPosition(tankBody.getWidth()/2 - turret.getWidth()/2, tankBody.getHeight()/2);
        addActor(tankBody);
        addActor(turret);
        setPosition(400, 300);
        setScale(0.5f);
    }

    public void setBox2d(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(localToStageCoordinates(new Vector2(tankBody.getOriginX() + tankBody.getWidth()/2, tankBody.getOriginY() + tankBody.getHeight()/2)));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        System.out.println(new Vector2(getWidth(), getHeight()));
        shape.setAsBox(tankBody.getWidth()/4, tankBody.getHeight()/4);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f;
        body.createFixture(fixtureDef);
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
        Vector2 clickVector = new Vector2(xPos, yPos);
        TankBullet bullet = new TankBullet(bulletAngle, bulletOrigin, bulletMove, clickVector);
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
        float moveX = (leftCoords.x + rightCoords.x)/2  - tankBody.getWidth();
        float moveY = (rightCoords.y + leftCoords.y)/2 - tankBody.getHeight();
        return localToStageCoordinates(new Vector2(moveX, moveY));
    }

    private Vector2 getBulletOrigin(){
        return turret.getOrigin();
    }

    private float getBulletAngle(){
        return turret.getRotation();
    }

    private void updateY(){
        Vector2 current = body.getPosition();
        float currentY = current.y;
        if(movingForward && !movingBackward){
            currentY += 5;
        } else if (movingBackward && !movingForward){
            currentY -=5;
        } else {
            return;
        }
        float currentX = current.x;
        body.setTransform(new Vector2(currentX, currentY), 0);
    }

    private void updateX(){
        Vector2 current = body.getPosition();
        float currentX = current.x;
        if(movingRight && !movingLeft){
            currentX += 5;
        } else if (movingLeft && !movingRight){
            currentX -= 5;
        } else {
            return;
        }
        float currentY = current.y;
        body.setTransform(new Vector2(currentX, currentY), 0);
    }

    private void updateAngle(){
        float angle = body.getAngle();
        if(movingRight && !movingLeft){
            tankBody.setRotation(90f);
        } else if (movingLeft && !movingRight){
            tankBody.setRotation(270);
        } else if (movingForward && !movingBackward) {
            tankBody.setRotation(0f);
        } else if (movingBackward && !movingForward) {
            tankBody.setRotation(180);
        }
        body.setTransform(body.getPosition(), angle);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        updateY();
        updateX();
        updateAngle();
        Vector2 current = body.getPosition();
        setPosition(current.x - tankBody.getWidth()/4, current.y - tankBody.getHeight()/4);
    }

}

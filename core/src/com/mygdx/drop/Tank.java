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
        bodyDef.fixedRotation = true;
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
        bullet.setBox2d();
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
    private boolean updateY(){
        Vector2 current = body.getPosition();
        boolean updated = false;
        if(movingForward){
            body.applyLinearImpulse(0f, 500000f, current.x, current.y, true);
            updated = true;
        } else if (movingBackward){
            body.applyLinearImpulse(0f, -500000f, current.x, current.y, true);
            updated = true;
        }
        return updated;
    }

    private boolean updateX(){
        Vector2 current = body.getPosition();
        boolean updated = false;
        if(movingRight && !movingLeft){
            body.applyLinearImpulse(500000f, 0f, current.x, current.y, true);
            updated = true;
        } else if (movingLeft && !movingRight){
            body.applyLinearImpulse(-500000f, 0f, current.x, current.y, true);
            updated = true;
        }
        return updated;
    }

    @Override
    public void act(float delta){
        super.act(delta);
        boolean newY = updateY();
        boolean newX = updateX();
        body.setAngularVelocity(0);
        if (!newX && !newY){
            body.setLinearVelocity(new Vector2(0,0));
            return;
        }
        Vector2 updatedPosition = body.getPosition();
        setPosition(updatedPosition.x - tankBody.getWidth()/4, updatedPosition.y - tankBody.getHeight()/4);
    }

}

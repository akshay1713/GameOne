package com.mygdx.drop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Align;

/**
 * Created by akshaysingh on 09/07/17.
 */
public class Tank extends Group{
    private TankBody body;
    private TankTurret turret;

    public Tank(){
        body = new TankBody();
        turret = new TankTurret();
        turret.setPosition(body.getWidth()/2 - turret.getWidth()/2, body.getHeight()/2);
        addActor(body);
        addActor(turret);
        setPosition(400, 300);
    }

    public void rotateTurret(float screenX, float screenY) {
        Vector2 turretStageCoords = turret.localToStageCoordinates(new Vector2(turret.getOriginX(), turret.getOriginY()));
        Vector2 touchVector = new Vector2(screenX, screenY);
        Vector2 xDiff = touchVector.sub(turretStageCoords);
        turret.setRotation(xDiff.angle() - 90);
    }

    public void fire(float xPos, float yPos){
        TankBullet bullet = new TankBullet(turret.getHeight());
        bullet.setRotation(turret.getRotation());
        Vector2 leftCoords = turret.localToParentCoordinates(new Vector2(turret.getX(Align.topLeft), turret.getY(Align.topLeft)));
        Vector2 rightCoords = turret.localToParentCoordinates(new Vector2(turret.getX(Align.topRight), turret.getY(Align.topRight)));
        bullet.setOrigin(turret.getOrigin().x, turret.getOrigin().y);
        float moveX = (leftCoords.x + rightCoords.x)/2  - body.getWidth()/2;
        float moveY = (rightCoords.y + leftCoords.y)/2 - body.getHeight()/2;
        bullet.moveBy(moveX, moveY);
        addActor(bullet);
        MoveToAction fireAction = new MoveToAction();
        fireAction.setPosition(1000,bullet.getY());
        fireAction.setDuration(10f);
        bullet.addAction(fireAction);
    }

    public void update(){

    }
}

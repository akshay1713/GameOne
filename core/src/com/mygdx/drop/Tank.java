package com.mygdx.drop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float xPos, float yPos, int pointer, int button){
                System.out.println("Turret listener " + xPos + " " + yPos);
                return false;
            }
        });
        setPosition(400, 0);
    }

    public void handleMouseMove(float screenX, float screenY) {
        Vector2 turretStageCoords = turret.localToStageCoordinates(new Vector2(turret.getX(Align.center), 0));
        Vector2 touchVector = new Vector2(screenX, screenY);
        Vector2 xDiff = touchVector.sub(turretStageCoords);
        turret.setRotation(xDiff.angle() - 90);
    }

    public void handleMouseMove(Vector3 touchVector) {
    }
}

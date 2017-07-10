package com.mygdx.drop;

import com.badlogic.gdx.scenes.scene2d.Group;

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
        setPosition(400, 0);
    }
}

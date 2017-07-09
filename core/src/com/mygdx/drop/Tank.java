package com.mygdx.drop;

import com.badlogic.gdx.graphics.g2d.Batch;
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
        addActor(body);
        addActor(turret);
        setPosition(0, 0);
    }

    @Override
    public void draw(Batch batch, float alpha){
        super.draw(batch, alpha);
        body.draw(batch, alpha, 0, 0);
        System.out.println(body.getWidth() + "\n" + body.getHeight());
        turret.draw(batch, alpha,  body.getWidth()/2 - turret.getWidth()/2, body.getHeight()/2);
    }
}

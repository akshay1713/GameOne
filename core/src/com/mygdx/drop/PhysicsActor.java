package com.mygdx.drop;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by akshaysingh on 22/07/17.
 */
public class PhysicsActor extends Actor{

    Body body;
    protected static World world;

    public static void setWorld(World newWorld){
        world = newWorld;
    }

    public PhysicsActor(){
    }
}

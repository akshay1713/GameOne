package com.mygdx.drop;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by akshaysingh on 22/07/17.
 */
public class PhysicsGroup extends Group {
    protected Body body;
    protected static World world;

    public static void setWorld(World newWorld){
        world = newWorld;
    }

    public PhysicsGroup(){
    }
}

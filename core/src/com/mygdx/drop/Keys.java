package com.mygdx.drop;

import com.badlogic.gdx.Input;

import java.util.Arrays;
import java.util.List;

public class Keys {
    private static final List<Integer> forward = Arrays.asList(Input.Keys.W, Input.Keys.UP);
    private static final List<Integer> backward = Arrays.asList(Input.Keys.S, Input.Keys.BACK);
    private static final List<Integer> left = Arrays.asList(Input.Keys.A, Input.Keys.LEFT);
    private static final List<Integer> right = Arrays.asList(Input.Keys.D, Input.Keys.RIGHT);

    public static boolean isForwardKey(int keyCode){
        if(forward.contains(keyCode)){
           return true;
        }
        return false;
    }

    public static boolean isBackwardKey(int keyCode){
        if(backward.contains(keyCode)){
            return true;
        }
        return false;
    }

    public static boolean isLeftKey(int keyCode){
        if(left.contains(keyCode)){
            return true;
        }
        return false;
    }


    public static boolean isRightKey(int keyCode){
        if(right.contains(keyCode)){
            return true;
        }
        return false;
    }
}


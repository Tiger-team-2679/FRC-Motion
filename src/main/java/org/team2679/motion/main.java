package org.team2679.motion;

import org.team2679.motion.util.Exporter;
import org.team2679.motion.util.Math;

public class main {
    public static void main(String args[]){
        double[] value = Math.rotate_2D(10,0,0,0,2* java.lang.Math.PI);
        System.out.println(value[0] + ", " + value[1]);
        System.out.println(Math.radians_to_degrees(java.lang.Math.PI*2));
    }
}

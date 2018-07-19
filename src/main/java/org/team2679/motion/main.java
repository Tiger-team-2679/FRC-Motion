package org.team2679.motion;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.team2679.motion.spline.Cubic;
import org.team2679.motion.spline.Spline;

import java.sql.SQLOutput;

public class main {
    public static void main(String args[]){
        double startTime = System.nanoTime();

        Spline spline = new Spline(new Waypoint[]{
                new Waypoint(0,0,0),
                new Waypoint(10,10,70),
                new Waypoint(11, 20 , 90),
                new Waypoint(7, 25 , 120)
        }, Spline.SPLINE_TYPE.QUINTIC);

        Waypoint[] path = spline.getSamples(1000);

        double now = System.nanoTime();

        double xpoints[] = new double[path.length];
        double ypoints[] = new double[path.length];
        for(int i = 0; i < path.length; i++){
            xpoints[i] = path[i].getX();
            ypoints[i] = path[i].getY();
        }


        System.out.println("elapse time = " + ((now - startTime)/1e6));
    }
}

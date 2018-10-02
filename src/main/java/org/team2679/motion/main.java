package org.team2679.motion;

import org.team2679.motion.spline.CubicSpline;
import org.team2679.motion.spline.CubicSplinePath;
import org.team2679.motion.spline.SplinePath;
import org.team2679.motion.spline.Waypoint;
import org.team2679.motion.util.Exporter;
import org.team2679.motion.util.Math;

public class main {
    public static void main(String args[]){
        Exporter exporter = new Exporter(',', "/home/slowl0ris/Desktop", "dump");
        try {
            CubicSplinePath path = new CubicSplinePath( new Waypoint(0,0,0), new Waypoint(10, 10, 0));
            System.out.println(path.get_length());
            CubicSpline spline = new CubicSpline( new Waypoint(0,0,0), new Waypoint(10, 10, 0));
            System.out.println(spline.interpolate(1)[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
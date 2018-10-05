package org.team2679.motion;

import org.team2679.motion.spline.CatmullRomSpline;

import java.awt.*;
import java.util.ArrayList;

public class main {
    public static void main(String args[]){
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(2, 4));
        points.add(new Point(4, 4));
        points.add(new Point(6, 4));
        points.add(new Point(8, 0));
        double time = System.currentTimeMillis();
        CatmullRomSpline spline = new CatmullRomSpline(points, 1000);
        for(double i = 0; i < 1; i = i + 0.001){
            try {
                double x = spline.interpolate_X(i);
                double y = spline.interpolate_Y(i);
                System.out.println(x + ", "+ y);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        System.out.println("elapse: " + (System.currentTimeMillis() - time));
    }
}
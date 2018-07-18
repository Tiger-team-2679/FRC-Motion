package org.team2679.motion;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.team2679.motion.spline.Cubic;
import org.team2679.motion.spline.Spline;

public class main {
    public static void main(String args[]){
        double startTime = System.nanoTime();

        Spline spline = new Spline(new Waypoint[]{
                new Waypoint(0,0,0),
                new Waypoint(9,5,70),
                new Waypoint(10,10,89),
                new Waypoint(25,10,0),
                new Waypoint(45,0,0),
        }, Spline.SPLINE_TYPE.QUINTIC);

        Waypoint[] path = spline.getSamples(500);

        double now = System.nanoTime();

        double xpoints[] = new double[path.length];
        double ypoints[] = new double[path.length];
        for(int i = 0; i < path.length; i++){
            xpoints[i] = path[i].getX();
            ypoints[i] = path[i].getY();
        }

        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)",  xpoints, ypoints);

        new SwingWrapper(chart).displayChart();



        System.out.println("elapse time = " + ((now - startTime)/1e6));
    }
}

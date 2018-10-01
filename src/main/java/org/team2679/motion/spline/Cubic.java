package org.team2679.motion.spline;

/**
 * a cubic is an object representing a cubic function, with a starting and ending points, including degrees
 * a cubic is used by the spline function, all cubic functions are created with offsets to x, y, and angle
 */
public class Cubic extends Spline {

    private double length;

    private double a,b,c,d;

    public Cubic(Waypoint startPoint, Waypoint endPoint){

    }

    @Override
    double[] get_point_by_length(double length, double number_of_samples) {
        return new double[0];
    }

    @Override
    double[] get_point_by_x(double x) {
        return new double[0];
    }

    @Override
    double get_length(double number_of_samples) {
        return 0;
    }
}

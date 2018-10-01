package org.team2679.motion.spline;

public abstract class Spline {

    abstract double[] get_point_by_length(double length, double number_of_samples);
    abstract double[] get_point_by_x(double x);
    abstract double get_length(double number_of_samples);

}

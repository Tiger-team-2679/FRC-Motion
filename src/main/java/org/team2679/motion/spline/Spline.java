package org.team2679.motion.spline;

public abstract class Spline {

    abstract public double[] get_point_by_length(double length, int number_of_samples);
    abstract public double[] interpolate(double percent) throws Exception;
    abstract public double get_length(int number_of_samples) throws Exception;

}

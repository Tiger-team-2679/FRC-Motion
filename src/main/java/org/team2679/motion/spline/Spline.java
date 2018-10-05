package org.team2679.motion.spline;

public abstract class Spline {

    abstract public double interpolate_X(double percent) throws Exception;
    abstract public double interpolate_Y(double percent) throws Exception;
    abstract public double get_length(int num_of_samples) throws Exception;

    abstract public void append(Waypoint... waypoints) throws Exception;

}

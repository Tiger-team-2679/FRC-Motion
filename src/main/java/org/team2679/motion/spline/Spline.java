package org.team2679.motion.spline;

public abstract class Spline {

    public enum SPLINE_TYPE{
        CATMULL_ROM_SPLINE, CUBIC_HERMITE_SPLINE;
    }

    abstract public double interpolate_X(double percent) throws Exception;
    abstract public double interpolate_Y(double percent) throws Exception;
    abstract public double get_length(int num_of_samples) throws Exception;
    abstract public SPLINE_TYPE get_type();

    abstract public void append(Waypoint... waypoints) throws Exception;

    abstract public boolean is_valid();

}

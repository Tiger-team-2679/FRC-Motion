package org.team2679.motion.trajectory;

/**
 * A segment is a part of a Trajectory {@link Trajectory}
 * it contains x, y, velocity, acceleration, heading, and curvature
 */
public class Segment {

    // package private attributes
    double x,y,v,a,h,c;

    public Segment(double x, double y){

    }

    public double get_x() {
        return x;
    }
    public double get_y() {
        return y;
    }
    public double get_velocity() {
        return v;
    }
    public double get_acceleration() {
        return a;
    }
    public double get_heading() {
        return h;
    }
    public double get_curvature() {
        return c;
    }

}

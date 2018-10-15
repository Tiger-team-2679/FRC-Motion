package org.team2679.motion.spline;

import javafx.scene.layout.Pane;
import org.team2679.motion.util.Math;

import java.util.ArrayList;

public class HermiteCubicSpline extends Spline {

    private ArrayList<Waypoint> waypoints;

    private boolean is_length_changed;
    private double length;

    public HermiteCubicSpline(Waypoint... points) throws Exception{
        this.waypoints = new ArrayList<>();
        this.append(points);
    }

    /**
     * calculate a hermite point
     * @param P1 control point 1
     * @param P2 control point 2
     * @param P3 control point 3
     * @param P4 control point 4
     * @param t the tangent size
     * @return the hermite point
     */
    private double calculate_hermite_point(double P1, double P2, double P3, double P4, double t){
        double a = -P1/2.0 + (3.0*P2)/2.0 - (3.0*P3)/2.0 + P4/2.0;
        double b = P1 - (5.0*P2)/2.0 + 2.0*P3 - P4 / 2.0;
        double c = -P1/2.0 + P3/2.0;
        double d = P2;

        return a*t*t*t + b*t*t + c*t + d;
    }

    @Override
    public void append(Waypoint... waypoints) throws Exception {
        if(this.waypoints.size() + waypoints.length < 4){
            throw new Exception("hermite spline must have at least 4 points");
        }

    }

    @Override
    public double interpolate_X(double percent) throws Exception {
        return 0;
    }

    @Override
    public double interpolate_Y(double percent) throws Exception {
        return 0;
    }

    @Override
    public double get_length(int number_of_samples) throws Exception {
        return 0;
    }

    @Override
    public SPLINE_TYPE get_type() {
        return null;
    }

    @Override
    public boolean is_valid() {
        return true;
    }
}

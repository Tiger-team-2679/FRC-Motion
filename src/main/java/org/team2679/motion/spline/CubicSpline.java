package org.team2679.motion.spline;

import java.util.ArrayList;

public class CubicSpline extends Spline {

    ArrayList<Cubic> path_splines;

    public CubicSpline(Waypoint... waypoints) throws Exception{
        this.path_splines = new ArrayList<>();
        this.append(waypoints);
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
    public double get_length(int num_of_samples) throws Exception {
        double length = 0;
        for (Cubic path_spline : this.path_splines) {
            length += path_spline.get_length(num_of_samples);
        }
        return length;
    }

    @Override
    public SPLINE_TYPE get_type() {
        return null;
    }

    @Override
    public void append(Waypoint... waypoints) throws Exception{
        if(waypoints.length + this.path_splines.size() < 2){
            throw new Exception("can't create cubic spline path with less than 2 points");
        }
        for (int i = 0; i < waypoints.length - 1; i++) {
            Cubic spline = new Cubic(waypoints[i],waypoints[i + 1]);
            this.path_splines.add(spline);
        }
    }

    @Override
    public boolean is_valid() {
        return true;
    }
}


package org.team2679.motion.spline;

import java.util.ArrayList;

public class CubicSplinePath extends SplinePath {

    int num_of_samples = 10000; // default number of samples

    ArrayList<Spline> path_splines;

    public CubicSplinePath(Waypoint... waypoints) throws Exception{
        this.path_splines = new ArrayList<>();
        this.append(waypoints);
        this.num_of_samples = num_of_samples;
    }

    @Override
    public double get_length() throws Exception {
        double length = 0;
        for (Spline path_spline : this.path_splines) {
            length += path_spline.get_length(this.num_of_samples);
        }
        return length;
    }

    @Override
    public void append(Waypoint... waypoints) throws Exception{
        if(waypoints.length + this.path_splines.size() < 2){
            throw new Exception("can't create cubic spline path with less than 2 points");
        }
        for (int i = 0; i < waypoints.length - 1; i++) {
            CubicSpline spline = new CubicSpline(waypoints[i],waypoints[i + 1]);
            this.path_splines.add(spline);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.path_splines.size() == 0;
    }

    /**
     * sets a new sum of samples for length calculation
     * @param num_of_samples
     */
    public void set_num_of_samples(int num_of_samples) {
        this.num_of_samples = num_of_samples;
    }
}


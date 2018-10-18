package org.team2679.motion.spline;

import java.util.ArrayList;
import java.util.Collections;

public class CubicHermiteSpline extends Spline{

    private ArrayList<Waypoint> controlPoints;

    /**
     * Constructs a Hermite Curve
     * @param controlPoints control points of the curve
     */
    public CubicHermiteSpline(Waypoint... controlPoints) throws Exception {
        this.controlPoints = new ArrayList<>();
        if(controlPoints.length < 4){
            throw new Exception("Hermite Cubic spline must have at least 4 points");
        }
        Collections.addAll(this.controlPoints, controlPoints);
    }

    /**
     * Constructs a Hermite Curve
     * @param controlPoints control points of the curve
     */
    public CubicHermiteSpline(ArrayList<Waypoint> controlPoints) throws Exception {
        if(controlPoints.size() < 4){
            throw new Exception("Hermite Cubic spline must have at least 4 points");
        }
        this.controlPoints = controlPoints;
    }

    private double solveCubicHermite(double p0, double p1, double p2, double p3, double t){
        double a = -p0/2.0f + (3.0f*p1)/2.0f - (3.0f*p2)/2.0f + p3/2.0f;
        double b = p0 - (5.0f*p1)/2.0f + 2.0f*p2 - p3 / 2.0f;
        double c = -p0/2.0f + p2/2.0f;
        double d = p1;

        return a*t*t*t + b*t*t + c*t + d;
    }

    /**
     * gets x from the spline by progress percent
     * @param percent percent value goes from 0 - 1
     * @return
     */
    public double interpolate_X(double percent) throws Exception{
        if(percent > 1){
            throw  new Exception("interpolation is out of bounds");
        }
        double[] interpolation_values = get_t_and_jump_for_percent(percent);
        Waypoint p0, p1, p2, p3;
        p0 = controlPoints.get((int)interpolation_values[1]);
        p1 = controlPoints.get((int)interpolation_values[1] + 1);
        p2 = controlPoints.get((int)interpolation_values[1] + 2);
        p3 = controlPoints.get((int)interpolation_values[1] + 3);

        return solveCubicHermite(p0.getX(), p1.getX(), p2.getX(), p3.getX(), interpolation_values[0]);
    }

    /**
     * gets y from the spline by progress percent
     * @param percent percent value goes from 0 - 1
     * @return
     */
    public double interpolate_Y(double percent) throws Exception {
        if(percent > 1){
            throw  new Exception("interpolation is out of bounds");
        }
        double[] interpolation_values = get_t_and_jump_for_percent(percent);
        Waypoint p0, p1, p2, p3;
        p0 = controlPoints.get((int)interpolation_values[1]);
        p1 = controlPoints.get((int)interpolation_values[1] + 1);
        p2 = controlPoints.get((int)interpolation_values[1] + 2);
        p3 = controlPoints.get((int)interpolation_values[1] + 3);

        return solveCubicHermite(p0.getY(), p1.getY(), p2.getY(), p3.getY(), interpolation_values[0]);
    }

    @Override
    public double get_length(int num_of_samples) throws Exception {
        // TODO implement
        return 0;
    }

    @Override
    public SPLINE_TYPE get_type() {
        return SPLINE_TYPE.CUBIC_HERMITE_SPLINE;
    }

    @Override
    public void append(Waypoint... waypoints) throws Exception {
        //TODO implement
    }

    @Override
    public boolean is_valid() {
        return true;
    }

    /**
     * get the right t and jump for the interpolation by the interpolation percent
     * @param percent of the interpolation
     * @return an array containing t and jump in that order
     */
    private double[] get_t_and_jump_for_percent(double percent){
        double jump = (int)(percent*(controlPoints.size() - 3)/1);
        double t = (controlPoints.size() - 3)*percent - jump;
        if(jump > controlPoints.size() - 4){
            jump = controlPoints.size() - 4;
            t = 1;
        }
        return new double[]{t,jump};
    }
}
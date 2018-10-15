package org.team2679.motion.spline;

import java.awt.*;
import java.util.ArrayList;

public class CatmullRomSpline extends Spline{

    private ArrayList<Waypoint> controlPoints;

    /**
     * Constructs a Catmull-Rom Curve
     * @param controlPoints control points of the curve
     */
    public CatmullRomSpline(ArrayList<Waypoint> controlPoints) throws Exception {
        if(controlPoints.size() < 4){
            throw new Exception("CatmullRom spline must have at least 4 points");
        }
        this.controlPoints = controlPoints;
    }

    /**
     * Returns the blending function 0 value of the Catmull-Rom Curve
     * @param t
     * @return blending function 0 value
     */
    private double getBlendingFunction0(double t) {
        return (-1*Math.pow(t, 3) + 2*Math.pow(t, 2) - t)/2.0;
    }

    /**
     * Returns the blending function 1 value of the Catmull-Rom Curve
     * @param t
     * @return blending function 1 value
     */
    private double getBlendingFunction1(double t) {
        return (3*Math.pow(t, 3) - 5*Math.pow(t, 2) + 2)/2.0;
    }

    /**
     * Returns the blending function 2 value of the Catmull-Rom Curve
     * @param t
     * @return blending function 2 value
     */
    private double getBlendingFunction2(double t) {
        return (-3*Math.pow(t, 3) + 4*Math.pow(t, 2) + t)/2.0;
    }

    /**
     * Returns the blending function 3 value of the Catmull-Rom Curve
     * @param t
     * @return blending function 3 value
     */
    private double getBlendingFunction3(double t) {
        return (Math.pow(t, 3) - Math.pow(t, 2))/2.0;
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
        int point_index = (int)(percent/1.0*(this.controlPoints.size() - 3));
        double t = get_t_for_percent(percent);
        Waypoint p0, p1, p2, p3;
        p0 = controlPoints.get(point_index);
        p1 = controlPoints.get(point_index + 1);
        p2 = controlPoints.get(point_index + 2);
        p3 = controlPoints.get(point_index + 3);

        return p0.getX()*getBlendingFunction0(t) + p1.getX()*getBlendingFunction1(t) +
                p2.getX()*getBlendingFunction2(t) + p3.getX()*getBlendingFunction3(t);
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
        int point_index = (int)(percent/1.0*(this.controlPoints.size() - 3));
        double t = get_t_for_percent(percent);
        Waypoint p0, p1, p2, p3;
        p0 = controlPoints.get(point_index);
        p1 = controlPoints.get(point_index + 1);
        p2 = controlPoints.get(point_index + 2);
        p3 = controlPoints.get(point_index + 3);

        return p0.getY()*getBlendingFunction0(t) + p1.getY()*getBlendingFunction1(t) +
                p2.getY()*getBlendingFunction2(t) + p3.getY()*getBlendingFunction3(t);
    }

    @Override
    public double get_length(int num_of_samples) throws Exception {
        // TODO implement
        return 0;
    }

    @Override
    public SPLINE_TYPE get_type() {
        return SPLINE_TYPE.CATMULL_ROM_SPLINE;
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
     * get the right t for the interpolation by the interpolation percent
     * @param percent
     * @return the t value for the interpolation
     */
    private double get_t_for_percent(double percent){
        double t =  percent/1.0*(this.controlPoints.size() - 3);
        if(t > 1 && t%1 == 0){
            t = 1;
        }
        else {
            t = t%1;
        }
        return t;
    }
}
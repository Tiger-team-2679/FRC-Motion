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

    public double[] interpolate(double percent) throws Exception {
        /**
        if(!Math.is_between(percent, 0, 1)){
            throw new Exception("hermite interpolation is out of bounds");
        }
        double x, y = 0.0;

        double tx = (this.waypoints.size() - 1)* percent;
        int index = int(tx);
        double t = tx - java.lang.Math.floor(tx);

        std::array<float, 2> A = GetIndexClamped(points, index - 1);
        std::array<float, 2> B = GetIndexClamped(points, index + 0);
        std::array<float, 2> C = GetIndexClamped(points, index + 1);
        std::array<float, 2> D = GetIndexClamped(points, index + 2);
        x = CubicHermite(A[0], B[0], C[0], D[0], t);
        y = CubicHermite(A[1], B[1], C[1], D[1], t);
        **/
        return new double[2];
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

}

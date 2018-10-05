package org.team2679.motion.spline;

import org.team2679.motion.util.Math;

/**
 * a class made to make cubic spline creation easier
 * a cubic is an object representing a cubic function, with a starting and ending points, including degrees
 * a cubic is used by the spline function, all cubic functions are created with offsets to x, y, and angle
 */
public class Cubic{

    private double length;
    private int recent_num_of_samples; // the amount of samples used to calculate the length most recently

    private double a,b,c,d;

    private double x_offset;
    private double y_offset;
    private double rotation_angle;

    private Waypoint start_waypoint;
    private Waypoint end_waypoint;

    private Waypoint rotated_start_waypoint;
    private Waypoint rotated_end_waypoint;

    public Cubic(Waypoint startPoint, Waypoint endPoint) throws ArithmeticException{
        calculate_offset(startPoint.getX(), startPoint.getY(), startPoint.getAngle());
        rotate_waypoints_and_calculate_coefficients(startPoint.getX(), startPoint.getY(), startPoint.getAngle(), endPoint.getX(), endPoint.getY(), endPoint.getAngle());
    }

    /**
     * calculate the rotation's offset
     * @param x0 start point x
     * @param y0 start point y
     * @param angle0 start point angle in radians
     */
    private void calculate_offset(double x0, double y0, double angle0){
        this.x_offset = x0;
        this.y_offset = y0;
        this.rotation_angle = angle0;
    }

    /**
     * rotates the points in order to allow spline paths that goes back
     * because we rotate the spline in order to have one y value for each x
     * @param x0 start point x
     * @param y0 start point y
     * @param angle0 start point angle in radians
     * @param x1 end point x
     * @param y1 end point y
     * @param angle1 end point angle in radians
     */
    private void rotate_waypoints_and_calculate_coefficients(double x0, double y0, double angle0, double x1, double y1, double angle1) throws ArithmeticException{
        // store the end point and start point for future calculations
        this.start_waypoint = new Waypoint(x0,y0,angle0);
        this.end_waypoint = new Waypoint(x1,y1,angle1);

        double new_x0 = x0 - x_offset;
        double new_y0 = y0 - y_offset;
        double new_angle0 = angle0 - rotation_angle;
        // get the rotated end point x and y
        double results[] = Math.rotate_2D(x1 - x_offset, y1 - y_offset, new_x0, new_y0, -rotation_angle);
        double new_x1 = results[0];
        double new_y1 = results[1];
        double new_angle1 = angle1 - rotation_angle;

        // store the rotated waypoints for future calculations
        this.rotated_start_waypoint = new Waypoint(new_x0, new_y0, new_angle0);
        this.rotated_end_waypoint = new Waypoint(new_x1, new_y1, new_angle1);

        calculate_coefficients(new_x0,new_y0 , new_angle0, new_x1, new_y1, new_angle1);
    }

    /**
     * calculates the coefficients of the rotated spline
     * @param x0 start point x
     * @param y0 start point y
     * @param angle0 start point angle in radians
     * @param x1 end point x
     * @param y1 end point y
     * @param angle1 end point angle in radians
     * @throws ArithmeticException throw an error if the slope is not
     * possible
     */
    private void calculate_coefficients(double x0, double y0, double angle0, double x1, double y1, double angle1) throws ArithmeticException{
        double slope0 = Math.radians_to_slope(angle0);
        double slope1 = Math.radians_to_slope(angle1);
        this.a = -(2* y0 - 2* y1 - slope0* x0 + slope0* x1 - slope1* x0 + slope1* x1)/((x0 - x1)*(x0*x0 - 2* x0 * x1 + x1*x1));
        this.b = (3* x0 * y0 - 3* x0 * y1 + 3* x1 * y0 - 3* x1 * y1 - slope0*x0*x0 + 2*slope0*x1*x1 - 2*slope1*x0*x0 + slope1*x1*x1 - slope0* x0 * x1 + slope1* x0 * x1)/((x0 - x1)*(x0*x0 - 2* x0 * x1 + x1*x1));
        this.c = -(slope0*x1*x1*x1 - slope1*x0*x0*x0 + slope0* x0 *x1*x1 - 2*slope0*x0*x0* x1 + 2*slope1* x0 *x1*x1 - slope1*x0*x0* x1 + 6* x0 * x1 * y0 - 6* x0 * x1 * y1)/((x0 - x1)*(x0*x0 - 2* x0 * x1 + x1*x1));
        this.d = (x0*x0*x0* y1 - x1*x1*x1* y0 + slope0* x0 *x1*x1*x1 - slope1*x0*x0*x0* x1 + 3* x0 *x1*x1* y0 - 3*x0*x0* x1 * y1 - slope0*x0*x0*x1*x1 + slope1*x0*x0*x1*x1)/((x0 - x1)*(x0*x0 - 2* x0 * x1 + x1*x1));
        //System.out.println("y = " + a + "x^3 + " + b + "x^2 + " + c + "x + " + d);
    }

    /**
     * get point on percent of the spline
     * @param percent percent goes from 0 to 1
     * @return
     * @throws Exception
     */
    public double[] interpolate(double percent) throws Exception{
        double x_axis_length = rotated_end_waypoint.getX() - rotated_start_waypoint.getX();
        double x = rotated_start_waypoint.getX() + x_axis_length*percent;
        if(!Math.is_between(x, rotated_start_waypoint.getX(), rotated_end_waypoint.getX())){
            throw new Exception("x value of interpolation is out of bounds");
        }
        return Math.rotate_2D(x, a*x*x*x + b*x*x + c*x + d, rotated_start_waypoint.getX(), rotated_start_waypoint.getY(), rotation_angle);
    }

    /**
     * gets the length of the spline using number of samples
     * if the number of samples is the same twice, the function will return the
     * last length calculated in order to save resources
     * @param num_of_samples
     * @return
     */
    public double get_length(int num_of_samples) throws Exception {
        if (num_of_samples == 0){
            throw new Exception("number of samples in spline can't be 0");
        }
        if(num_of_samples != this.recent_num_of_samples) {
            double new_length = 0;
            // go through the spline to get an approximated length
            double x_axis_length = rotated_end_waypoint.getX() - rotated_start_waypoint.getX();
            double delta = x_axis_length / num_of_samples;

            for (double i = 1; i < x_axis_length; i += delta) {
                // calculate the segment length and add it to the sum
                new_length += Math.pythagoras(delta, rotated_start_waypoint.getX() + a * i * i * i + b * i * i + c * i + d - (a * (i - delta) * (i - delta) * (i - delta) + b * (i - delta) * (i - delta) + c * (i - delta) + d));
            }
            this.length = new_length;
            this.recent_num_of_samples = num_of_samples;
        }
        return this.length;
    }
}

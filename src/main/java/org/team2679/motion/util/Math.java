package org.team2679.motion.util;

public class Math {

    /**
     * rotate a around a center point
     * @param x the x of the point
     * @param y the y of the point
     * @param center_x the x of the center point
     * @param center_y the y of the center point
     * @param radians angle to rotate in radians
     * @return array containing the rotated points, first x and then y
     */
    public static double[] rotate_2D(double x, double y, double center_x, double center_y, double radians){
        double cos = java.lang.Math.cos(radians);
        double sin = java.lang.Math.sin(radians);
        double nx = (cos * (x - center_x)) + (sin * (y - center_y)) + center_x;
        double ny = (cos * (y - center_y)) - (sin * (x - center_x)) + center_y;
        return new double[]{nx, ny};
    }

    /**
     * convert radians to degrees
     * @param angle angle in radians
     * @return angle in degrees
     */
    public static double radians_to_degrees(double angle){
        return angle*180.0/ java.lang.Math.PI;
    }

    /**
     * converts degrees to radians
     * @param angle angle in degrees
     * @return angle in radians
     */
    public static double degrees_to_radians(double angle){
        return angle*java.lang.Math.PI/180.0;
    }
}

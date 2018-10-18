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

        double nx = (x - center_x) * cos - (y- center_y) * sin;
        double ny = (x - center_x) * sin + (y- center_y) * cos;

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

    /**
     * converts radians of a tangent to a slope
     * @param radians radians to convert
     * @return the slope of the angle
     * @throws throws an arithmetic error if the slope is impossible
     */
    public static double radians_to_slope(double radians) throws ArithmeticException{
        if(radians >= java.lang.Math.PI/2 && radians <= java.lang.Math.PI*3/2){
            throw new ArithmeticException("slope out of limits");
        }
        return java.lang.Math.tan(radians);
    }

    /**
     * returns Pythagoras law c = sqrt(a^2 + b^2)
     * @param x
     * @param y
     * @return the result
     */
    public static double pythagoras(double x, double y){
        return java.lang.Math.sqrt(x*x + y*y);
    }

    /**
     * checks if number is between other two numbers
     * @param x number to check
     * @param y limit 1
     * @param z limit 2
     * @return is x between y and z
     */
    public static boolean is_between(double x, double y, double z){
        if(y > z){
            return x <= y && x >= z;
        }
        if(z > y){
            return x <= z && x >= y;
        }
        else {
            return x == y;
        }
    }
}

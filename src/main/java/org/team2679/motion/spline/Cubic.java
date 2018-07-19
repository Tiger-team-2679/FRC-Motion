package org.team2679.motion.spline;

import org.team2679.motion.Waypoint;

public class Cubic extends Polynomial {

    private int samples = 10000;

    private double a,b,c,d = 0;

    private double tangent1, tangent2;
    private double angle1, angle2 = 0;
    private double x0, y0, x1, y1;

    private double angleOffset;
    private double xOffset;
    private double yOffset;

    private Waypoint startPoint = null;
    private Waypoint endPoint = null;
    private Waypoint offsetEndPoint = null;
    private Waypoint offsetStartPoint = null;

    public Cubic(Waypoint startPoint, Waypoint endPoint){

        /* handle offset stuff */
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        this.angleOffset = startPoint.getAngle();
        this.xOffset = this.startPoint.getX();
        this.yOffset = this.startPoint.getY();

        this.offsetEndPoint = Waypoint.rotate(endPoint, this.angleOffset, this.xOffset, this.yOffset, false);
        this.offsetStartPoint = Waypoint.rotate(startPoint, this.angleOffset, this.xOffset, this.yOffset ,false);

        this.angle1 = offsetStartPoint.getAngle();
        this.angle2 = offsetEndPoint.getAngle();

        this.x0 = this.offsetStartPoint.getX();
        this.x1 = this.offsetEndPoint.getX();
        this.y0 = this.offsetStartPoint.getY();
        this.y1 = this.offsetEndPoint.getY();

        /* handle exceptions */
        if(angle1 >= 90 || angle1 <= -90 || angle2 >= 90 || angle2 <= -90){
            throw new RuntimeException("Angles must be relatively between -90 and 90");
        }
        if(x0 - x1 == 0){
            throw new RuntimeException("X value points can't be the same");
        }

        // calculates the equation now that it's with the offset

        this.tangent1 = Math.tan(Math.toRadians(this.offsetStartPoint.getAngle()));
        this.tangent2 = Math.tan(Math.toRadians(this.offsetEndPoint.getAngle()));

        this.a = -(2* y0 - 2* y1 - tangent1* x0 + tangent1* x1 - tangent2* x0 + tangent2* x1)/((x0 - x1)*(Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2)));
        this.b = (3* x0 * y0 - 3* x0 * y1 + 3* x1 * y0 - 3* x1 * y1 - tangent1*Math.pow(x0,2) + 2*tangent1*Math.pow(x1,2) - 2*tangent2*Math.pow(x0,2) + tangent2*Math.pow(x1,2) - tangent1* x0 * x1 + tangent2* x0 * x1)/((x0 - x1)*(Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2)));
        this.c = -(tangent1*Math.pow(x1,3) - tangent2*Math.pow(x0,3) + tangent1* x0 *Math.pow(x1,2) - 2*tangent1*Math.pow(x0,2)* x1 + 2*tangent2* x0 *Math.pow(x1,2) - tangent2*Math.pow(x0,2)* x1 + 6* x0 * x1 * y0 - 6* x0 * x1 * y1)/((x0 - x1)*(Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2)));
        this.d = (Math.pow(x0,3)* y1 - Math.pow(x1,3)* y0 + tangent1* x0 *Math.pow(x1,3) - tangent2*Math.pow(x0,3)* x1 + 3* x0 *Math.pow(x1,2)* y0 - 3*Math.pow(x0,2)* x1 * y1 - tangent1*Math.pow(x0,2)*Math.pow(x1,2) + tangent2*Math.pow(x0,2)*Math.pow(x1,2))/((x0 - x1)*(Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2)));

    }

    @Override
    public Waypoint getWaypointByPrecent(double percent){
        double length = this.offsetEndPoint.getX() - this.offsetStartPoint.getX();
        percent = Math.max(Math.min(percent, 1), 0);

        double x = length * percent;
        double y = a*Math.pow(x, 3) + b*Math.pow(x, 2) + c*x + d;
        double angle = Math.toDegrees(Math.atan(3*a*Math.pow(x, 2) + 2*b*x + c));

        return Waypoint.rotate(x, y, angle, angleOffset, xOffset, yOffset, true);
    }
}

package org.team2679.motion.spline;

import org.team2679.motion.Waypoint;

public class Quintic extends Polynomial {

    private int samples = 10000;
    private double length;

    private double a,b,c,d,e,f;

    private  Waypoint rotatedEndPoint;
    private Waypoint rotatedStartPoint;

    private double angleOffset, xOffset, yOffset;

    public Quintic(Waypoint startPoint, Waypoint endPoint){
        this(startPoint.getX(), startPoint.getY(), startPoint.getAngle(),
                endPoint.getX(), endPoint.getY(), endPoint.getAngle());
    }

    public Quintic(double x0, double y0, double angle0, double x1, double y1, double angle1){
        angle0 = -angle0;
        angle1 = - angle1;

        this.angleOffset = angle0;
        this.xOffset = x0;
        this.yOffset = y0;

        this.rotatedStartPoint = Waypoint.rotate(x0, y0, angle0, angleOffset, xOffset, yOffset, false);
        rotatedEndPoint = Waypoint.rotate(x1, y1, angle1, angleOffset, xOffset, yOffset, false);

        calculate(rotatedStartPoint.getX(), rotatedStartPoint.getY(), rotatedStartPoint.getAngle(),
                rotatedEndPoint.getX(), rotatedEndPoint.getY(), rotatedEndPoint.getAngle());
    }

    private void calculate(double x0, double y0, double angle0, double x1, double y1, double angle1){
        /* handle exceptions */
        double difference = Math.abs(angle1 - angle0);
        if( difference <= 270 && difference >= 90){
            throw new RuntimeException("Angle absolute difference must be below 90");
        }
        if(x0 >= x1){
            throw new RuntimeException("X values must be relatively growing");
        }

        // calculates the equation now that it's with the offset

        double tangent1 = Math.tan(Math.toRadians(angle0));
        double tangent2 = Math.tan(Math.toRadians(angle1));

        this.a = (6* y1 - 3* x1 *(tangent2 + tangent1) + 3*(tangent2 + tangent1)* x0 - 6* y0)/Math.pow((x1 - x0), 5);
        this.b = (8*Math.pow(x1,2)* tangent1 + tangent2 *(7*Math.pow(x1,2) + x1 * x0 - 8*Math.pow(x0,2)) - x0 *(15* y1 + 7* tangent1 * x0 - 15* y0) + x1 *(-15* y1 - tangent1 * x0 + 15* y0))/Math.pow((x1 - x0),5);
        this.c = -(2*(2* tangent1 *Math.pow(x0,3) - 3* tangent1 *Math.pow(x1,3) + 3* tangent2 *Math.pow(x0,3) - 2* tangent2 *Math.pow(x1,3) - 5*Math.pow(x0,2)* y0 + 5*Math.pow(x0,2)* y1 - 5*Math.pow(x1,2)* y0 + 5*Math.pow(x1,2)* y1 - 7* tangent1 * x0 *Math.pow(x1,2) + 8* tangent1 *Math.pow(x0,2)* x1 - 8* tangent2 * x0 *Math.pow(x1,2) + 7* tangent2 *Math.pow(x0,2)* x1 - 20* x0 * x1 * y0 + 20* x0 * x1 * y1))/(Math.pow((x0 - x1),2)*(Math.pow(x0,3) - 3*Math.pow(x0,2)* x1 + 3* x0 *Math.pow(x1,2) - Math.pow(x1,3)));
        this.d = -(6* x0 *(3* tangent1 *Math.pow(x1,3) + 2* tangent2 *Math.pow(x1,3) + 5*Math.pow(x1,2)* y0 - 5*Math.pow(x1,2)* y1 - tangent1 * x0 *Math.pow(x1,2) - 2* tangent1 *Math.pow(x0,2)* x1 + tangent2 * x0 *Math.pow(x1,2) - 3* tangent2 *Math.pow(x0,2)* x1 + 5* x0 * x1 * y0 - 5* x0 * x1 * y1))/((Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2))*(Math.pow(x0,3) - 3*Math.pow(x0,2)* x1 + 3* x0 *Math.pow(x1,2) - Math.pow(x1,3)));
        this.e = -(tangent1 *Math.pow(x1,5) - tangent2 *Math.pow(x0,5) - 5* tangent1 * x0 *Math.pow(x1,4) + 5* tangent2 *Math.pow(x0,4)* x1 - 8* tangent1 *Math.pow(x0,2)*Math.pow(x1,3) + 12* tangent1 *Math.pow(x0,3)*Math.pow(x1,2) - 12* tangent2 *Math.pow(x0,2)*Math.pow(x1,3) + 8* tangent2 *Math.pow(x0,3)*Math.pow(x1,2) - 30*Math.pow(x0,2)*Math.pow(x1,2)* y0 + 30*Math.pow(x0,2)*Math.pow(x1,2)* y1)/(Math.pow((x0 - x1),2)*(Math.pow(x0,3) - 3*Math.pow(x0,2)* x1 + 3* x0 *Math.pow(x1,2) - Math.pow(x1,3)));
        this.f = (Math.pow(x0,5)* y1 - Math.pow(x1,5)* y0 + tangent1 * x0 *Math.pow(x1,5) - tangent2 *Math.pow(x0,5)* x1 + 5* x0 *Math.pow(x1,4)* y0 - 5*Math.pow(x0,4)* x1 * y1 - 5* tangent1 *Math.pow(x0,2)*Math.pow(x1,4) + 4* tangent1 *Math.pow(x0,3)*Math.pow(x1,3) - 4* tangent2 *Math.pow(x0,3)*Math.pow(x1,3) + 5* tangent2 *Math.pow(x0,4)*Math.pow(x1,2) - 10*Math.pow(x0,2)*Math.pow(x1,3)* y0 + 10*Math.pow(x0,3)*Math.pow(x1,2)* y1)/((Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2))*(Math.pow(x0,3) - 3*Math.pow(x0,2)* x1 + 3* x0 *Math.pow(x1,2) - Math.pow(x1,3)));
    }

    @Override
    public Waypoint getWaypointByPrecent(double percent){
        double xAxisLength = this.rotatedEndPoint.getX() - this.rotatedStartPoint.getX();
        percent = Math.max(Math.min(percent, 1), 0);

        double x = xAxisLength * percent;
        double y = a*Math.pow(x, 5) + b*Math.pow(x, 4) + c*Math.pow(x, 3) + d*Math.pow(x, 2) + e*x + f;
        double angle = Math.toDegrees(Math.atan(5*a*Math.pow(x, 4) + 4*b*Math.pow(x, 3) + 3*c*Math.pow(x, 2) + 2*d*x + e));

        return Waypoint.rotate(x, y, angle, angleOffset, xOffset, yOffset, true);
    }

    @Override
    public double getLength() {
        if (this.length > 0) {
            return this.length;
        }

        double length = 0;
        double deltaX = Math.abs(this.rotatedEndPoint.getX() - this.rotatedStartPoint.getX());

        double lastPoint[] = new double[2];
        double currentPoint[] = new double[2];

        for (double i = 0; i <= deltaX; i += deltaX/this.samples) {
            currentPoint[0] = i;
            currentPoint[1] = a*Math.pow(this.rotatedStartPoint.getX() + i, 5) + b*Math.pow(this.rotatedStartPoint.getX() + i, 4) + c*Math.pow(this.rotatedStartPoint.getX() + i, 3) + d*Math.pow(this.rotatedStartPoint.getX() + i, 2) + e*(this.rotatedStartPoint.getX() + i) + f;

            length += Math.sqrt(Math.pow(lastPoint[0] - currentPoint[0], 2) + Math.pow(lastPoint[1] - currentPoint[1], 2));

            lastPoint[0] = currentPoint[0];
            lastPoint[1] = currentPoint[1];
        }

        this.length = length;
        return length;
    }
}

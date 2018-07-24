package org.team2679.motion.spline;

import org.team2679.motion.Waypoint;

public class Cubic extends Polynomial {

    private int samples = 10000;
    private double length;

    private double a,b,c,d,e,f;

    private  Waypoint rotatedEndPoint;
    private Waypoint rotatedStartPoint;

    private double angleOffset, xOffset, yOffset;

    public Cubic(Waypoint startPoint, Waypoint endPoint){
        this(startPoint.getX(), startPoint.getY(), startPoint.getAngle(),
                endPoint.getX(), endPoint.getY(), endPoint.getAngle());
    }

    public Cubic(double x0, double y0, double angle0, double x1, double y1, double angle1){
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

        this.a = -(2* y0 - 2* y1 - tangent1* x0 + tangent1* x1 - tangent2* x0 + tangent2* x1)/((x0 - x1)*(Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2)));
        this.b = (3* x0 * y0 - 3* x0 * y1 + 3* x1 * y0 - 3* x1 * y1 - tangent1*Math.pow(x0,2) + 2*tangent1*Math.pow(x1,2) - 2*tangent2*Math.pow(x0,2) + tangent2*Math.pow(x1,2) - tangent1* x0 * x1 + tangent2* x0 * x1)/((x0 - x1)*(Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2)));
        this.c = -(tangent1*Math.pow(x1,3) - tangent2*Math.pow(x0,3) + tangent1* x0 *Math.pow(x1,2) - 2*tangent1*Math.pow(x0,2)* x1 + 2*tangent2* x0 *Math.pow(x1,2) - tangent2*Math.pow(x0,2)* x1 + 6* x0 * x1 * y0 - 6* x0 * x1 * y1)/((x0 - x1)*(Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2)));
        this.d = (Math.pow(x0,3)* y1 - Math.pow(x1,3)* y0 + tangent1* x0 *Math.pow(x1,3) - tangent2*Math.pow(x0,3)* x1 + 3* x0 *Math.pow(x1,2)* y0 - 3*Math.pow(x0,2)* x1 * y1 - tangent1*Math.pow(x0,2)*Math.pow(x1,2) + tangent2*Math.pow(x0,2)*Math.pow(x1,2))/((x0 - x1)*(Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2)));

    }

    @Override
    public Waypoint getWaypointByPrecent(double percent){
        double length = this.rotatedEndPoint.getX() - this.rotatedStartPoint.getX();
        percent = Math.max(Math.min(percent, 1), 0);

        double x = length * percent;
        double y = a*Math.pow(x, 3) + b*Math.pow(x, 2) + c*x + d;
        double angle = Math.toDegrees(Math.atan(3*a*Math.pow(x, 2) + 2*b*x + c));

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
            currentPoint[1] = a*Math.pow(this.rotatedStartPoint.getX() + i, 3) + b*Math.pow(this.rotatedStartPoint.getX() + i, 2) + c*(this.rotatedStartPoint.getX() + i) + d;

            length += Math.sqrt(Math.pow(lastPoint[0] - currentPoint[0], 2) + Math.pow(lastPoint[1] - currentPoint[1], 2));

            lastPoint[0] = currentPoint[0];
            lastPoint[1] = currentPoint[1];
        }

        this.length = length;
        return length;
    }
}

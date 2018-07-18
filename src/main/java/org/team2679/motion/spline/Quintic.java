package org.team2679.motion.spline;

import org.team2679.motion.Waypoint;

public class Quintic extends Polynomial {

    private int samples = 10000;

    private double a,b,c,d,e,f = 0;
    private double tangent1, tangent2;
    private double x0, y0, x1, y1;
    private double angle1 = 0;
    private double angle2 = 0;

    private double length = 0;

    private Waypoint startPoint = null;
    private Waypoint endPoint = null;

    public Quintic(Waypoint startPoint, Waypoint endPoint){
        this.x0 = startPoint.getX();
        this.x1 = endPoint.getX();
        this.y0 = startPoint.getY();
        this.y1 = endPoint.getY();

        this.angle1 = startPoint.getAngle();
        this.angle2 = endPoint.getAngle();

        this.startPoint = startPoint;
        this.endPoint = endPoint;

        if(angle1 > 90 || angle1 < -90 || angle2 > 90 || angle2 < -90){
            throw new RuntimeException("Angles must be between -90 and 90");
        }
        if(x0 - x1 >= 0){
            throw new RuntimeException("X values of waypoints must be growing");
        }

        this.tangent1 = Math.tan(Math.toRadians(angle1));
        this.tangent2 = Math.tan(Math.toRadians(angle2));

        this.a = (6* y1 - 3* x1 *(tangent2 + tangent1) + 3*(tangent2 + tangent1)* x0 - 6* y0)/Math.pow((x1 - x0), 5);
        this.b = (8*Math.pow(x1,2)* tangent1 + tangent2 *(7*Math.pow(x1,2) + x1 * x0 - 8*Math.pow(x0,2)) - x0 *(15* y1 + 7* tangent1 * x0 - 15* y0) + x1 *(-15* y1 - tangent1 * x0 + 15* y0))/Math.pow((x1 - x0),5);
        this.c = -(2*(2* tangent1 *Math.pow(x0,3) - 3* tangent1 *Math.pow(x1,3) + 3* tangent2 *Math.pow(x0,3) - 2* tangent2 *Math.pow(x1,3) - 5*Math.pow(x0,2)* y0 + 5*Math.pow(x0,2)* y1 - 5*Math.pow(x1,2)* y0 + 5*Math.pow(x1,2)* y1 - 7* tangent1 * x0 *Math.pow(x1,2) + 8* tangent1 *Math.pow(x0,2)* x1 - 8* tangent2 * x0 *Math.pow(x1,2) + 7* tangent2 *Math.pow(x0,2)* x1 - 20* x0 * x1 * y0 + 20* x0 * x1 * y1))/(Math.pow((x0 - x1),2)*(Math.pow(x0,3) - 3*Math.pow(x0,2)* x1 + 3* x0 *Math.pow(x1,2) - Math.pow(x1,3)));
        this.d = -(6* x0 *(3* tangent1 *Math.pow(x1,3) + 2* tangent2 *Math.pow(x1,3) + 5*Math.pow(x1,2)* y0 - 5*Math.pow(x1,2)* y1 - tangent1 * x0 *Math.pow(x1,2) - 2* tangent1 *Math.pow(x0,2)* x1 + tangent2 * x0 *Math.pow(x1,2) - 3* tangent2 *Math.pow(x0,2)* x1 + 5* x0 * x1 * y0 - 5* x0 * x1 * y1))/((Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2))*(Math.pow(x0,3) - 3*Math.pow(x0,2)* x1 + 3* x0 *Math.pow(x1,2) - Math.pow(x1,3)));
        this.e = -(tangent1 *Math.pow(x1,5) - tangent2 *Math.pow(x0,5) - 5* tangent1 * x0 *Math.pow(x1,4) + 5* tangent2 *Math.pow(x0,4)* x1 - 8* tangent1 *Math.pow(x0,2)*Math.pow(x1,3) + 12* tangent1 *Math.pow(x0,3)*Math.pow(x1,2) - 12* tangent2 *Math.pow(x0,2)*Math.pow(x1,3) + 8* tangent2 *Math.pow(x0,3)*Math.pow(x1,2) - 30*Math.pow(x0,2)*Math.pow(x1,2)* y0 + 30*Math.pow(x0,2)*Math.pow(x1,2)* y1)/(Math.pow((x0 - x1),2)*(Math.pow(x0,3) - 3*Math.pow(x0,2)* x1 + 3* x0 *Math.pow(x1,2) - Math.pow(x1,3)));
        this.f = (Math.pow(x0,5)* y1 - Math.pow(x1,5)* y0 + tangent1 * x0 *Math.pow(x1,5) - tangent2 *Math.pow(x0,5)* x1 + 5* x0 *Math.pow(x1,4)* y0 - 5*Math.pow(x0,4)* x1 * y1 - 5* tangent1 *Math.pow(x0,2)*Math.pow(x1,4) + 4* tangent1 *Math.pow(x0,3)*Math.pow(x1,3) - 4* tangent2 *Math.pow(x0,3)*Math.pow(x1,3) + 5* tangent2 *Math.pow(x0,4)*Math.pow(x1,2) - 10*Math.pow(x0,2)*Math.pow(x1,3)* y0 + 10*Math.pow(x0,3)*Math.pow(x1,2)* y1)/((Math.pow(x0,2) - 2* x0 * x1 + Math.pow(x1,2))*(Math.pow(x0,3) - 3*Math.pow(x0,2)* x1 + 3* x0 *Math.pow(x1,2) - Math.pow(x1,3)));
    }

    @Override
    public double getLength() {
        if (this.length > 0) {
            return this.length;
        }

        double length = 0;
        double deltaX = Math.abs(x1 - x0);

        double lastPoint[] = new double[2];
        double currentPoint[] = new double[2];

        for (double i = 0; i <= deltaX; i += deltaX/this.samples) {
            currentPoint[0] = i;
            currentPoint[1] = getYByX(x0 + i);

            length += Math.sqrt(Math.pow(lastPoint[0] - currentPoint[0], 2) + Math.pow(lastPoint[1] - currentPoint[1], 2));

            lastPoint[0] = currentPoint[0];
            lastPoint[1] = currentPoint[1];
        }

        this.length = length;
        return length;
    }

    @Override
    public double getYByX(double x){
        return a*Math.pow(x, 5) + b*Math.pow(x, 4) + c*Math.pow(x, 3) + d*Math.pow(x, 2) + e*x + f;
    }

    @Override
    public double getDerivativeByX(double x){
        return 5*a*Math.pow(x, 4) + 4*b*Math.pow(x, 3) + 3*c*Math.pow(x, 2) + 2*d*x + e;
    }

    @Override
    public double getAngleByX(double x){
        return Math.toDegrees(Math.atan(getDerivativeByX(x)));
    }

    @Override
    public Waypoint getStartPoint(){ return this.startPoint; }

    @Override
    public Waypoint getEndPoint(){
        return this.endPoint;
    }
}

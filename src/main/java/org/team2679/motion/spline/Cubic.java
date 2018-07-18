package org.team2679.motion.spline;

import org.team2679.motion.Waypoint;

public class Cubic extends Polynomial {

    private int samples = 10000;

    private double a,b,c,d = 0;
    private double tangent1, tangent2;

    private double x1,y1,x2,y2;
    private double angle1 = 0;
    private double angle2 = 0;

    private double length = 0;

    private Waypoint startPoint = null;
    private Waypoint endPoint = null;

    public Cubic(Waypoint startPoint, Waypoint endPoint){
        this.x1 = startPoint.getX();
        this.x2 = endPoint.getX();
        this.y1 = startPoint.getY();
        this.y2 = endPoint.getY();

        this.angle1 = startPoint.getAngle();
        this.angle2 = endPoint.getAngle();

        this.startPoint = startPoint;
        this.endPoint = endPoint;

        if(angle1 > 90 || angle1 < -90 || angle2 > 90 || angle2 < -90){
            throw new RuntimeException("Angles must be between -90 and 90");
        }
        if(x1 - x2 >= 0){
            throw new RuntimeException("X values of waypoints must be growing");
        }

        this.tangent1 = Math.tan(Math.toRadians(angle1));
        this.tangent2 = Math.tan(Math.toRadians(angle2));

        this.a = -(2*y1 - 2*y2 - tangent1*x1 + tangent1*x2 - tangent2*x1 + tangent2*x2)/((x1 - x2)*(Math.pow(x1,2) - 2*x1*x2 + Math.pow(x2,2)));
        this.b = (3*x1*y1 - 3*x1*y2 + 3*x2*y1 - 3*x2*y2 - tangent1*Math.pow(x1,2) + 2*tangent1*Math.pow(x2,2) - 2*tangent2*Math.pow(x1,2) + tangent2*Math.pow(x2,2) - tangent1*x1*x2 + tangent2*x1*x2)/((x1 - x2)*(Math.pow(x1,2) - 2*x1*x2 + Math.pow(x2,2)));
        this.c = -(tangent1*Math.pow(x2,3) - tangent2*Math.pow(x1,3) + tangent1*x1*Math.pow(x2,2) - 2*tangent1*Math.pow(x1,2)*x2 + 2*tangent2*x1*Math.pow(x2,2) - tangent2*Math.pow(x1,2)*x2 + 6*x1*x2*y1 - 6*x1*x2*y2)/((x1 - x2)*(Math.pow(x1,2) - 2*x1*x2 + Math.pow(x2,2)));
        this.d = (Math.pow(x1,3)*y2 - Math.pow(x2,3)*y1 + tangent1*x1*Math.pow(x2,3) - tangent2*Math.pow(x1,3)*x2 + 3*x1*Math.pow(x2,2)*y1 - 3*Math.pow(x1,2)*x2*y2 - tangent1*Math.pow(x1,2)*Math.pow(x2,2) + tangent2*Math.pow(x1,2)*Math.pow(x2,2))/((x1 - x2)*(Math.pow(x1,2) - 2*x1*x2 + Math.pow(x2,2)));

    }

    @Override
    public double getLength() {
        if (this.length > 0) {
            return this.length;
        }

        double length = 0;
        double deltaX = Math.abs(x2 - x1);

        double lastPoint[] = new double[2];
        double currentPoint[] = new double[2];

        for (double i = 0; i <= deltaX; i += deltaX/this.samples) {
            currentPoint[0] = i;
            currentPoint[1] = getYByX(x1 + i);

            length += Math.sqrt(Math.pow(lastPoint[0] - currentPoint[0], 2) + Math.pow(lastPoint[1] - currentPoint[1], 2));

            lastPoint[0] = currentPoint[0];
            lastPoint[1] = currentPoint[1];
        }

        this.length = length;
        return length;
    }

    @Override
    public double getYByX(double x){
        return a*Math.pow(x, 3) + b*Math.pow(x, 2) + c*x + d;
    }

    @Override
    public double getDerivativeByX(double x){
        return 3*this.a*Math.pow(x, 2) + 2*this.b*x + this.c;
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

package org.team2679.motion.spline;

public class Spline {

    private int samples = 10000;

    private double a,b,c,d = 0;
    private double tangent1, tangent2;

    private double x1,y1,x2,y2;
    private double angle1 = 0;
    private double angle2 = 0;

    private double length = 0;

    enum SPLINE_TYPE{
        CUBIC
    }

    public Spline(Waypoint startPoint, Waypoint endPoint){
        this.x1 = startPoint.getX();
        this.x2 = endPoint.getX();
        this.y1 = startPoint.getY();
        this.y2 = endPoint.getY();

        this.angle1 = startPoint.getAngle();
        this.angle2 = endPoint.getAngle();

        solveSpline();
    }

    public Spline(double x1, double y1, double angle1, double x2, double y2, double angle2)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        this.angle1 = angle1;
        this.angle2 = angle2;

        solveSpline();
    }

    /*
     *  What we did is to take the cubic equation and solve the equations for a, b, c and d.
     *  Ugly isn't it? well it's math...
     */
    private void solveSpline(){
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

    public double calculateLength() {
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

    public double[] getPoints(int amount) {
        return new double[2];
    }

    public double getYByX(double x){
        return a*Math.pow(x, 3) + b*Math.pow(x, 2) + c*x + d;
    }

    public double getDerivativeByX(double x){
        return 3*this.a*Math.pow(x, 2) + 2*this.b*x + this.c;
    }

    public double getAngleByX(double x){
        return Math.toDegrees(Math.atan(getDerivativeByX(x)));
    }

    public String getFormula()
    {
        return "y = " + a +"x^3 + " + b + "x^2 + " + c + "x + " + d;
    }
}

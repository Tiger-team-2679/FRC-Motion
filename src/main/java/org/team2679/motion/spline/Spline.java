package org.team2679.motion.spline;

public class Spline {

    private double a,b,c,d = 0;

    enum SPLINE_TYPE{
        CUBIC
    }

    public Spline(Waypoint startPoint, Waypoint endPoint){
        solveSpline(startPoint.getX(), startPoint.getY(), startPoint.getTheta(), endPoint.getX(), endPoint.getY(), endPoint.getTheta());
    }

    public Spline(double x1, double y1, double t1, double x2, double y2, double t2){
        solveSpline(x1,y1,t1,x2,y2,t2);
    }

    /*
     *  What we did is to take the cubic equation and solve the equations for a, b, c and d.
     *  Ugly isn't it? well it's math...
     */
    //  (n ((-2 + i) + t) - (i + t) x + 2 y)/(n - x)^3
    private void solveSpline(double x1, double y1, double t1, double x2, double y2, double t2){
        this.a = (y2*((t2 - 2) + t1) - (t2-t1)*x1 + 2*y1)/Math.pow((x2 - x1), 3);
        // (-x2^2 (t2 + 2 t1) + x2 (3 y2 + (-t2 + t1) x1 - 3 y1) + x1 (3 y2 + (2 t2 + t1) x1 - 3 y1))/(x2 - x1)^3
        this.b = (Math.pow(-x2, 2)*(t2 + 2*t1) + x2*(3*y2 + (-t2 + t1)*x1 - 3*y1) + x1*(3*y2 + (2*t2 + t1)*x1 - 3*y1))/Math.pow((x2 - x1), 3);
        // (x2^3 t + x2^2 (2 t2 + t) x1 - t2 x1^3 - x2 x1 (6 y2 + t2 x1 + 2 t x1 - 6 y1))/(x2 - x1)^3
        this.c = (Math.pow(x2, 3)*t1 + Math.pow(x2, 2)*(2*t2 + t1)*x1 - t2*Math.pow(x1, 3) - x2*x1*(6*y2 + t2*x1 + 2*t1*x1 - 6*y1))/Math.pow((x2 - x1), 3);
        // (x2 (3 y2 + t2 x1) x1^2 - y2 x1^3 + x2^2 x1 ((-t2 + t1) x1 - 3 y1) + x2^3 (-t1 x1 + y1))/(x2 - x1)^3
        this.d = (x2*(3*y2 + t2*x1)*Math.pow(x1, 2) - y2*Math.pow(x1, 3) + Math.pow(x2, 2)*x1*((t1 - t2)*x1 - 3*y1) + Math.pow(x2, 3)*(-t1*x1 + y1))/Math.pow((x2 - x1), 3);
        this.d = y1 - Math.pow(x1, 3)*a - Math.pow(x1,2)*b - x1*c;
    }

    public String getFormula(){
        return "y = " + a +  "x^3 + " + b + "x^2 + " + c + "x + " + d;
    }
}

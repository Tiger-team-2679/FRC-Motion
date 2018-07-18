package org.team2679.motion.spline;

public class main {
    public static void main(String args[]){
        double startTime = System.nanoTime();
        Waypoint p = new Waypoint(0,0,0);
        Waypoint p2 = new Waypoint(20,20, -50);
        Spline mySpline = new Spline(p, p2);
        mySpline.calculateLength();
        double now = System.nanoTime();
        System.out.println("elapse time = " + ((now - startTime)/1e6));
        System.out.println(mySpline.getFormula());
        System.out.println(mySpline.calculateLength());
        System.out.println(mySpline.getXandY(0.6)[1]);
    }
}

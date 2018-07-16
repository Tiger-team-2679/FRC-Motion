package org.team2679.motion.spline;

public class main {
    public static void main(String args[]){
        Waypoint p = new Waypoint(0,0,0);
        Waypoint p2 = new Waypoint(10,10, 1);
        Spline mySpline = new Spline(p, p2);
        System.out.println(mySpline.getFormula());
    }
}

package org.team2679.motion.spline;

public class Path {

    public Path(Waypoint[] waypoints, Spline.SPLINE_TYPE type){
        Spline[] splines = new Spline[waypoints.length - 1];

        for(int i = 0; i < waypoints.length; i++){
            splines[i] = new Spline(waypoints[i], waypoints[i + 1]);
        }
    }
}

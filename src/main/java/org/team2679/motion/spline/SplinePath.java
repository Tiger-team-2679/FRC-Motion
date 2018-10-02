package org.team2679.motion.spline;

import java.util.ArrayList;

public abstract class SplinePath {
    /**
     * get the total length of the path
     * @return length of the path
     */
    public abstract double get_length() throws Exception;
    /**
     * append the spline path with waypoints
     * @param waypoints waypoints to add
     */
    public abstract void append(Waypoint... waypoints) throws Exception;
    /**
     * checks if the spline path is empty
     * @return is the path empty
     */
    public abstract boolean isEmpty();
}

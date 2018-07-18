package org.team2679.motion.spline;

import org.team2679.motion.Waypoint;

public abstract class Polynomial {

    abstract public double getLength();

    abstract public double getYByX(double x);

    abstract public double getDerivativeByX(double x);

    abstract public double getAngleByX(double x);

    abstract public Waypoint getStartPoint();

    abstract public Waypoint getEndPoint();

}

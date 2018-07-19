package org.team2679.motion;

public class Waypoint {

    private double x,y,angle,velocity,acceleration,curvature = 0;

    public Waypoint(double x, double y, double angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public Waypoint(double x, double y, double angle, double curvature){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.curvature = curvature;
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getAngle(){
        return this.angle;
    }
    public double getVelocity() { return this.velocity; }
    public double getAcceleration() { return this.acceleration; }
    public double getCurvature() { return this.curvature; }


    public static Waypoint rotate(double x, double y, double angle, double angleOffset, double xOffset, double yOffset, boolean revert){
        if(revert){
            return new Waypoint(x*Math.cos(Math.toRadians(angleOffset)) - y*Math.sin(Math.toRadians(angleOffset)) + xOffset,
                    x *Math.sin(Math.toRadians(angleOffset)) + y*Math.cos(Math.toRadians(angleOffset)) + yOffset,
                    angle + angleOffset);
        }
        return new Waypoint((x - xOffset)*Math.cos(Math.toRadians(-angleOffset)) - (y - yOffset)*Math.sin(Math.toRadians(-angleOffset)),
                (x - xOffset) *Math.sin(Math.toRadians(-angleOffset)) + (y - yOffset)*Math.cos(Math.toRadians(-angleOffset)),
                angle - angleOffset);
    }

    public static Waypoint rotate(Waypoint p, double angleOffset, double xOffset, double yOffset, boolean revert){
        return rotate(p.getX(), p.getY(), p.getAngle(), angleOffset, xOffset, yOffset, revert);
    }

}

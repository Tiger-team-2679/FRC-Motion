package org.team2679.motion.controls;

public class PID {
    private double lastTime = -1;
    private double lastError;
    private double integral;
    private double kp;
    private double ki;
    private double kd;

    public PID(double kp, double ki, double kd) {
        this.lastError = 0;
        this.integral = 0;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }

    public double update(double currentPoint, double setPoint) {
        if(lastTime == -1){
            this.lastTime = System.nanoTime();
        }
        double interval = System.nanoTime() - this.lastTime;
        double error = setPoint - currentPoint;
        this.integral = this.integral + (error + lastError)*interval/2;
        double derivative = (error-this.lastError)/interval;

        return kp*error + ki*integral + kd*derivative;
    }
}
package org.team2679.motion.trajectory;

public class TrajectoryConfig {

    /**
     * contains the interpolation methods
     */
    public enum  INTERPOLATION_METHOD{
        FIXED_INTERVALS, CURVATUR, FIXED_INTERVALS_AND_CURVATUR;
    }

    INTERPOLATION_METHOD interpolation_method;

    /**
     * creates a new trajectory configuration
     */
    public TrajectoryConfig(INTERPOLATION_METHOD interpolation_method){
        this.interpolation_method = interpolation_method;
    }

}

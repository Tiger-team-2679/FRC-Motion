package org.team2679.motion.gui;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GUIProperties {

    private final static Image image = new Image("/fieldImage.png");

    public final static double CANVAS_WIDTH = image.getWidth();
    public final static double CANVAS_HEIGHT = image.getHeight();

    public final static int FIELD_HEIGHT = 0;
    public final static int FIELD_WIDTH = 0;

    public final static int CONTROL_POINTS_RADIUS = 10;
    public final static int DIRECTION_POINTS_RADIUS = 15;

    public final static Color SPLINE_PATH_COLOR = Color.LIMEGREEN;
    public final static Color SPLINE_PATH_ERROR_COLOR =Color.RED;
}

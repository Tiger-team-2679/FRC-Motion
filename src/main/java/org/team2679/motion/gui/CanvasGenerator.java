package org.team2679.motion.gui;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.team2679.motion.gui.canvas.GuiWaypoint;
import org.team2679.motion.spline.CatmullRomSpline;
import org.team2679.motion.spline.Spline;
import org.team2679.motion.spline.Waypoint;

import java.util.ArrayList;

public class CanvasGenerator{

    private static StackPane main_pane;
    private static Pane points_pane;
    private static Canvas spline_canvas;

    private static ArrayList<Waypoint> waypoints = new ArrayList<>();

    private static Spline drawen_spline;
    private static Spline.SPLINE_TYPE drawen_spline_type = Spline.SPLINE_TYPE.CATMULL_ROM_SPLINE;

    /**
     * generate the pane used buy the main Gui object
     * @return the Pane used by the Gui
     */
    public static StackPane generate_canvas_object(){
        main_pane = new StackPane();
        main_pane.setPrefSize(GUIProperties.CANVAS_WIDTH, GUIProperties.CANVAS_HEIGHT);

        load_background_field_image();
        load_points_canvas_pane();

        update();

        return main_pane;
    }

    private static void load_background_field_image(){
        // TODO just implement adding a new Image object
    }

    /**
     * loads the canvas to the spline drawing
     */
    private static void load_points_canvas_pane(){
        points_pane = new Pane();
        points_pane.setPrefSize(GUIProperties.CANVAS_WIDTH, GUIProperties.CANVAS_HEIGHT);
        points_pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.SECONDARY)){
                    addPoint(new Waypoint(event.getX(), event.getY()));
                }
            }
        });

        ImageView imageView = new ImageView();
        Image image=new Image("/fieldImage.png");
        imageView.setImage(image);

        spline_canvas = new Canvas();
        spline_canvas.setWidth(image.getWidth());
        spline_canvas.setHeight(image.getHeight());

        main_pane.getChildren().addAll(imageView, spline_canvas, points_pane);
    }

    private static void update(){
        drawen_spline = null;
        if(drawen_spline_type == Spline.SPLINE_TYPE.CATMULL_ROM_SPLINE){
            try {
                drawen_spline = new CatmullRomSpline(waypoints);
                System.out.println("spline created!");
            } catch (Exception e) {
                drawen_spline = null;
            }
        }
        points_pane.getChildren().clear();
        for (Waypoint waypoint : waypoints) {
            GuiWaypoint gui_waypoint = new GuiWaypoint(waypoint) {
                @Override
                public void onMove() {
                    draw_spline_path();
                }
            };
            gui_waypoint.addToPane(points_pane);
        }
        draw_spline_path();
    }

    private static void draw_spline_path(){
        spline_canvas.getGraphicsContext2D().clearRect(0,0, spline_canvas.getWidth(), spline_canvas.getHeight());
        spline_canvas.getGraphicsContext2D().setLineWidth(1.5);
        spline_canvas.getGraphicsContext2D().setStroke(GUIProperties.SPLINE_PATH_ERROR_COLOR);
        if(waypoints.size() > 1) {
            for (int i = 0; i < waypoints.size() - 1; i++) {
                spline_canvas.getGraphicsContext2D().strokeLine(waypoints.get(i).getX(), waypoints.get(i).getY(), waypoints.get(i + 1).getX(), waypoints.get(i + 1).getY());
            }
        }
        spline_canvas.getGraphicsContext2D().setLineWidth(3.0);
        if(drawen_spline != null) {
            spline_canvas.getGraphicsContext2D().setStroke(GUIProperties.SPLINE_PATH_COLOR);
            int num_of_points = 500;
            double delta = 1.0 / (double) num_of_points;
            for (double i = 0; i < 1 - delta; i += delta) {
                try {
                    double x0 = drawen_spline.interpolate_X(i);
                    double y0 = drawen_spline.interpolate_Y(i);
                    double x1 = drawen_spline.interpolate_X(i + delta);
                    double y1 = drawen_spline.interpolate_Y(i + delta);
                    spline_canvas.getGraphicsContext2D().strokeLine(x0, y0, x1, y1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * adds a new point to the pane
     * @param p
     */
    private static void addPoint(Waypoint p){
        waypoints.add(p);
        update();
    }

    /**
     * removes a waypoint from the screen
     * @param point
     */
    private static void removePoint(Waypoint point){
        waypoints.remove(point);
    }

    /**
     * clears all the points
     */
    public static void clear(){
        waypoints.clear();
        update();
    }
}

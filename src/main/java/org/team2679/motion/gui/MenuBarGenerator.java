package org.team2679.motion.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarGenerator{

    public static MenuBar generate_MenuBar(){
        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu options_menu = new Menu("Menu");

        init_options_menu_items(options_menu);

        menuBar.getMenus().addAll(options_menu);

        return menuBar;
    }

    private static void init_options_menu_items (Menu menu){
        MenuItem clear = new MenuItem();
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CanvasGenerator.clear();
            }
        });
        MenuItem exit = new MenuItem();
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        clear.setText("Clear");
        exit.setText("Exit");

        menu.getItems().addAll(clear, exit);
    }
}

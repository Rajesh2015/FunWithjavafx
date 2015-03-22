package saturdayfun.rajesh.switchbutton.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import saturdayfun.rajesh.switchbutton.SlideCheckBox;

public class Demo extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Hello World!");
    VBox root = new VBox();
    SlideCheckBox slideCheckBox = new SlideCheckBox();
    root.getChildren().add(slideCheckBox);
    primaryStage.setScene(new Scene(root, 300, 250));
    primaryStage.show();
  }
}
package saturdayfun.rajesh.carousel.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import saturdayfun.rajesh.carousel.AbstractCoverFlow;
import saturdayfun.rajesh.carousel.CoverFlow;

public class Demo extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Hello World!");
    HBox contenthBox = new HBox(10);
    VBox.setVgrow(contenthBox, Priority.ALWAYS);

    contenthBox.setAlignment(Pos.CENTER);
    VBox b[] = new VBox[10];
    for (int i = 0; i < b.length; i++) {
      b[i] = new VBox();
      b[i].setPrefHeight(150);
      b[i].setPrefWidth(150);

    }

    contenthBox.getChildren().addAll(b);
    contenthBox.setAlignment(Pos.CENTER);
    AbstractCoverFlow cutsom = new CoverFlow(contenthBox);
    VBox root = new VBox();

    root.getChildren().add(cutsom);
    primaryStage.setScene(new Scene(root, 300, 250));
    primaryStage.show();
  }
}
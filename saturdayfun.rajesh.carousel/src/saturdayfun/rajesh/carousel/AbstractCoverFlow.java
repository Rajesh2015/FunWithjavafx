package saturdayfun.rajesh.carousel;

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public abstract class AbstractCoverFlow extends VBox {
  private boolean isanimationstop = true;

  private final Node contentNode;
  private String stylerCss = AbstractCoverFlow.class.getResource("css/FxStyler.css")
      .toExternalForm();
  private ScrollPane scrollPane;
  private HBox rootHBox;
  private TranslateTransition animationForRightContentnode;
  private double widthToMove;
  private TranslateTransition animationForRightContentnode2;
  private TranslateTransition animationForLeftContentnode;
  private TranslateTransition animationForLeftContentnode2;
  private ObservableList<Node> children;

  /**
   * Inner Class To support click
   * 
   * @author nxp58501
   * 
   */
  private final class NodeFocousListener implements EventHandler<MouseEvent> {
    private Node node;

    public NodeFocousListener(Node node) {
      this.node = node;

    }

    @Override
    public void handle(MouseEvent event) {
      for (Node n : children) {
        if (!n.equals(node)) {
          ((VBox) n).setId("NodeNotonFocous");
        }
      }

      ((VBox) node).setId("NodeonFocous");
      CoverflowSelectionManager.getInstance().fireselection((VBox) node);
    }
  }

  /**
   * Default constructor
   */
  public AbstractCoverFlow() {
    this.contentNode = null;
    setFillWidth(true);

    this.getStylesheets().add(stylerCss);
  }

  /**
   * Constructor for creating coverflow
   * 
   * @param node
   * @param featuresNode
   */
  public AbstractCoverFlow(Node node) {
    this.contentNode = node;

    createContents();
    setFillWidth(true);

    this.getStylesheets().add(stylerCss);
  }

  public void createContents() {

    HBox createCustomPane = createCustomPane();

    this.getChildren().add(createCustomPane);
  }

  /**
   * 
   * @return HBox
   */
  public HBox createCustomPane() {

    /**
     * Animation For left And right navigation
     */
    animationForRightContentnode = new TranslateTransition(Duration.millis(300), getContentNode());
    animationForRightContentnode.setCycleCount(1);
    animationForRightContentnode.setInterpolator(Interpolator.LINEAR);
    animationForLeftContentnode = new TranslateTransition(Duration.millis(1), getContentNode());
    animationForLeftContentnode.setCycleCount(1);
    animationForLeftContentnode.setInterpolator(Interpolator.LINEAR);
    animationForRightContentnode2 = new TranslateTransition(Duration.millis(1), getContentNode());
    animationForRightContentnode2.setCycleCount(1);
    animationForRightContentnode2.setInterpolator(Interpolator.LINEAR);
    animationForLeftContentnode2 = new TranslateTransition(Duration.millis(300), getContentNode());
    animationForLeftContentnode2.setCycleCount(1);
    animationForLeftContentnode2.setInterpolator(Interpolator.LINEAR);
    /**
     * Adding to map to create a kind of mapping
     */
    children = ((HBox) getContentNode()).getChildren();

    addFocousListener();
    children.get(0).setId("NodeonFocous");

    rootHBox = new HBox();
    rootHBox.setPrefHeight(155);
    rootHBox.setFillHeight(true);
    setVgrow(rootHBox, Priority.ALWAYS);
    rootHBox.setAlignment(Pos.CENTER);

    Button previousButton = new Button();

    Image rightnavigationbutton = new Image(getClass().getResourceAsStream(
        "image/leftnavigation.PNG"));
    previousButton.setGraphic(new ImageView(rightnavigationbutton));
    previousButton.setId("Scanbutton");
    previousButton.setFont(Font.font("segoe ui", FontWeight.BOLD, 20));
    previousButton.setTextFill(Color.BLACK);
    Button nextButton = new Button();
    Image leftnavigationbutton = new Image(getClass().getResourceAsStream(
        "image/rightnavigation.PNG"));
    nextButton.setGraphic(new ImageView(leftnavigationbutton));
    nextButton.setId("Scanbutton");
    nextButton.setFont(Font.font("segoe ui", FontWeight.BOLD, 20));
    nextButton.setTextFill(Color.BLACK);

    scrollPane = new ScrollPane();
    scrollPane.setStyle("-fx-background-color:transparent;");
    scrollPane.setContent(getContentNode());
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

    HBox.setHgrow(scrollPane, Priority.ALWAYS);

    previousButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent me) {
        animationForLeftContentnode2.statusProperty().addListener(new ChangeListener<Status>() {

          @Override
          public void changed(ObservableValue<? extends Status> observable, Status oldValue,
              Status newValue) {
            if (newValue == Status.STOPPED) {
              System.out.println("STopped");
              isanimationstop = true;
            }
          }
        });
        /**
         * To make sure if the animation is not finished it will not allow user
         * for button click
         */
        performAnimationOnPrevButtonClicked();
      }

    });
    nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent me) {
        animationForRightContentnode2.statusProperty().addListener(new ChangeListener<Status>() {

          @Override
          public void changed(ObservableValue<? extends Status> observable, Status oldValue,
              Status newValue) {
            if (newValue == Status.STOPPED) {
              System.out.println("STopped");
              isanimationstop = true;
            }
          }
        });
        /**
         * To make sure if the animation is not finished it will not allow user
         * for button click
         */
        performAnimationOnnextButtonClicked();

      }

    });

    rootHBox.getChildren().addAll(previousButton, scrollPane, nextButton);

    return rootHBox;
  }

  /**
   * For fadeout transition of features pane
   * 
   * @param children
   */
  public abstract void addFadeInAndFadeOutTransitionForFeatures(ObservableList<Node> children);

  protected void performAnimationOnPrevButtonClicked() {
    if (isanimationstop) {
      isanimationstop = false;
      /**
       * If any of the mode is focoused then it will remove the focous
       */
      for (Node node : children) {
        if (node instanceof VBox) {
          ((VBox) node).setId("NodeNotonFocous");
        }
      }
      /**
       * To make sure if the animation is not finished it will not allow user
       * for button click
       */

      ObservableList<Node> children = ((HBox) getContentNode()).getChildren();

      ((VBox) children.get(0)).setId("NodeNotonFocous");
      Node node = children.get(children.size() - 1);
      children.remove(node);
      children.add(0, node);
      node = children.get(0);

      ((VBox) node).setId("NodeonFocous");
      CoverflowSelectionManager.getInstance().fireselection(((VBox) node));
      addFadeInAndFadeOutTransitionForFeatures(children);
      Bounds boundsInLocal = children.get(0).getBoundsInLocal();
      Bounds localToParent = children.get(0).localToParent(boundsInLocal);
      widthToMove = 0 - (localToParent.getMaxY() - 5);
      animationForLeftContentnode.setByX(widthToMove);

      animationForLeftContentnode.play();

      animationForLeftContentnode.setOnFinished(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {

          animationForLeftContentnode2.setByX(Math.abs(widthToMove));

          animationForLeftContentnode2.play();
        }
      });
    }
  }

  protected void performAnimationOnnextButtonClicked() {
    if (isanimationstop) {
      isanimationstop = false;
      /**
       * If any of the mode is focoused then it will remove the focous
       */
      for (Node node : children) {
        if (node instanceof VBox) {
          ((VBox) node).setId("NodeNotonFocous");
        }
      }

      if (animationForRightContentnode.getCurrentRate() != 0.0d
          && animationForRightContentnode2.getCurrentRate() != 0.0d) {
        return;
      }
      ObservableList<Node> children = ((HBox) getContentNode()).getChildren();
      Bounds boundsInLocal = children.get(0).getBoundsInLocal();
      Bounds localToParent = children.get(0).localToParent(boundsInLocal);
      widthToMove = 0 - (localToParent.getMaxY() - 5);
      animationForRightContentnode.setByX(widthToMove);

      animationForRightContentnode.play();

      animationForRightContentnode.setOnFinished(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {

          Node node = children.get(0);
          children.remove(node);
          ((VBox) node).setId("NodeNotonFocous");
          children.add(node);
          node = children.get(0);

          ((VBox) node).setId("NodeonFocous");
          CoverflowSelectionManager.getInstance().fireselection(((VBox) node));
          addFadeInAndFadeOutTransitionForFeatures(children);
          animationForRightContentnode2.setByX(Math.abs(widthToMove));
          animationForRightContentnode2.play();
        }
      });
    }
  }

  protected void addFocousListener() {
    for (Node node : children) {
      if (node instanceof VBox) {
        ((VBox) node).setId("NodeNotonFocous");
        node.setOnMouseClicked(new NodeFocousListener(node));
      }
    }
  }

  public Node getContentNode() {
    return contentNode;
  }
}

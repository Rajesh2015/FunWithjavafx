package saturdayfun.rajesh.switchbutton;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.NodeOrientation;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import com.sun.javafx.scene.control.skin.LabeledSkinBase;

/**
 * This class will define on off button skin
 * 
 * @author nxp58501
 * 
 */
public class SlideCheckBoxSkin extends
    LabeledSkinBase<SlideCheckBox, SlideCheckBoxBehavior<SlideCheckBox>> {
  private static final double BOX_WIDTH = 70;
  private static final double BOX_HEIGHT = 25;
  private static final double THUMB_WIDTH = 48;
  private static final double THUMB_HEIGHT = 34;
  private final StackPane box = new StackPane();
  private HBox onBox;
  private HBox offBox;
  private Region thumb;
  private Timeline selectTimeline;
  private Timeline deselectTimeline;

  // ******************** Constructors **************************************\
  /**
   * This will design on and off button
   * 
   * @param checkbox
   */
  public SlideCheckBoxSkin(SlideCheckBox checkbox) {
    super(checkbox, new SlideCheckBoxBehavior<>(checkbox));
    initGraphics();
    initTimelines();
    registerListeners();
  }

  // ******************** Initialization ************************************
  private void initGraphics() {
    onBox = new HBox();
    Label onLabel = new Label("On");
    onLabel.setTextFill(Color.BLACK);
    onBox.getChildren().add(onLabel);
    onBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    onBox.setTranslateX(10);
    onBox.setTranslateY(3);
    offBox = new HBox();
    Label OffLabel = new Label("Off");
    OffLabel.setTextFill(Color.BLACK);
    offBox.getChildren().add(OffLabel);
    offBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    offBox.setTranslateX(-8);
    offBox.setTranslateY(3);
    thumb = new Region();
    thumb.getStyleClass().setAll("thumb");
    thumb.setMinSize(35, 20);
    thumb.setPrefSize(35, 20);
    thumb.setMaxSize(35, 20);

    if (getSkinnable().isSelected()) {
      offBox.setOpacity(0);
      thumb.setTranslateX(16);
    } else {
      onBox.setOpacity(0);
      thumb.setTranslateX(-16);
    }

    box.getStyleClass().setAll("box");
    box.getChildren().addAll(onBox, offBox, thumb);
    updateChildren();
  }

  private void initTimelines() {
    selectTimeline = getSelectTimeline();
    deselectTimeline = getDeselectTimeline();
  }

  private void registerListeners() {
    getSkinnable().selectedProperty().addListener(observable -> toggle());
  }

  /**
   * This class update the look and feel on changing on to off and viceversa
   */
  // ******************** Methods *******************************************
  @Override
  protected void updateChildren() {
    super.updateChildren();
    if (box != null) {
      getChildren().add(box);
    }
  }

  /**
   * This will return minwidth
   * 
   * @return double
   */

  @Override
  protected double computeMinWidth(double height, double topInset, double rightInset,
      double bottomInset, double leftInset) {
    return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset)
        + snapSize(box.minWidth(-1));
  }

  /**
   * This will copunte minimum height
   * 
   * @return double
   */
  @Override
  protected double computeMinHeight(double width, double topInset, double rightInset,
      double bottomInset, double leftInset) {
    return Math.max(super.computeMinHeight(width - box.minWidth(-1), topInset, rightInset,
        bottomInset, leftInset), topInset + box.minHeight(-1) + bottomInset);
  }

  /**
   * Compute prefwidth of controls
   * 
   * @param height
   * @param topInset
   * @param rightInset
   * @return double
   */
  @Override
  protected double computePrefWidth(double height, double topInset, double rightInset,
      double bottomInset, double leftInset) {
    return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset)
        + snapSize(box.prefWidth(-1) + 46);
  }

  /**
   * Compute prefheight of controls
   * 
   * @param width
   *          : width of control
   * @param topInset
   *          :Top inset of control
   * @param rightInset
   *          :Right inset of control
   * 
   * @param bottomInset
   *          :Bottom inset of control
   * @param leftInset
   *          :Left Inset of control
   * @return double
   */
  @Override
  protected double computePrefHeight(double width, double topInset, double rightInset,
      double bottomInset, double leftInset) {
    return Math.max(super.computePrefHeight(width - box.prefWidth(-1), topInset, rightInset,
        bottomInset, leftInset), topInset + box.prefHeight(-1) + bottomInset);
  }

  /**
   * Layouting children of the control
   * 
   * @param x
   * @param y
   * @param w
   * @param h
   * 
   */

  @Override
  protected void layoutChildren(final double x, final double y, final double w, final double h) {
    final SlideCheckBox checkBox = getSkinnable();
    final double computeWidth = Math.max(checkBox.prefWidth(-1), checkBox.minWidth(-1));
    final double labelWidth = Math.min(computeWidth - BOX_WIDTH, w - snapSize(BOX_WIDTH) + 100);
    final double labelHeight = Math.min(checkBox.prefHeight(labelWidth), h);
    final double maxHeight = Math.max(BOX_HEIGHT, labelHeight);
    final double xOffset = computeXOffset(w, labelWidth + BOX_WIDTH, checkBox.getAlignment()
        .getHpos())
        + x;
    final double yOffset = computeYOffset(h, maxHeight, checkBox.getAlignment().getVpos()) + x;

    layoutLabelInArea(xOffset + BOX_WIDTH, yOffset, labelWidth, maxHeight, checkBox.getAlignment());
    thumb.resize(THUMB_WIDTH, THUMB_HEIGHT);
    box.resize(BOX_WIDTH, BOX_HEIGHT);
    positionInArea(box, xOffset, yOffset, BOX_WIDTH, maxHeight, 0, checkBox.getAlignment()
        .getHpos(), checkBox.getAlignment().getVpos());
  }

  private void toggle() {
    if (getSkinnable().isSelected()) {
      selectTimeline.play();
    } else {
      deselectTimeline.play();
    }
  }

  private Timeline getSelectTimeline() {
    final KeyValue kvThumbStartTranslateSelected = new KeyValue(thumb.translateXProperty(), -16,
        Interpolator.EASE_BOTH);
    final KeyValue kvThumbEndTranslateSelected = new KeyValue(thumb.translateXProperty(), 14,
        Interpolator.EASE_BOTH);

    final KeyValue kvMarkStartOpacitySelected = new KeyValue(onBox.opacityProperty(), 0,
        Interpolator.EASE_BOTH);
    final KeyValue kvMarkEndOpacitySelected = new KeyValue(onBox.opacityProperty(), 1,
        Interpolator.EASE_BOTH);

    final KeyValue kvMarkStartScaleXSelected = new KeyValue(onBox.scaleXProperty(), 1,
        Interpolator.EASE_BOTH);
    final KeyValue kvMarkEndScaleXSelected = new KeyValue(onBox.scaleXProperty(), 1,
        Interpolator.EASE_BOTH);

    final KeyValue kvMarkStartScaleYSelected = new KeyValue(onBox.scaleYProperty(), 1,
        Interpolator.EASE_BOTH);
    final KeyValue kvMarkEndScaleYSelected = new KeyValue(onBox.scaleYProperty(), 1,
        Interpolator.EASE_BOTH);

    final KeyValue kvMarkStartScaleUpXSelected = new KeyValue(onBox.scaleXProperty(), 1,
        Interpolator.EASE_BOTH);
    final KeyValue kvMarkEndScaleUpXSelected = new KeyValue(onBox.scaleXProperty(), 1,
        Interpolator.EASE_BOTH);

    final KeyValue kvMarkStartScaleUpYSelected = new KeyValue(onBox.scaleYProperty(), 1,
        Interpolator.EASE_BOTH);
    final KeyValue kvMarkEndScaleUpYSelected = new KeyValue(onBox.scaleYProperty(), 1,
        Interpolator.EASE_BOTH);

    final KeyValue kvMarkStartScaleDownXSelected = new KeyValue(onBox.scaleXProperty(), 1,
        Interpolator.EASE_BOTH);
    final KeyValue kvMarkEndScaleDownXSelected = new KeyValue(onBox.scaleXProperty(), 1,
        Interpolator.EASE_BOTH);

    final KeyValue kvMarkStartScaleDownYSelected = new KeyValue(onBox.scaleYProperty(), 1,
        Interpolator.EASE_BOTH);
    final KeyValue kvMarkEndScaleDownYSelected = new KeyValue(onBox.scaleYProperty(), 1,
        Interpolator.EASE_BOTH);

    final KeyValue kvCrossStartOpacitySelected = new KeyValue(offBox.opacityProperty(), 1,
        Interpolator.EASE_BOTH);
    final KeyValue kvCrossEndOpacitySelected = new KeyValue(offBox.opacityProperty(), 0,
        Interpolator.EASE_BOTH);

    final KeyValue kvCrossStartScaleXSelected = new KeyValue(offBox.scaleXProperty(), 1,
        Interpolator.EASE_BOTH);
    final KeyValue kvCrossEndScaleXSelected = new KeyValue(offBox.scaleXProperty(), 0,
        Interpolator.EASE_BOTH);

    final KeyValue kvCrossStartScaleYSelected = new KeyValue(offBox.scaleYProperty(), 1,
        Interpolator.EASE_BOTH);
    final KeyValue kvCrossEndScaleYSelected = new KeyValue(offBox.scaleYProperty(), 0,
        Interpolator.EASE_BOTH);

    final KeyFrame kfStart = new KeyFrame(Duration.ZERO, kvThumbStartTranslateSelected,
        kvMarkStartOpacitySelected, kvMarkStartScaleXSelected, kvMarkStartScaleYSelected,
        kvCrossStartOpacitySelected, kvCrossStartScaleXSelected, kvCrossStartScaleYSelected);
    final KeyFrame kfEnd = new KeyFrame(Duration.millis(180), kvThumbEndTranslateSelected,
        kvMarkEndOpacitySelected, kvMarkEndScaleXSelected, kvMarkEndScaleYSelected,
        kvCrossEndOpacitySelected, kvCrossEndScaleXSelected, kvCrossEndScaleYSelected);
    final KeyFrame kfScaleUpStart = new KeyFrame(Duration.millis(250), kvMarkStartScaleUpXSelected,
        kvMarkStartScaleUpYSelected);
    final KeyFrame kfScaleUpEnd = new KeyFrame(Duration.millis(350), kvMarkEndScaleUpXSelected,
        kvMarkEndScaleUpYSelected);
    final KeyFrame kfScaleDownStart = new KeyFrame(Duration.millis(350),
        kvMarkStartScaleDownXSelected, kvMarkStartScaleDownYSelected);
    final KeyFrame kfScaleDownEnd = new KeyFrame(Duration.millis(500), kvMarkEndScaleDownXSelected,
        kvMarkEndScaleDownYSelected);

    final Timeline timeline = new Timeline();
    timeline.getKeyFrames().setAll(kfStart, kfEnd, kfScaleUpStart, kfScaleUpEnd, kfScaleDownStart,
        kfScaleDownEnd);
    return timeline;
  }

  private Timeline getDeselectTimeline() {
    final KeyValue kvThumbStartTranslateDeselect = new KeyValue(thumb.translateXProperty(), 16);
    final KeyValue kvThumbEndTranslateDeselect = new KeyValue(thumb.translateXProperty(), -15);

    final KeyValue kvMarkStartOpacityDeselect = new KeyValue(onBox.opacityProperty(), 1);
    final KeyValue kvMarkEndOpacityDeselect = new KeyValue(onBox.opacityProperty(), 0);

    final KeyValue kvMarkStartScaleXDeselect = new KeyValue(onBox.scaleXProperty(), 1);
    final KeyValue kvMarkEndScaleXDeselect = new KeyValue(onBox.scaleXProperty(), 0);

    final KeyValue kvMarkStartScaleYDeselect = new KeyValue(onBox.scaleYProperty(), 1);
    final KeyValue kvMarkEndScaleYDeselect = new KeyValue(onBox.scaleYProperty(), 0);

    final KeyValue kvCrossStartOpacityDeselect = new KeyValue(offBox.opacityProperty(), 0);
    final KeyValue kvCrossEndOpacityDeselect = new KeyValue(offBox.opacityProperty(), 1);

    final KeyValue kvCrossStartScaleXDeselect = new KeyValue(offBox.scaleXProperty(), 0);
    final KeyValue kvCrossEndScaleXDeselect = new KeyValue(offBox.scaleXProperty(), 1);

    final KeyValue kvCrossStartScaleYDeselect = new KeyValue(offBox.scaleYProperty(), 0);
    final KeyValue kvCrossEndScaleYDeselect = new KeyValue(offBox.scaleYProperty(), 1);

    final KeyValue kvCrossRotateStart = new KeyValue(offBox.rotateProperty(), 0);
    final KeyValue kvCrossRotateEnd = new KeyValue(offBox.rotateProperty(), 0);

    final KeyFrame kfStart = new KeyFrame(Duration.ZERO, kvThumbStartTranslateDeselect,
        kvMarkStartOpacityDeselect, kvMarkStartScaleXDeselect, kvMarkStartScaleYDeselect,
        kvCrossStartOpacityDeselect, kvCrossStartScaleXDeselect, kvCrossStartScaleYDeselect);
    final KeyFrame kfEnd = new KeyFrame(Duration.millis(180), kvThumbEndTranslateDeselect,
        kvMarkEndOpacityDeselect, kvMarkEndScaleXDeselect, kvMarkEndScaleYDeselect,
        kvCrossEndOpacityDeselect, kvCrossEndScaleXDeselect, kvCrossEndScaleYDeselect);
    final KeyFrame kfRotateStart = new KeyFrame(Duration.millis(250), kvCrossRotateStart);
    final KeyFrame kfRotateEnd = new KeyFrame(Duration.millis(750), kvCrossRotateEnd);

    final Timeline timeline = new Timeline();
    timeline.getKeyFrames().setAll(kfStart, kfEnd, kfRotateStart, kfRotateEnd);
    timeline.setOnFinished(actionEvent -> offBox.setRotate(0));
    return timeline;
  }

  private static double computeXOffset(double width, double contentWidth, HPos hpos) {
    switch (hpos) {
    case LEFT:
      return 0;
    case CENTER:
      return (width - contentWidth) / 2;
    case RIGHT:
      return width - contentWidth;
    }
    return 0;
  }

  private static double computeYOffset(double height, double contentHeight, VPos vpos) {
    switch (vpos) {
    case TOP:
      return 0;
    case CENTER:
      return (height - contentHeight) / 2;
    case BOTTOM:
      return height - contentHeight;
    default:
      return 0;
    }
  }
}

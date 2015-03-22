package saturdayfun.rajesh.carousel;

import javafx.scene.layout.VBox;

@FunctionalInterface
public interface CoverflowSelectionListener {
  /**
   * Implemented By observer to get the event
   * 
   * @param vbox
   */
  void fireCoverFlowSelection(VBox vbox);

}

package saturdayfun.rajesh.carousel;

import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.layout.VBox;

public class CoverflowSelectionManager {
  private volatile static CoverflowSelectionManager coverflowSelectionManager;
  private CopyOnWriteArrayList<CoverflowSelectionListener> listofListener = new CopyOnWriteArrayList<>();

  private CoverflowSelectionManager() {
  }

  /**
   * Ensures doubled lock checking to avoid multiple instance creation of single
   * class
   * 
   * @return CoverflowSelectionManager
   */
  public static CoverflowSelectionManager getInstance() {
    if (coverflowSelectionManager == null) {
      synchronized (CoverflowSelectionManager.class) {
        if (coverflowSelectionManager == null) {
          coverflowSelectionManager = new CoverflowSelectionManager();
        }
      }
    }
    return coverflowSelectionManager;
  }

  /**
   * Register listener
   * 
   * @param listener
   */
  public void registerListener(CoverflowSelectionListener listener) {
    listofListener.add(listener);
  }

  /**
   * unRegister listener
   * 
   * @param listener
   */
  public void unRegisterListener(CoverflowSelectionListener listener) {
    listofListener.remove(listener);
  }

  /**
   * This part of the code will be called by the client to fire the selection so
   * that all the observer will get the notification
   * 
   * @param vbox
   */
  public void fireselection(VBox vbox) {
    for (CoverflowSelectionListener lisriListener : listofListener) {
      lisriListener.fireCoverFlowSelection(vbox);
    }
  }

}

package saturdayfun.rajesh.switchbutton;

import javafx.scene.control.CheckBox;

public class SlideCheckBox extends CheckBox {

  // ******************** Constructors **************************************
  public SlideCheckBox() {
    this("");
  }

  public SlideCheckBox(final String TEXT) {
    super(TEXT);
    getStylesheets().add(getClass().getResource("css/slidecheckbox.css").toExternalForm());
    setSkin(new SlideCheckBoxSkin(this));
  }
}

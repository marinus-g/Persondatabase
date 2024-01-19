package academy.mischok.persondatabase.ui.component;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public interface IComponent {

    void display(Pane scene);

    void hide(Pane scene);

    Pane getCurrentParent();
}

package academy.mischok.persondatabase.ui.component;

import javafx.scene.layout.Pane;

public interface IComponent {

    void display(Pane scene);

    void hide(Pane scene);

    Pane getCurrentParent();
}

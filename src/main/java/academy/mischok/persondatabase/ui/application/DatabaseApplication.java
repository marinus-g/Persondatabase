package academy.mischok.persondatabase.ui.application;

import academy.mischok.persondatabase.ui.PersonUi;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class DatabaseApplication extends Application {
    private Stage primaryStage;

    public static void startUi(Runnable runnable) {
        Thread t;
        ( t = new Thread(() -> {
            Application.launch(DatabaseApplication.class);
            runnable.run();
        })).start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage.show();
        Platform.setImplicitExit(false);
        PersonUi.PERSON_UI.setDatabaseApplication(this);
        PersonUi.PERSON_UI.init();
    }
}
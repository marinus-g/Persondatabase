package academy.mischok.persondatabase.ui.view;

import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.service.PersonService;
import academy.mischok.persondatabase.ui.PersonUi;
import academy.mischok.persondatabase.ui.column.UiColumn;
import academy.mischok.persondatabase.ui.component.FieldComponent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.*;
import java.util.stream.DoubleStream;


public class DatabaseView implements IView {

    private static final int START_Y = 40;
    private static final int MAX_PER_PAGE = 25;

    private final PersonService personService;
    @Getter
    private final PersonUi personUi;
    private final Pane root;
    private final Scene scene;
    private final Map<UiColumn, Double> startPositions = new HashMap<>();

    private List<Person> persons = new ArrayList<>();
    private final Map<UiColumn, List<FieldComponent>> databaseComponents = new HashMap<>();
    @Getter
    private final Set<FieldComponent> insertFields = new HashSet<>();
    private int page = 1;
    @Getter
    private final PersonDto personToAdd = new PersonDto();


    public DatabaseView(final PersonUi personUi, final PersonService personService) {
        this.personUi = personUi;
        this.personService = personService;
        this.root = new Pane();
        this.scene = new Scene(root, getWidth(), getHeight());
        this.init();
    }

    private void init() {
        Button closeButton  = new Button("Close");
        closeButton.setOnAction(event -> {
            this.handleCloseRequest();
        });
        closeButton.setLayoutX(500);
        this.personUi.getDatabaseApplication().getPrimaryStage().setTitle("Personendatenbank");
        this.personUi.getDatabaseApplication().getPrimaryStage().setScene(this.scene);

        double position = 10;
        for (UiColumn value : UiColumn.values()) {
            TextField textField = createTextField(value);
            textField.setLayoutX(position);
            textField.setLayoutY(START_Y);
            root.getChildren().add(textField);
            this.startPositions.put(value, position);
            position += value.getSize();
        }
        root.getChildren().add(closeButton);
        double rowPosY = START_Y + 20;
        for (UiColumn value : UiColumn.values()) {
            FieldComponent fieldComponent = new FieldComponent(value, null, this);
            fieldComponent.setLayoutX(this.startPositions.get(value));
            fieldComponent.setLayoutY(rowPosY);
            fieldComponent.display(root);
            this.insertFields.add(fieldComponent);
        }

        rowPosY += 22;
        queryPersons();
        initListButtons();
        if (persons.isEmpty()) {
            return;
        }
        for (int i = 0; i < Math.min(MAX_PER_PAGE, persons.size()); i++) {
            Person person = persons.get(i);
            for (UiColumn value : UiColumn.values()) {
                if (!databaseComponents.containsKey(value)) {
                    databaseComponents.put(value, new ArrayList<>());
                }
                FieldComponent fieldComponent = new FieldComponent(value, person, this);
                fieldComponent.setLayoutX(this.startPositions.get(value));
                fieldComponent.setLayoutY(rowPosY);
                fieldComponent.display(root);
                databaseComponents.get(value).add(fieldComponent);
            }
            rowPosY += 22;
        }
    }

    private void initListButtons() {
        Button nextButton = new Button(">");
        nextButton.setLayoutX(10);
        nextButton.setLayoutY(10);
        nextButton.setOnAction(event -> {
            if (page * MAX_PER_PAGE >= persons.size()) {
                return;
            }
            reloadDatabaseList(++page);
        });
        this.root.getChildren().add(nextButton);

        Button lastButton = new Button("<");
        lastButton.setLayoutX(50);
        lastButton.setLayoutY(10);
        lastButton.setOnAction(event -> {
            if (page == 1) {
                return;
            }
            reloadDatabaseList(--page);
        });
        this.root.getChildren().add(lastButton);
    }

    private void reloadDatabaseList(int page) {
        int perPage = MAX_PER_PAGE;
        int currentStart = (page - 1) * perPage;
        int currentEnd = Math.min(persons.size(), currentStart + perPage);
        int j = 0;
        for (int i = currentStart; i < currentEnd; i++) {
            Person person = persons.get(i);
            for (UiColumn value : UiColumn.values()) {
                int finalJ = j;
                Optional.ofNullable(databaseComponents.get(value).size() >= j ? null : databaseComponents.get(value).get(j))
                        .ifPresentOrElse(fieldComponent -> fieldComponent.setPerson(person), () -> {
                            FieldComponent fieldComponent = new FieldComponent(value, person, this);
                            fieldComponent.setLayoutX(this.startPositions.get(value));
                            fieldComponent.setLayoutY(START_Y + 20 + (finalJ + 1) * 22);
                            fieldComponent.display(root);
                            databaseComponents.get(value).add(fieldComponent);
                        });
            }
            j++;
        }
    }

    private void queryPersons() {
        this.persons = this.personService.findByFilterQuery(this.personUi.getFilterQuery());
    }


    private TextField createTextField(UiColumn column) {
        TextField textField = new TextField();
        textField.setId(UUID.randomUUID().toString());
        textField.setPrefWidth(column.getSize());
        textField.setPrefHeight(20);
        textField.setEditable(false);
        assert UiColumn.toColumn(column) != null;
        textField.setText(UiColumn.toColumn(column).getClearName());
        return textField;
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public double getHeight() {
        return 600;
    }

    @Override
    public double getWidth() {
        return Arrays.stream(UiColumn.values()).flatMapToDouble(column -> DoubleStream.of(column.getSize())).sum() + 20D + 50D;
    }

    public void refreshList() {
        this.queryPersons();
        this.reloadDatabaseList(this.page);
    }

    private void handleCloseRequest() {
        PersonUi.PERSON_UI.getDatabaseApplication().getPrimaryStage().hide();
        PersonUi.PERSON_UI.setRunning(false);
        System.out.println("close request");
    }
}
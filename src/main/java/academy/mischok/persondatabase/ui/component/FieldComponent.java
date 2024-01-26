package academy.mischok.persondatabase.ui.component;

import academy.mischok.persondatabase.dto.PersonDto;
import academy.mischok.persondatabase.model.Person;
import academy.mischok.persondatabase.ui.column.UiColumn;
import academy.mischok.persondatabase.ui.view.DatabaseView;
import academy.mischok.persondatabase.util.Utility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class FieldComponent extends TextField implements IComponent {
    private final UiColumn column;
    private final DatabaseView databaseView;
    private Person person;
    private Pane parent;

    public FieldComponent(UiColumn column, final Person person, DatabaseView databaseView) {
        this.column = column;
        this.databaseView = databaseView;
        this.setPrefWidth(column.getSize());
        this.setPrefHeight(20);
        setPerson(person);
        setEditable(person == null);
        if (person == null && this.column != UiColumn.ID) {
            this.setText("");
            this.setPromptText(columnToText());

        }

        this.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode() == KeyCode.ENTER) {
                final PersonDto personDto = this.isNewEntry() ? this.databaseView.getPersonToAdd()
                        : this.databaseView.getPersonUi().getPersonDto((this.person)).orElse(null);
                if (personDto == null)
                    return;
                if (!personDto.canSubmit()) {
                    return;
                }
                if (this.person == null) {
                    this.databaseView.getPersonUi().getPersonService().addPerson(personDto);
                    this.databaseView.getPersonToAdd().reset();
                    databaseView.getInsertFields().forEach(fieldComponent -> {
                        fieldComponent.setText("");
                        fieldComponent.setPromptText(fieldComponent.columnToText());
                        this.setFocused(false);
                    });
                } else {
                    this.databaseView.getPersonUi().getPersonService().editPerson(this.person, personDto);
                }
                this.databaseView.refreshList();
                return;
            }
        });
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                final PersonDto personDto = isNewEntry() ? databaseView.getPersonToAdd()
                        : databaseView.getPersonUi().getPersonDto((person)).orElse(null);
                if (personDto == null)
                    return;
                switch (column) {
                    case FIRST_NAME -> {
                        personDto.setFirstName(newValue);
                    }
                    case LAST_NAME -> {
                        personDto.setLastName(newValue);
                    }
                    case EMAIL -> {
                        personDto.setEmail(newValue);
                    }
                    case COUNTRY -> {
                        personDto.setCountry(newValue);
                    }
                    case BIRTHDAY -> {
                        personDto.setBirthday(newValue);
                    }
                    case SALARY -> {
                        try {
                            Integer.parseInt(newValue);
                        } catch (NumberFormatException e) {
                            return;
                        }
                        personDto.setSalary(Integer.parseInt(newValue));
                    }
                    case BONUS -> {
                        try {
                            Integer.parseInt(newValue);
                        } catch (NumberFormatException e) {
                            return;
                        }
                        personDto.setBonus(Integer.parseInt(newValue));
                    }
                }
            }
        });
    }

    @Override
    public void display(Pane parent) {
        parent.getChildren().add(this);
        this.parent = parent;
    }

    @Override
    public void hide(Pane parent) {
        parent.getChildren().remove(this);
        this.parent = null;
    }

    public boolean isNewEntry() {
        return this.person == null;
    }

    @Override
    public Pane getCurrentParent() {
        return this.parent;
    }

    private String columnToText() {
        if (this.person == null) {
            return UiColumn.toColumn(this.column).getClearName();
        }
        switch (this.column) {
            case ID -> {
                return String.valueOf(this.person.getId());
            }
            case FIRST_NAME -> {
                return this.person.getFirstName();
            }
            case LAST_NAME -> {
                return this.person.getLastName();
            }
            case EMAIL -> {
                return this.person.getEmail();
            }
            case COUNTRY -> {
                return this.person.getCountry();
            }
            case BIRTHDAY -> {
                return Utility.convertDateToString(this.person.getBirthday());
            }
            case SALARY -> {
                return String.valueOf(this.person.getSalary());
            }
            case BONUS -> {
                return String.valueOf(this.person.getBonus());
            }
            default -> {
                return "";
            }
        }
    }

    public void setPerson(Person person) {
        this.person = person;
        setText(columnToText());
    }
}
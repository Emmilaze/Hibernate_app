package sample.utils;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import sample.models.Person;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class IntegerEditingCell extends TableCell<Person, Number> {

    private final TextField textField = new TextField();
    private final Pattern intPattern = Pattern.compile("-?\\d+");

    private Consumer<Pair<Person, Integer>> onChange;

    public IntegerEditingCell(Consumer<Pair<Person, Integer>> onChange) {
        this.onChange = onChange;

        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                processEdit();
            }
        });
        textField.setOnAction(event -> processEdit());
    }

    private void processEdit() {
        String text = textField.getText();
        if (intPattern.matcher(text).matches()) {
            commitEdit(Integer.parseInt(text));
        } else {
            cancelEdit();
        }
    }

    @Override
    public void updateItem(Number value, boolean empty) {
        super.updateItem(value, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            setText(null);
            textField.setText(value.toString());
            setGraphic(textField);
        } else {
            setText(value.toString());
            setGraphic(null);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        Number value = getItem();
        if (value != null) {
            textField.setText(value.toString());
            setGraphic(textField);
            setText(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().toString());
        setGraphic(null);
    }

    // This seems necessary to persist the edit on loss of focus; not sure why:
    @Override
    public void commitEdit(Number value) {
        super.commitEdit(value);
        Pair<Person, Integer> pair = new Pair<Person, Integer>((Person) this.getTableRow().getItem(), value.intValue());
        onChange.accept(pair);

//        (this.getTableRow().getItem()).setYear(value.intValue());
    }
}

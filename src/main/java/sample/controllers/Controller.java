package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import sample.utils.IntegerEditingCell;
import sample.models.Person;
import sample.utils.PersonStorage;
import sample.view.View;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField findNumber;
    @FXML
    private TextField findName;
    @FXML
    private TextField findYear;
    @FXML
    private TextField nameText;
    @FXML
    private TextField yearText;
    @FXML
    private TextField numberText;
    @FXML
    private TextField addressText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TableView phonebookTable;
    @FXML
    private TableColumn<Person, Integer> idColumn;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, Number> yearColumn;
    @FXML
    private TableColumn<Person, Number> numberColumn;
    @FXML
    private TableColumn<Person, String> addressColumn;

    private ObservableList<Person> oblist = FXCollections.observableArrayList();
    private View view = new View();
    private PersonStorage personStorage = new PersonStorage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        openDB();
    }

    public void openDB() {
        oblist.clear();
        oblist.addAll(personStorage.findAllUsers());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        phonebookTable.setItems(oblist);

        phonebookTable.setEditable(true);
        idColumn.setEditable(false);

        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        yearColumn.setCellFactory(col -> new IntegerEditingCell(
                pair -> pair.getKey().setYear(pair.getValue())
        ));
        numberColumn.setCellFactory(col -> new IntegerEditingCell(
                pair -> pair.getKey().setNumber(pair.getValue())
        ));

        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void columnEdit(TableColumn.CellEditEvent editedCell) {
        int row = editedCell.getTablePosition().getColumn();
        Person personSelected = (Person) phonebookTable.getSelectionModel().getSelectedItem();

        if (row == 2 || row == 3) {
            if (isNumeric(editedCell.getNewValue().toString())) {
                personStorage.updatePerson(row, personSelected, editedCell.getNewValue().toString());
            } else {
                view.print("You must print only numbers!", resultArea);
            }
        } else {
            personStorage.updatePerson(row, personSelected, editedCell.getNewValue().toString());
        }
        openDB();
    }

    public boolean isNumeric(String str) {
        try {
            int number = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            view.print("Введіть будь ласка ціле число", resultArea);
            return false;
        }
    }

    public void deleting(ActionEvent actionEvent) {
        int id = ((Person) phonebookTable.getSelectionModel().getSelectedItem()).getId();
        try {
            personStorage.deletePerson(id);
            openDB();
            view.print("Record number " + id + " was successfully deleted", resultArea);
        } catch (NullPointerException e) {
            view.print("Choose the record to delete!", resultArea);
        }
    }

    public void insert(ActionEvent actionEvent) {
        if (!(nameText.getText().isEmpty()) && !(yearText.getText().isEmpty()) && !(numberText.getText().isEmpty()) &&
                !(addressText.getText().isEmpty())) {
            try {
                personStorage.savePerson(nameText.getText(), Integer.parseInt(yearText.getText()),
                        Integer.parseInt(numberText.getText()), addressText.getText());
                openDB();
                view.print("Record was successfully created", resultArea);
            } catch (NumberFormatException e) {
                view.print("Please enter number", resultArea);
            }
        } else view.print("Please, fill all the fields", resultArea);
    }

    public void searching(ActionEvent actionEvent) {
        int index = 0;
        if (!(findName.getText().isEmpty())) index++;
        if (!(findNumber.getText().isEmpty())) index++;
        if (!(findYear.getText().isEmpty())) index++;
        if (index > 1) view.print("Fill only one field for search!", resultArea);
        if (index == 1 && !(findName.getText().isEmpty())) {
            oblist.clear();
            oblist.addAll(personStorage.getNameByNumber(findName.getText()));
            phonebookTable.setItems(oblist);
        }
        if (index == 1 && !(findNumber.getText().isEmpty())) {
            if (isNumeric(findNumber.getText())){
                oblist.clear();
                oblist.addAll(personStorage.getNumbersByName(findNumber.getText()));
                phonebookTable.setItems(oblist);
            }
        }
        if (index == 1 && !(findYear.getText().isEmpty())) {
            if (isNumeric(findYear.getText())) {
                oblist.clear();
                oblist.addAll(personStorage.getYear(findYear.getText()));
                phonebookTable.setItems(oblist);
            }
        }
        if (index == 0) view.print("Fill only one field for search!", resultArea);
    }

    public void clear(ActionEvent actionEvent) {
        openDB();
        resultArea.clear();
        nameText.clear();
        yearText.clear();
        numberText.clear();
        addressText.clear();
        findName.clear();
        findNumber.clear();
        findYear.clear();
    }
}
package sample.view;

import javafx.scene.control.TextArea;

public class View {
    public void print(String str, TextArea resultArea){
        resultArea.setText(str);
    }
}

package com.approgramming.coursework.invest;

import com.approgramming.coursework.attachment.Attachment;
import com.approgramming.coursework.authorization.Authorization;
import com.approgramming.coursework.data.Data;
import com.approgramming.coursework.offer.Offer;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class InvestBank {
    Stage investWindow;
    Label comment, bankName;
    TextField percentsInput, monthCountInput;
    TableView<Offer> table;
    public void display(Stage window) {
        investWindow = window;
        investWindow.setTitle("InvestBank");
        comment = new Label("");

        TableColumn<Offer, Double> percentsColumn = new TableColumn<>("Year Percents");
        percentsColumn.setMinWidth(100);
        percentsColumn.setCellValueFactory(new PropertyValueFactory<>("percents"));

        TableColumn<Offer, Integer> monthColumn = new TableColumn<>("Month Count");
        monthColumn.setMinWidth(100);
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("monthCount"));

        percentsInput = new TextField();
        percentsInput.setPromptText("Percents");

        monthCountInput = new TextField();
        monthCountInput.setPromptText("Month Count");

        bankName = new Label("Bank name : " + Authorization.customer.getName());

        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        addButton.setOnAction(e -> addButtonClicked());
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox editBox = new HBox();
        editBox.setPadding(new Insets(10,10,10,10));
        editBox.setSpacing(20);
        editBox.setAlignment(Pos.BOTTOM_CENTER);
        editBox.getChildren().addAll(percentsInput, monthCountInput, addButton, deleteButton, comment);

        table = new TableView<>();
        fillOffersTable(table);

        table.getColumns().addAll(percentsColumn, monthColumn);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(table, editBox);

        investWindow.setScene(new Scene(vbox));
        investWindow.show();
    }

    private void fillOffersTable(TableView<Offer> table) {
        Data data = new Data();
        table.setItems(data.getOffersByBankId(Authorization.customer.getCustomerId()));
        data.closeConnection();
    }

    private void addButtonClicked() {
        Offer offer = new Offer();
        Integer monthCount;
        Double percents;
        try{
            monthCount = Integer.parseInt(monthCountInput.getText());
            percents = Double.parseDouble(percentsInput.getText());
            offer.setMonthCount(monthCount);
            offer.setPercents(percents);
            offer.setBankName(Authorization.customer.getName());
            offer.setBankId(Authorization.customer.getCustomerId());
        } catch(NumberFormatException e) {
            comment.setText("Invalid values");
        }
        Data data = new Data();
        if(data.newOffer(offer)) {
            table.getItems().add(offer);
            fillOffersTable(table);
        } else {
            comment.setText("Adding new offer error");
        }
        data.closeConnection();
    }

    private void deleteButtonClicked() {
        ObservableList<Offer> selectedOff, allOff;
        allOff = table.getItems();
        selectedOff = table.getSelectionModel().getSelectedItems();
        Offer selectedOffObj = selectedOff.get(0);
        Data data = new Data();
        if(data.deleteOffer(selectedOffObj)) {
            selectedOff.forEach(allOff::remove);
        }
        else
            comment.setText("Offer deleting error");
        data.closeConnection();
        fillOffersTable(table);
    }
}

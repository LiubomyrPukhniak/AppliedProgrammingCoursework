package com.approgramming.coursework.invest;

import com.approgramming.coursework.attachment.Attachment;
import com.approgramming.coursework.authorization.Authorization;
import com.approgramming.coursework.data.Data;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Invest {
    Stage investWindow;
    TableView<Attachment> table;
    TextField moneyInput;
    ComboBox<String> offerInput;
    CheckBox outOfScheduleInput;
    Label comment, balance;
    public void display(Stage window, ArrayList<Attachment> readyAttachments) {
        investWindow = window;
        investWindow.setTitle("Invest");
        comment = new Label("");
        comment.setId("error");
        if(!readyAttachments.isEmpty()) {
            Data data = new Data();
            if(data.completeAttachments(readyAttachments))
                data.closeConnection();
        }

        TableColumn<Attachment, Double> moneyColumn = new TableColumn<>("Money Amount");
        moneyColumn.setMinWidth(200);
        moneyColumn.setCellValueFactory(new PropertyValueFactory<>("money"));

        TableColumn<Attachment, Double> percentsColumn = new TableColumn<>("Year Percents");
        percentsColumn.setMinWidth(150);
        percentsColumn.setCellValueFactory(new PropertyValueFactory<>("percents"));

        TableColumn<Attachment, Integer> monthColumn = new TableColumn<>("Month Count");
        monthColumn.setMinWidth(150);
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("monthCount"));

        TableColumn<Attachment, String> dateOfEndColumn = new TableColumn<>("End Date");
        dateOfEndColumn.setMinWidth(100);
        dateOfEndColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfEnd"));

        TableColumn<Attachment, Boolean> isOutOfScheduleColumn = new TableColumn<>("Out of Schedule");
        isOutOfScheduleColumn.setMinWidth(200);
        isOutOfScheduleColumn.setCellValueFactory(new PropertyValueFactory<>("outOfSchedule"));

        TableColumn<Attachment, String> bankNameColumn = new TableColumn<>("Bank Name");
        bankNameColumn.setMinWidth(100);
        bankNameColumn.setCellValueFactory(new PropertyValueFactory<>("bankName"));

        offerInput = new ComboBox<>();
        offerInput.setPromptText("Offer");
        fillOffersComboBox(offerInput);

        moneyInput = new TextField();
        moneyInput.setPromptText("Money");

        outOfScheduleInput = new CheckBox("Is out of schedule");
        balance = new Label("Your balance : " + Authorization.customer.getMoneyCount());

        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button replenishButton = new Button("Replenish");
        addButton.setOnAction(e -> addButtonClicked());
        deleteButton.setOnAction(e -> deleteButtonClicked());
        replenishButton.setOnAction(e -> replenishButtonClicked());

        HBox editBox = new HBox();
        editBox.setPadding(new Insets(10,10,10,10));
        editBox.setSpacing(20);
        editBox.setAlignment(Pos.BOTTOM_CENTER);
        editBox.getChildren().addAll(offerInput, moneyInput, outOfScheduleInput,
                addButton, deleteButton, replenishButton, balance, comment);

        table = new TableView<>();
        fillAttachmentsTable(table);

        table.getColumns().addAll(moneyColumn, percentsColumn, monthColumn,
                dateOfEndColumn, isOutOfScheduleColumn, bankNameColumn);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(table, editBox);
        Scene scene = new Scene(vbox, 1300, 450);
        scene.getStylesheets().add("MainStyle.css");
        window.setScene(scene);
        window.setMinHeight(450);
        window.setMaxHeight(450);
        window.setMinWidth(1300);
        window.show();
    }

    private void fillOffersComboBox(ComboBox<String> comboBox) {
        Data data = new Data();
        comboBox.setItems(data.getOffers(0));
        data.closeConnection();
    }

    private void fillAttachmentsTable(TableView<Attachment> table) {
        Data data = new Data();
        table.setItems(data.getAttachments(Authorization.customer));
        data.closeConnection();
    }

    private void addButtonClicked() {
        Attachment att = new Attachment();
       try{
           if(moneyInput.getText().isBlank())
               throw new Exception("Money field is empty");
           Data data = new Data();
           att.setOffer(data.getOfferById(getOfferIdFromStr(offerInput.getValue())));
           data.closeConnection();
           att.setMoney(Double.parseDouble(moneyInput.getText()));
           if(att.getMoney() <= 0)
               throw new Exception("Invalid values");
           att.setFirstMoneyAmount(att.getMoney());
           if(Authorization.customer.getMoneyCount() < att.getMoney()) {
               throw new Exception("you don\'t have that much money");
           }
           att.setOutOfSchedule(outOfScheduleInput.isSelected());
           DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           Calendar timeNow = Calendar.getInstance();
           att.setDateOfStart(dateFormat.format(timeNow.getTime()));
           timeNow.add(Calendar.MONTH, att.getMonthCount());
           att.setDateOfEnd(dateFormat.format(timeNow.getTime()));
       }catch(Exception e) {
           e.printStackTrace();
           comment.setText(e.getMessage().isBlank() ? "Invalid values" : e.getMessage());
       }
       Data data = new Data();
        if(data.newAttachment(att)) {
            table.getItems().add(att);
            if(data.updateMoneyCount(Authorization.customer.getCustomerId(), -(att.getMoney())))
                Authorization.customer.setMoneyCount(Authorization.customer.getMoneyCount() - att.getMoney());
            fillAttachmentsTable(table);
            balance.setText("Your balance : " + Authorization.customer.getMoneyCount());
        }
        else
            comment.setText("Attachment adding error");
        data.closeConnection();
        moneyInput.clear();
        outOfScheduleInput.setSelected(false);
    }

    private void deleteButtonClicked() {
        ObservableList<Attachment> selectedAtt, allAtt;
        allAtt = table.getItems();
        selectedAtt = table.getSelectionModel().getSelectedItems();
        Attachment selectedAttObj = selectedAtt.get(0);
        if(selectedAttObj.getOutOfSchedule()) {
            Data data = new Data();
            if(data.updateMoneyCount(Authorization.customer.getCustomerId(), selectedAttObj.getMoney()))
                Authorization.customer.setMoneyCount(Authorization.customer.getMoneyCount() + selectedAtt.get(0).getMoney());
            if(data.deleteAttachment(selectedAttObj)) {
                selectedAtt.forEach(allAtt::remove);
            }
            else
                comment.setText("Attachment deleting error");
            data.closeConnection();
            fillAttachmentsTable(table);
            balance.setText("Your balance : " + Authorization.customer.getMoneyCount());
        }
        else{
            comment.setText("This attachment can't be deleted");
        }
    }

    private Integer getOfferIdFromStr(String str) {
        char[] charArr = str.substring(5).toCharArray();
        int i = 0;
        for(; charArr[i] != ','; i++);
        return Integer.parseInt(str.substring(5, i+5));
    }

    private void replenishButtonClicked() {
        ObservableList<Attachment> selectedAtt;
        selectedAtt = table.getSelectionModel().getSelectedItems();
        Attachment selectedAttObj = selectedAtt.get(0);
        Data data = new Data();
        Double replCount;
        try {
            replCount = Double.parseDouble(moneyInput.getText());
            if(data.updateAttMoneyCount(selectedAttObj, replCount))
                selectedAttObj.setMoney(selectedAttObj.getMoney() + replCount);
            if(data.updateMoneyCount(Authorization.customer.getCustomerId(), -(replCount)))
                Authorization.customer.setMoneyCount(Authorization.customer.getMoneyCount() - replCount);
            fillAttachmentsTable(table);
            balance.setText("Your balance : " + Authorization.customer.getMoneyCount());
        } catch (NumberFormatException e) {
            comment.setText("Invalid money field");
        }
        data.closeConnection();
    }
}

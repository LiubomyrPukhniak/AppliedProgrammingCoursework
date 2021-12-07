package com.approgramming.coursework.authorization;

import com.approgramming.coursework.attachment.Attachment;
import com.approgramming.coursework.customer.Customer;
import com.approgramming.coursework.data.Data;
import com.approgramming.coursework.invest.Invest;
import com.approgramming.coursework.validation.Validation;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SignUp {
    Stage singUpWindow;
    public void display(Stage window) {
        singUpWindow = window;
        singUpWindow.setTitle("Registration");
        Label comment = new Label("");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setVgap(30);
        grid.setHgap(10);

        Label AuthText = new Label("Registration");
        GridPane.setConstraints(AuthText, 1, 0);

        Label nameLabel = new Label("Name : ");
        GridPane.setConstraints(nameLabel, 0, 1);

        TextField name = new TextField();
        name.setPromptText("Name");
        GridPane.setConstraints(name, 1, 1);

        Label surnameLabel = new Label("Surname : ");
        GridPane.setConstraints(surnameLabel, 0, 2);

        TextField surname = new TextField();
        surname.setPromptText("Surname");
        GridPane.setConstraints(surname, 1, 2);

        Label emailLabel = new Label("Email : ");
        GridPane.setConstraints(emailLabel, 0, 3);

        TextField email = new TextField();
        email.setPromptText("Email");
        GridPane.setConstraints(email, 1, 3);

        Label passwordLabel = new Label("Password : ");
        GridPane.setConstraints(passwordLabel, 0, 4);

        TextField password = new TextField();
        password.setPromptText("Password");
        GridPane.setConstraints(password, 1, 4);

        Label moneyLabel = new Label("Money : ");
        GridPane.setConstraints(moneyLabel, 0, 5);

        TextField money = new TextField();
        money.setPromptText("Password");
        GridPane.setConstraints(money, 1, 5);

        Button loginButton = new Button("Sign In");
        loginButton.setOnAction(e -> {
            new Authorization().display(window);
        });


        Button registerButton = new Button("Sign Up");
        registerButton.setOnAction(e -> {
            if(register(email.getText().strip(), password.getText().strip(),
                    name.getText().strip(), surname.getText().strip(),
                    money.getText().strip(), comment)) {
                new Invest().display(window, new ArrayList<Attachment>());
            }
        });

        HBox AuthButtons = new HBox();
        AuthButtons.setSpacing(20);
        AuthButtons.getChildren().addAll(loginButton, registerButton);
        GridPane.setConstraints(AuthButtons, 1, 6);

        GridPane.setConstraints(comment, 1, 7);

        grid.getChildren().addAll(AuthText, emailLabel, email, passwordLabel,
                password, nameLabel, name, surnameLabel, surname, moneyLabel,
                money, AuthButtons, comment);
        Scene scene = new Scene(grid, 400, 500);
        singUpWindow.setScene(scene);
        singUpWindow.show();
    }

    private boolean register(String email, String password, String name, String surname, String moneyCount, Label comment) {
        double money;
        try{
            money = Double.parseDouble(moneyCount);
        }catch (Exception e) {
            e.printStackTrace();
            comment.setText("Invalid Money Field");
            return false;
        }
        if(!Validation.email(email)) {
            comment.setText("Invalid Email Field");
            return false;
        }
        if(!Validation.password(password)) {
            comment.setText("Invalid Password Field");
            return false;
        }
        if(!Validation.name(name)) {
            comment.setText("Invalid Name Field");
            return false;
        }
        if(!Validation.surname(surname)) {
            comment.setText("Invalid Surname Field");
            return false;
        }
        Data data = new Data();
        Authorization.customer = data.newCustomer(email, password, name, surname, money);
        data.closeConnection();
        return Authorization.customer != null;
    }
}

package com.approgramming.coursework.authorization;

import com.approgramming.coursework.attachment.Attachment;
import com.approgramming.coursework.data.Data;
import com.approgramming.coursework.customer.Customer;
import com.approgramming.coursework.invest.Invest;
import com.approgramming.coursework.invest.InvestBank;
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

public class Authorization {
    Stage authWindow;
    public static Customer customer;

    public void display(Stage window) {
        authWindow = window;
        authWindow.setTitle("Authorization");
        Label comment = new Label("");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setVgap(30);
        grid.setHgap(10);

        Label AuthText = new Label("Authorization");
        GridPane.setConstraints(AuthText, 1, 0);

        Label emailLabel = new Label("Email : ");
        GridPane.setConstraints(emailLabel, 0, 1);

        TextField email = new TextField();
        email.setPromptText("Email");
        GridPane.setConstraints(email, 1, 1);

        Label passwordLabel = new Label("Password : ");
        GridPane.setConstraints(passwordLabel, 0, 2);

        TextField password = new TextField();
        password.setPromptText("Password");
        GridPane.setConstraints(password, 1, 2);

        Button loginButton = new Button("Sign In");
        loginButton.setOnAction(e -> {
            if(login(email.getText().strip(), password.getText().strip())){
                new Invest().display(window, checkReadyAttachments());
            } else if(loginBank(email.getText().strip(), password.getText().strip())) {
                new InvestBank().display(window);
            } else {
                comment.setText("Invalid Email or password");
            }
        });


        Button registerButton = new Button("Sign Up");
        registerButton.setOnAction(e -> {
            new SignUp().display(window);
        });

        HBox AuthButtons = new HBox();
        AuthButtons.setSpacing(20);
        AuthButtons.getChildren().addAll(loginButton, registerButton);
        GridPane.setConstraints(AuthButtons, 1, 3);


        GridPane.setConstraints(comment, 1, 4);

        grid.getChildren().addAll(AuthText, emailLabel, email, passwordLabel, password, AuthButtons, comment);
        Scene scene = new Scene(grid, 400, 500);
        authWindow.setScene(scene);
        authWindow.show();
    }

    private boolean login(String email, String password) {
        if(Validation.email(email) && Validation.password(password)) {
            Data data = new Data();
            Authorization.customer = data.authSearch(email, password);
            data.closeConnection();
            return Authorization.customer != null;
        } else
            return false;
    }

    private boolean loginBank(String email, String password) {
        if(Validation.email(email) && Validation.password(password)) {
            Data data = new Data();
            Authorization.customer = data.authBankSearch(email, password);
            data.closeConnection();
            return Authorization.customer != null;
        } else
            return false;
    }

    private ArrayList<Attachment> checkReadyAttachments() {
        Data data = new Data();
        ArrayList<Attachment> readyAtt = data.checkReadyAttachments();
        data.closeConnection();
        return readyAtt;
    }
}

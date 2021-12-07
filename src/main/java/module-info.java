module com.approgramming.coursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.approgramming.coursework to javafx.fxml;
    exports com.approgramming.coursework;
    exports com.approgramming.coursework.authorization;
    opens com.approgramming.coursework.authorization to javafx.fxml;
    exports com.approgramming.coursework.data;
    opens com.approgramming.coursework.data to javafx.fxml;
    opens com.approgramming.coursework.attachment to javafx.base;
    opens com.approgramming.coursework.offer to javafx.base;
}
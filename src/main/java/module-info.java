module com.trivia.triviamat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires javafx.media;
    requires jfxmessagebox;


    opens com.trivia.triviamat to javafx.fxml;
    exports com.trivia.triviamat;
}
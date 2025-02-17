module dev.typeracist.typeracist {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.typeracist.typeracist to javafx.fxml;
    exports dev.typeracist.typeracist.application;
    opens dev.typeracist.typeracist.application to javafx.fxml;
}
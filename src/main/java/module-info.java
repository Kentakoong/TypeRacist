module dev.typeracist.typeracist {
    requires javafx.controls;
    requires org.json;
    requires javafx.fxml;

    exports dev.typeracist.typeracist.application;

    opens dev.typeracist.typeracist to javafx.fxml;
    exports dev.typeracist.typeracist;
}
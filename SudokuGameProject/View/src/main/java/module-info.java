module View {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires ModelProject;
    requires log4j;

    opens view;
    opens view.listbundle;
}
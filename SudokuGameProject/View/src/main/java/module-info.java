module View {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires ModelProject;

    opens view;
    opens view.listbundle;
}
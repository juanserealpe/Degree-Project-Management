module app.main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.prefs;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires jbcrypt;
    requires java.desktop;
    requires app.main;
    //requires app.main;

    opens Main to javafx.fxml;
    opens Controllers to javafx.fxml;

    opens img;
    opens css;

    exports Main;
    exports Controllers;
}

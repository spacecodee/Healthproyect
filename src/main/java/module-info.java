module com.spacecodee.healthproyect {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.dbcp2;
    requires java.sql;
    requires java.management;
    requires lombok;

    opens com.spacecodee.healthproyect to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.dashboard;
    opens com.spacecodee.healthproyect.controllers.components;
    exports com.spacecodee.healthproyect;
    exports com.spacecodee.healthproyect.controllers.dashboard;
}
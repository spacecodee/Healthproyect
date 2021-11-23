module com.spacecodee.healthproyect {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.dbcp2;
    requires java.sql;
    requires java.management;
    requires lombok;

    opens com.spacecodee.healthproyect to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.dashboard to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.top_bar to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.user_roles to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.modals to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.address to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.countries to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.cities to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.districs to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.users to javafx.fxml;
    opens com.spacecodee.healthproyect.controllers.add_modal to javafx.fxml;
    opens com.spacecodee.healthproyect.model.users_roles to javafx.base;
    opens com.spacecodee.healthproyect.model.countries to javafx.base;
    opens com.spacecodee.healthproyect.dto.country to javafx.base;
    opens com.spacecodee.healthproyect.dto.address to javafx.base;
    opens com.spacecodee.healthproyect.dto.city to javafx.base;
    opens com.spacecodee.healthproyect.dto.district to javafx.base;
    opens com.spacecodee.healthproyect.dto.user to javafx.base;
    exports com.spacecodee.healthproyect to javafx.graphics;
    exports com.spacecodee.healthproyect.controllers.dashboard;
    exports com.spacecodee.healthproyect.controllers.user_roles;
    exports com.spacecodee.healthproyect.controllers.modals to javafx.fxml;
    exports com.spacecodee.healthproyect.controllers.address to javafx.fxml;
    exports com.spacecodee.healthproyect.controllers.countries to javafx.fxml;
    exports com.spacecodee.healthproyect.controllers.cities to javafx.fxml;
    exports com.spacecodee.healthproyect.controllers.districs to javafx.fxml;
}
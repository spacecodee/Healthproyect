package com.spacecodee.healthproyect.controllers.login;

import com.spacecodee.healthproyect.dao.users.IUserDao;
import com.spacecodee.healthproyect.dao.users.UserDaoImpl;
import com.spacecodee.healthproyect.dto.users.UserDto;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private ImageView imgDoctors;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Images.addImg("img/Doctors-rafiki.png", this.imgDoctors);
    }

    @FXML
    private void loginOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnLogin) || event.getSource().equals(this.txtUserName)
                || event.getSource().equals(this.txtPassword)) {
            var username = this.txtUserName.getText().trim();
            var password = this.txtPassword.getText().trim();
            if (!username.isEmpty() || !password.isEmpty()) {
                var userDto = new UserDto(username);
                final IUserDao userDao = new UserDaoImpl();
                var userLogin = userDao.login(userDto);

                if (userLogin != null) {
                    if (userLogin.getUserName().equalsIgnoreCase(username) && userLogin.getPassword().equalsIgnoreCase(password)) {
                        this.loadDashboard(userLogin);
                        AppUtils.closeModal(event);
                    } else {
                        AppUtils.loadModalMessage("Usuario y/o contraseña incorrectos", "error");
                    }
                } else {
                    AppUtils.loadModalMessage("El usuario no existe", "error");
                }
            } else {
                AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
            }
        }
    }

    private void loadDashboard(UserDto userDto) {
        var stage = new Stage();
        var dashboard = AppUtils.appDashboard(stage);
        dashboard.setUserDto(userDto);
        dashboard.initDashboardLogin();
        stage.show();
    }
}

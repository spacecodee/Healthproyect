package com.spacecodee.healthproyect.controllers.settings;

import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.peoples.IPeopleDao;
import com.spacecodee.healthproyect.dao.peoples.PeopleDaoImpl;
import com.spacecodee.healthproyect.dao.users.IUserDao;
import com.spacecodee.healthproyect.dao.users.UserDaoImpl;
import com.spacecodee.healthproyect.dto.address.AddressDto;
import com.spacecodee.healthproyect.dto.districts.DistrictDto;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;
import com.spacecodee.healthproyect.dto.users.UserDto;
import com.spacecodee.healthproyect.dto.users_roles.UserRolesDto;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class Settings implements Initializable {

    @FXML
    private Button btnChangePassword;

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker dtBirthDate;

    @FXML
    private ImageView iconSave;

    @FXML
    private ImageView iconSave1;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUserName;

    @Getter
    @Setter
    private UserDto userDto;

    final private IUserDao userDao = new UserDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Images.addImg("icons/save.png", this.iconSave);
        Images.addImg("icons/save.png", this.iconSave1);
    }

    @FXML
    void onSaveOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnSave)) {
            this.changeProfile(event);
        }
        else if (event.getSource().equals(this.btnChangePassword)) {
            this.changedPassword();
        }
    }

    private void changeProfile(ActionEvent event) {
        final IAddressDao addressDao = new AddressDaoImpl();
        final IPeopleDao peopleDao = new PeopleDaoImpl();

        var idPeople = this.userDto.getPeople().getIdPeople();
        var idUser = this.userDto.getIdUser();
        var idAddress = this.userDto.getPeople().getAddressDto().getIdAddress();
        var idDistrict = this.userDto.getPeople().getAddressDto().getDistrictDto().getIdDistrict();
        var idRol = this.userDto.getUserRolesDto().getIdRolUser();
        var dni = this.txtDni.getText().trim();
        var name = this.txtName.getText().trim();
        var lastName = this.txtLastName.getText().trim();
        var email = this.txtEmail.getText().trim();
        var phone = this.txtPhone.getText().trim();
        var userName = this.txtUserName.getText().trim();
        var password = this.userDto.getPassword();
        var address = this.txtAddress.getText().trim();

        if (!AppUtils.validateText(this.txtDni, this.txtName, this.txtLastName, this.txtEmail, this.txtPhone,
                                   this.txtUserName, this.txtAddress)) {
            var addressDto = new AddressDto(idAddress, address, new DistrictDto(idDistrict));

            if (addressDao.update(addressDto)) {
                var peopleDto = new PeopleDto(idPeople, dni, name, lastName, email, phone, addressDto);

                if (peopleDao.update(peopleDto)) {
                    var user = new UserDto(idUser, userName, password, peopleDto, new UserRolesDto(idRol));

                    if (this.userDao.validateRepeatUsername(userName) <= 1) {
                        if (userDao.update(user)) {
                            if (userName.equalsIgnoreCase(this.userDto.getUserName())) {
                                this.sendData();
                                AppUtils.loadModalMessage("Datos actualizados", "success");
                            }
                            else {
                                var modal = AppUtils.loadMessageCloseSession("Serás redirigido al login", "success");
                                AppUtils.closeModal(event);
                                modal.getBtnOk().setOnAction(actionEvent -> {
                                    AppUtils.closeModal(actionEvent);
                                    AppUtils.loadLogin(this.userDto);
                                });
                            }
                        }
                        else {
                            AppUtils.loadModalMessage("Algunos datos fueron actualizados", "error");
                        }
                    }
                    else {
                        AppUtils.loadModalMessage("Use nombre de usuario ya existe, intenta con otro", "error");
                    }

                }
                else {
                    AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
                }
            }
            else {
                AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
            }
        }
        else {
            AppUtils.loadModalMessage("Los datos son necesarios", "error");
        }
    }

    private void changedPassword() {
        var password = this.txtPassword.getText().trim();
        var newPassword = this.txtNewPassword.getText().trim();

        if (newPassword.length() >= 6) {
            if (!this.userDto.getPassword().equalsIgnoreCase(newPassword)) {
                if (!password.equalsIgnoreCase(newPassword)) {
                    var user = new UserDto(this.userDto.getIdUser(), newPassword);

                    if (this.userDao.changedPassword(user)) {
                        AppUtils.loadModalMessage("Contraseñas actualizadas", "success");
                    }
                    else {
                        AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
                    }
                }
                else {
                    AppUtils.loadModalMessage("Las contraseñas son identicas", "error");
                }
            }
            else {
                AppUtils.loadModalMessage("Las contraseñas son identicas a tu contraseña actual", "error");
            }
        }
        else {
            AppUtils.loadModalMessage("La nueva contraseña debe tener al menos 6 carácteres", "error");
        }
    }

    public void sendData() {
        if (this.userDto != null) {
            this.setUserDto(this.userDao.profile(this.userDto));
            if (this.userDto != null) {
                this.txtDni.setText(this.userDto.getPeople().getDni());
                this.txtName.setText(this.userDto.getPeople().getName());
                this.txtLastName.setText(this.userDto.getPeople().getLastname());
                this.txtEmail.setText(this.userDto.getPeople().getMail());
                this.txtPhone.setText(this.userDto.getPeople().getPhone());
                this.txtUserName.setText(this.userDto.getUserName());
                this.txtAddress.setText(this.userDto.getPeople().getAddressDto().getAddressName());
                this.dtBirthDate.setValue(AppUtils.LOCAL_DATE(this.userDto.getPeople().getBirthDate()));
            }
            else {
                AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
            }
        }
        else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }
    }
}

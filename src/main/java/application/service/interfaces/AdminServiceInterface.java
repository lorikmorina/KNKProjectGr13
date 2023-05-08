package application.service.interfaces;

import application.models.Admin;
import application.models.User;

import java.sql.SQLException;

public interface AdminServiceInterface {
    Admin login(String username, String password) throws SQLException;
}

package application.service.interfaces;

import application.models.User;

import java.sql.SQLException;

public interface UserServiceInterface {
    User login(String username, String password) throws SQLException;
}

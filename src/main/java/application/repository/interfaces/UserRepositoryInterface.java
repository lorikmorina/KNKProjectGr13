package application.repository.interfaces;

import application.models.User;
import application.models.dto.CreateUserDto;

import java.sql.SQLException;

public interface UserRepositoryInterface {
    public User insert(CreateUserDto user) throws SQLException;
}
package application.repository.interfaces;

import application.models.Admin;
import application.models.User;
import application.models.dto.CreateAdminDto;
import application.models.dto.CreateUserDto;

import java.sql.SQLException;

public interface AdminRepositoryInterface {
    public Admin insert(CreateAdminDto user) throws SQLException;
}
package application.repository.interfaces;

import application.models.Child;
import application.models.User;
import application.models.dto.CreateChildDto;
import application.models.dto.CreateUserDto;

import java.sql.SQLException;

public interface ChildRepositoryInterface {
    public Child insert(CreateChildDto user) throws SQLException;
}
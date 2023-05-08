package application.repository.interfaces;

import application.models.Teacher;
import application.models.User;
import application.models.dto.CreateTeacherDto;
import application.models.dto.CreateUserDto;

import java.sql.SQLException;

public interface TeacherRepositoryInterface {
    public Teacher insert(CreateTeacherDto user) throws SQLException;
}
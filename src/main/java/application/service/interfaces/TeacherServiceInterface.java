package application.service.interfaces;

import application.models.Admin;
import application.models.Teacher;

import java.sql.SQLException;

public interface TeacherServiceInterface {
    Teacher login(String username, String password) throws SQLException;
}

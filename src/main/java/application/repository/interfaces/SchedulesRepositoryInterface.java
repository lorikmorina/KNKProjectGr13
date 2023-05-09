package application.repository.interfaces;

import application.models.Schedule;
import application.models.Teacher;
import application.models.dto.CreateScheduleDto;
import application.models.dto.CreateTeacherDto;

import java.sql.SQLException;

public interface SchedulesRepositoryInterface {
    public Schedule insert(CreateScheduleDto user) throws SQLException;
}
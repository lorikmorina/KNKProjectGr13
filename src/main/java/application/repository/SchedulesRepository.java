package application.repository;


import application.database.ConnectionUtil;
import application.models.Schedule;
import application.models.Teacher;
import application.models.dto.CreateScheduleDto;
import application.models.dto.CreateTeacherDto;
import application.repository.interfaces.SchedulesRepositoryInterface;
import application.repository.interfaces.TeacherRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchedulesRepository implements SchedulesRepositoryInterface {

    public Schedule insert(CreateScheduleDto user) throws SQLException {
        String sql = "INSERT INTO schedules (day, startTime, endTime, teacher, classroomNr) VALUES (?,?,?, ?, ?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getDay());
        statement.setString(2, user.getStartTime());
        statement.setString(3, user.getEndTime());
        statement.setInt(4, user.getTeacher());
        statement.setInt(5, user.getClassroomNr());
        statement.executeUpdate();

        return SchedulesRepository.getByDay(user.getDay());
    }

//    public static User get(String columnName, Object value) throws SQLException{
//        String sql = "SELECT * FROM users WHERE ?=?";
//        Connection connection = ConnectionUtil.getConnection();
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setObject(1, columnName);
//        statement.setObject(2, value);
//        ResultSet res = statement.executeQuery();
////        ...
//        return null;
//    }

    public static Schedule getByDay(String day) throws SQLException {
        String sql = "SELECT * FROM schedules WHERE day=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, day);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String startTime = resultSet.getString("startTime");
                String endTime = resultSet.getString("endTime");
                int teacher = resultSet.getInt("teacher");
                int classroomNr = resultSet.getInt("classroomNr");
                return new Schedule(id,day, startTime,endTime , teacher, classroomNr);
            } else {
                return null;
            }
        }
    }
    public static Schedule getByID(int ID) throws SQLException {
        String sql = "SELECT * FROM schedules WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                String day = resultSet.getString("day");
                String startTime = resultSet.getString("startTime");
                String endTime = resultSet.getString("endTime");
                int teacher = resultSet.getInt("teacher");
                int classroomNr = resultSet.getInt("classroomNr");
                return new Schedule(ID,day, startTime,endTime , teacher, classroomNr);
            } else {
                return null;
            }
        }
    }
//    public static List<User> getByFilter(UserFilter filters, Pagination pagination) throws SQLException{
//        List<User> users = new ArrayList<User>();
//        String sql = "SELECT * FROM users WHERE 1=1";
//        ArrayList<Object> params = new ArrayList<Object>();
//
//        sql += filters.getFilterQuery();
//        params.addAll(filters.getFilterParams());
//        sql += pagination.getSQLQuery();
//        params.addAll(pagination.getSQLParams());
//        /// other filter options
//
//        Connection conn = ConnectionUtil.getConnection();
//        PreparedStatement preparedStatement = conn.prepareStatement(sql);
//        for(int i = 0; i < params.size(); i++){
//            preparedStatement.setObject(i+1, params.get(i));
//        }
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        while(resultSet.next()){
//            users.add(
//                    new User(
//                            resultSet.getInt(1),
//                            resultSet.getString(2),
//                            resultSet.getString(3),
//                            resultSet.getString(4)
//                    )
//            );
//        }
//
//        return users;
//    }

//    public static User update(UpdateUserDto user) throws SQLException{
//        String sql = "UPDATE user SET salted_password = ? WHERE id = ?";
//        Connection conn = ConnectionUtil.getConnection();
//        PreparedStatement preparedStatement = conn.prepareStatement(sql);
//        preparedStatement.setObject(1, user.getSaltedPassword());
//        preparedStatement.setObject(2, user.getId());
//        preparedStatement.executeUpdate();
//
//        return UserRepository.get("id", user.getId());
//    }


}


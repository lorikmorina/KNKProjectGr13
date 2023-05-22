package application.repository;


import application.database.ConnectionUtil;
import application.models.Teacher;
import application.models.User;
import application.models.dto.CreateTeacherDto;
import application.models.dto.CreateUserDto;
import application.repository.interfaces.TeacherRepositoryInterface;
import application.repository.interfaces.UserRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherRepository implements TeacherRepositoryInterface {

    public Teacher insert(CreateTeacherDto user) throws SQLException {
        String sql = "INSERT INTO teachers (fullName, email, personalNr, salted_hash, salt, securityQuestion) VALUES (?,?,?,?, ?, ?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFullName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPersonalNr());
        statement.setString(4, user.getSaltedPassword());
        statement.setString(5, user.getSalt());
        statement.setString(6,user.getSecQuestion());
        statement.executeUpdate();

        return TeacherRepository.getByEmail(user.getEmail());
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

    public static Teacher getByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE email=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("fullName");
                String personalNr = resultSet.getString("personalNr");
                String saltedHash = resultSet.getString("salted_hash");
                String salt = resultSet.getString("salt");
                String secQuesion = resultSet.getString("securityQuestion");
                return new Teacher(id,fullName, email,personalNr , saltedHash, salt,secQuesion);
            } else {
                return null;
            }
        }
    }
    public static Teacher getByID(int ID) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("fullName");
                String email = resultSet.getString("email");
                String personalNr = resultSet.getString("personalNr");
                String saltedHash = resultSet.getString("salted_hash");
                String salt = resultSet.getString("salt");
                String secQuesion = resultSet.getString("securityQuestion");
                return new Teacher(id,fullName, email,personalNr , saltedHash, salt,secQuesion);
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


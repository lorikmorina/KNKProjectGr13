package application.repository;


import application.database.ConnectionUtil;
import application.models.Child;
import application.models.User;
import application.models.dto.CreateChildDto;
import application.repository.interfaces.ChildRepositoryInterface;
import application.repository.interfaces.UserRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateChildRepository implements ChildRepositoryInterface {

    public  Child insert(CreateChildDto child) throws SQLException {
        String sql = "INSERT INTO children (childsName, parent_id, age, teacher, classroomNr,contactInfo,medicalInfo) VALUES (?,?,?, ?, ?,?,?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, child.getChildsName());
        statement.setInt(2, child.getParent_id());
        statement.setInt(3, child.getChild_age());
        statement.setString(4, child.getTeacher());
        statement.setInt(5, child.getClassNr());
        statement.setString(6, child.getContactInfo());
        statement.setString(7, child.getMedicalInfo());

        statement.executeUpdate();

        return CreateChildRepository.getById(child.getChild_id());
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

    public static Child getById(int ID) throws SQLException {
        String sql = "SELECT * FROM children WHERE child_id=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int child_id = resultSet.getInt("child_id");
                String childsName = resultSet.getString("childsName");
                int parent_id = resultSet.getInt("parent_id");
                int age = resultSet.getInt("age");
                String teacher = resultSet.getString("teacher");
                int classroomNr = resultSet.getInt("classroomNr");
                String contactInfo = resultSet.getString("contactInfo");
                String medicalInfo = resultSet.getString("medicalInfo");


                return new Child(child_id,childsName,parent_id , age, teacher,classroomNr,contactInfo,medicalInfo);
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


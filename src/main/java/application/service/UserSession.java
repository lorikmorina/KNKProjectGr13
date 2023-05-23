package application.service;

import application.database.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSession {
    private static int id;
    private String fullName;
    private String email;
    private String personalNr;
    private static int accessLevel;

    private int nrChildren;

    public UserSession(int id, String fullName, String email, String personalNr, int accessLvl) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.personalNr = personalNr;
        this.accessLevel = accessLvl;
    }


    public static int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonalNr() {
        return personalNr;
    }

    public static int getAccessLevel() {
        return accessLevel;
    }

    public void cleanUserSession() {
        id = 0;// or null
        fullName = "";
        email = "";
        personalNr = "";
        accessLevel = 0;
    }


    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + fullName + '\'' +
                "employeeId = '" + accessLevel + '\'' +
                '}';
    }

    public int getNrChildren(int parent_id) throws SQLException {
        Connection connection = null;
        int nrChildren = 0;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM children WHERE parent_id = ?"
            );
            stmt.setInt(1, parent_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nrChildren = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return nrChildren;
    }

}

package application.service;
public class UserSession {


    private String userName;
    private int userId;
    private int accessLevel;

    public UserSession(String userName, int userId, int accessLvl) {
        this.userName = userName;
        this.userId = userId;
        this.accessLevel = accessLvl;
    }




    public String getUserName() {
        return userName;
    }
    public int getUserId() {
        return userId;
    }
    public int getAccessLevel() {
        return accessLevel;
    }



    public void cleanUserSession() {
        userName = "";// or null
        userId = 0;
        accessLevel = 0;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + userName + '\'' +
                "employeeId = '" + accessLevel + '\'' +
                '}';
    }
}

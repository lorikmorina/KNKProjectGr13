package application.service;
public class UserSession {
    private int id;
    private String fullName;
    private String email;
    private String personalNr;
    private int accessLevel;

    public UserSession(int id, String fullName, String email, String personalNr, int accessLvl) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.personalNr = personalNr;
        this.accessLevel = accessLvl;
    }


    public int getId() {
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

    public int getAccessLevel() {
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
}

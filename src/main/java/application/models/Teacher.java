package application.models;

public class Teacher {
    private int id;
    private String fullName;
    private String email;
    private String personalNr;
    private String saltedPassword;
    private String salt;
    private String secQuestion;

    public Teacher(int id, String fullName, String email, String personalNr, String saltedPassword, String salt, String secQuestion) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.personalNr = personalNr;
        this.saltedPassword = saltedPassword;
        this.salt = salt;
        this.secQuestion = secQuestion;
    }

    public Teacher(int id, String fullName, String email, String personalNr, String secQuestion) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.personalNr = personalNr;
        this.secQuestion = secQuestion;
    }


    public int getId() {
        return this.id;
    }

    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getPersonalNr() {return personalNr;}
    public String getSecQuestion(){return secQuestion;}
    public String getSaltedPassword() { return saltedPassword;}
    public String getSalt() { return salt; }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonalNr(String personalNr) {
        this.personalNr = personalNr;
    }

    public void setSaltedPassword(String saltedPassword) {
        this.saltedPassword = saltedPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


}

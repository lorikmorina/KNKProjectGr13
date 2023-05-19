package application.models.dto;

public class CreateTeacherDto {
    private String fullName;
    private String email;
    private String personalNr;
    private String saltedPassword;
    private String salt;
    private String secQuestion;
    public CreateTeacherDto(String fullName, String email, String personalNr, String saltedPassword, String salt, String secQuestion) {
        this.fullName = fullName;
        this.email = email;
        this.personalNr = personalNr;
        this.saltedPassword = saltedPassword;
        this.salt = salt;
        this.secQuestion = secQuestion;
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
    public String getSaltedPassword() {
        return saltedPassword;
    }

    public String getSalt() {
        return salt;
    }
    public String getSecQuestion(){return secQuestion;}
}

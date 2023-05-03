package application.models.dto;

public class CreateUserDto {
    private String fullName;
    private String email;
    private String personalNr;
    private String saltedPassword;
    private String salt;

    public CreateUserDto(String fullName, String email, String personalNr, String saltedPassword, String salt) {
        this.fullName = fullName;
        this.email = email;
        this.personalNr = personalNr;
        this.saltedPassword = saltedPassword;
        this.salt = salt;
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
}

package application.service;

import application.models.Teacher;
import application.models.User;
import application.models.dto.CreateTeacherDto;
import application.models.dto.CreateUserDto;
import application.repository.TeacherRepository;
import application.repository.UserRepository;
import application.repository.interfaces.TeacherRepositoryInterface;
import application.repository.interfaces.UserRepositoryInterface;
import application.service.interfaces.TeacherServiceInterface;
import application.service.interfaces.UserServiceInterface;

import java.sql.SQLException;

public class TeacherService implements TeacherServiceInterface {
//    Repositories
    private static TeacherRepositoryInterface userRepository;

    public TeacherService() {
//        repositories
        this.userRepository = new TeacherRepository();
    }

    public Teacher login(String email, String password) throws SQLException {
        Teacher loginUser = TeacherRepository.getByEmail(email);
        if(loginUser == null){
            return null;
        }
        boolean isPasswordCorrect = PasswordHasher.compareSaltedHash(
                password, loginUser.getSalt(), loginUser.getSaltedPassword()
        );
        if(isPasswordCorrect){
            return loginUser;
        }

        return null;
    }

    public static Teacher signUp(String fullName, String email, String personalNr, String password, String secQuestion) throws SQLException{
       userRepository = new TeacherRepository();
        String salt = PasswordHasher.generateSalt();
        String saltedHash = PasswordHasher.generateSaltedHash(password, salt);
        CreateTeacherDto user = new CreateTeacherDto(fullName, email, personalNr, saltedHash, salt, secQuestion);
        userRepository.insert(user);
        return TeacherRepository.getByEmail(email);
    }

//    public static List<User> filterUsers(UserFilter filter, Pagination pagination){
//        try{
//
//            return UserRepository.getByFilter(filter, pagination);
//        }catch (SQLException e){
//            return null;
//        }
//    }

//    public static User updateUser(UpdateUserDto updateUserDto) throws SQLException{
////        logjiken e perditesimit
//        User user = UserRepository.update(updateUserDto);
//        return user;
//    }
}

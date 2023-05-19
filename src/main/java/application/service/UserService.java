package application.service;

import application.models.Pagination;
import application.models.User;
import application.models.dto.CreateUserDto;
import application.models.dto.UpdateUserDto;
import application.models.dto.UserFilter;
import application.repository.UserRepository;
import application.repository.interfaces.UserRepositoryInterface;
import application.service.interfaces.UserServiceInterface;

import java.sql.SQLException;
import java.util.List;

public class UserService implements UserServiceInterface {
//    Repositories
    private static UserRepositoryInterface userRepository;

    public UserService() {
//        repositories
        this.userRepository = new UserRepository();
    }

    public User login(String email, String password) throws SQLException {
        User loginUser = UserRepository.getByEmail(email);
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

    public static User signUp(String fullName, String email, String personalNr, String password, String secQuestion) throws SQLException{
       userRepository = new UserRepository();
        String salt = PasswordHasher.generateSalt();
        String saltedHash = PasswordHasher.generateSaltedHash(password, salt);
        CreateUserDto user = new CreateUserDto(fullName, email, personalNr, saltedHash, salt,secQuestion);
        userRepository.insert(user);
        return UserRepository.getByEmail(email);
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

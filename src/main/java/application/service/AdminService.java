package application.service;

import application.models.Admin;
import application.models.User;
import application.models.dto.CreateAdminDto;
import application.models.dto.CreateUserDto;
import application.repository.AdminRepository;
import application.repository.UserRepository;
import application.repository.interfaces.AdminRepositoryInterface;
import application.repository.interfaces.UserRepositoryInterface;
import application.service.interfaces.AdminServiceInterface;
import application.service.interfaces.UserServiceInterface;

import java.sql.SQLException;

public class AdminService implements AdminServiceInterface {
//    Repositories
    private static AdminRepositoryInterface userRepository;

    public AdminService() {
//        repositories
        this.userRepository = new AdminRepository();
    }

    public Admin login(String email, String password) throws SQLException {
        Admin loginUser = AdminRepository.getByEmail(email);
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

    public static Admin signUp(String fullName, String email, String personalNr, String password) throws SQLException{
       userRepository = new AdminRepository();
        String salt = PasswordHasher.generateSalt();
        String saltedHash = PasswordHasher.generateSaltedHash(password, salt);
        CreateAdminDto admin = new CreateAdminDto(fullName, email, personalNr, saltedHash, salt);
        userRepository.insert(admin);
        return AdminRepository.getByEmail(email);
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

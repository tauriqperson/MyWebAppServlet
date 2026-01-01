import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserService {
    private UserDAO userDAO;

    public UserService(Connection connection) {
        this.userDAO = new UserDAO(connection);
    }

    public String validateUserInput(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) {
            return "Username is required";
        }
        if (password == null || password.length() < 8) {
            return "Password must be at least 8 characters";
        }
        if (email == null || !isValidEmail(email)){
            return "Valid email is required";
        }
        return null;
    }

    private boolean isValidEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public User registerUser(String username, String password, String email) throws SQLException {

        //checking if the user does already exist
        User existingUser = userDAO.findByUsername(username);
        if (existingUser !=null){

            throw new IllegalArgumentException("Username already exists");
        }

        //Create new user with regular roles
        User user = new User(0, username, password, email, "USER");
        return userDAO.createUser(user);
    }

    public User loginUser (String username, String password) throws SQLException {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User updateUserProfile (int userId, String email) throws SQLException {
        User user = userDAO.findById(userId);

        if (user != null) {
            user.setEmail(email);
            if (userDAO.updateUser(user)) {
                return user;
            }
        }
        return null;
    }
}

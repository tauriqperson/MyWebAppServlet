import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
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
        logger.info("Attempting to register new user: {}", username);
        
        //checking if the user does already exist
        User existingUser = userDAO.findByUsername(username);
        if (existingUser !=null){
            logger.warn("Registration failed: Username already exists - {}", username);
            throw new IllegalArgumentException("Username already exists");
        }

        //Hash the password before storing
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        //Create new user with regular roles
        User user = new User(0, username, hashedPassword, email, "USER");
        User createdUser = userDAO.createUser(user);
        logger.info("User registered successfully: {}", username);
        return createdUser;
    }

    public User loginUser (String username, String password) throws SQLException {
        logger.info("Login attempt for user: {}", username);
        User user = userDAO.findByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            logger.info("Login successful for user: {}", username);
            return user;
        }
        logger.warn("Login failed for user: {}", username);
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

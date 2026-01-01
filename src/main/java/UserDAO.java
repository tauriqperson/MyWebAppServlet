import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
    private Connection connection;

    public UserDAO(Connection connection){
        this.connection = connection;
    }

    public User createUser(User user) throws SQLException{
        String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            
            // Encrypt email before storing
            try {
                String encryptedEmail = EncryptionUtil.encrypt(user.getEmail());
                stmt.setString(3, encryptedEmail);
            } catch (Exception e) {
                logger.error("Error encrypting email for user: {}", user.getUsername(), e);
                throw new SQLException("Failed to encrypt user data", e);
            }
            
            stmt.setString(4, user.getRole());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0){
                try (ResultSet rs = stmt.getGeneratedKeys()){
                    if (rs.next()){
                        user.setId(rs.getInt(1));
                    }
                }
            }
        }

        return user;
    }

    public User findByUsername (String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    String encryptedEmail = rs.getString("email");
                    String decryptedEmail = encryptedEmail;
                    
                    //Decrypt email when retrieving
                    try {
                        decryptedEmail = EncryptionUtil.decrypt(encryptedEmail);
                    } catch (Exception e) {
                        logger.error("Error decrypting email for user: {}", username, e);
                    }
                    
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            decryptedEmail,
                            rs.getString("role"));
                }
            }
        }
        return null;
    }

    public User findById (int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next ()) {
                    String encryptedEmail = rs.getString("email");
                    String decryptedEmail = encryptedEmail;
                    
                    // Decrypt email when retrieving
                    try {
                        decryptedEmail = EncryptionUtil.decrypt(encryptedEmail);
                    } catch (Exception e) {
                        logger.error("Error decrypting email for user ID: {}", id, e);
                    }
                    
                    return new User (
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            decryptedEmail,
                            rs.getString("role"));
                }
            }
        }
        return null;
    }

    public boolean updateUser (User user) throws SQLException{
        String sql = "UPDATE users SET email = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            //Encrypt email before updating
            try {
                String encryptedEmail = EncryptionUtil.encrypt(user.getEmail());
                stmt.setString(1, encryptedEmail);
            } catch (Exception e) {
                logger.error("Error encrypting email for user ID: {}", user.getId(), e);
                throw new SQLException("Failed to encrypt user data", e);
            }
            
            stmt.setInt(2, user.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}


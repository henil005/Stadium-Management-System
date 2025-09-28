package Stadium;
import java.sql.*;
import java.util.*;
public class User {
    int id;
    String username;
    String password;
    String email;
    String membershipLevel;

    public User(int id, String username, String password, String email, String membershipLevel) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.membershipLevel = membershipLevel;
    }
}
class UserManager {
    static Scanner sc=new Scanner(System.in);
    HashMap<String,String> hm=new HashMap<>();
    

    // Check if the username is unique
    boolean isUsernameUnique(String username) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)){

            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // Username exists
                }
                return true; // Username is unique
            
        }
        
    }
    void registerUser(String username, String password, String email, String membershipLevel) throws SQLException {
        String sql = "INSERT INTO Users (username, password, email, membership_level) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseUtil.getConnection();
        while (true) {
            System.out.println("Enter the username again:");
            username = sc.nextLine();
            
            if (isUsernameUnique(username)) {
                break; // Exit loop if the username is unique
            } else {
                System.out.println("Username already exists. Please choose another username.");
            }
        }
             PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password); // In a real application, ensure password is hashed
            stmt.setString(3, email);
            stmt.setString(4, membershipLevel);
            stmt.executeUpdate();
            hm.put(password,username );
    }

    void authenticateUser() {
        Scanner sc=new Scanner(System.in);
        while (true) {
            System.out.println("Enter your username for login:");
            String username = sc.nextLine();
            System.out.println("Enter your password for login:");
            String password = sc.nextLine();

            if (hm.containsKey(password) &&hm.containsValue(username) ) {
                System.out.println("Login successful!");
                System.out.println("Welcome "+username);
                break;
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
        sc.close();
    }
    void authenticateAdmin() {
        Map<String, String> hmad = new HashMap<>();
        hmad.put("admin123", "1234");
        
        Scanner sc = new Scanner(System.in);
        int attempts = 0;
    
        while (attempts < 3) { // Allow up to 3 attempts
            System.out.println("Enter your username for login:");
            String username = sc.next();
            System.out.println("Enter your password for login:");
            String password = sc.next();
    
            if (hmad.containsKey(username) && hmad.get(username).equals(password)) {
                System.out.println("Login successful!");
                break;
            } else {
                System.out.println("Invalid username or password. Please try again.");
                attempts++;
            }
    
            if (attempts == 3) {
                System.out.println("Too many failed attempts. Access denied.");
            }
        }
    
        sc.close(); // Close the scanner after the loop
    }
    public void updateUser(int id, String username,String password,String email, String membershipLevel) throws SQLException {
        Connection conn=DatabaseUtil.getConnection();
        CallableStatement ct=conn.prepareCall("{Call update_user(?,?,?,?,?)}");
        ct.setInt(1, id);
        ct.setString(2,username);
        ct.setString(3, password);
        ct.setString(4, email);
        ct.setString(5, membershipLevel);
        ct.executeUpdate();
        System.out.println("Updation Done");
       
    }
    public void deleteUser(int id) throws SQLException {
        Connection conn=DatabaseUtil.getConnection();
        CallableStatement ct=conn.prepareCall("{Call delete_user(?)}");
        ct.setInt(1, id);
        ct.executeUpdate();
        System.out.println("Deletion Done");
       
    }
    public void displayUsers() throws SQLException {
        Connection conn=DatabaseUtil.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select * from users");
       while (rs.next()) {
        if(rs.next()==false){
            System.out.println("No users in database");
            break;
        }
        System.out.println("User ID: "+rs.getInt("id"));
        System.out.println("Username: "+rs.getString("username"));
        System.out.println("Password: "+rs.getString("password"));
        System.out.println("Email: "+rs.getString("email"));
        System.out.println("Membership level: "+rs.getString("membership_level"));
       }
    }
}
class DatabaseUtil {
    static final String URL = "jdbc:mysql://localhost:3306/stadiummanagementportal";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
package Stadium;
import java.sql.*;
public class Ticket {
    int id;
    int event_id;
    String seatNumber;
    int userId;
    String status;

    public Ticket(int id, int event_id, String seatNumber, int userId, String status) {
        this.id = id;
        this.event_id = event_id;
        this.seatNumber = seatNumber;
        this.userId = userId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", seatNumber='" + seatNumber + '\'' +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
class TicketManager {
    static int top;
    Ticket[] ticketStack = new Ticket[10];

    public TicketManager() throws SQLException {
        top = -1;

        Ticket tickets = null;
        Statement stmt =  DatabaseUtil.getConnection().createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM tickets ");
        while (rs.next()) {
            int id = rs.getInt(1);
            int event_id = rs.getInt(2);
            String seat_number = rs.getString(3);
            int uid = rs.getInt(4);
            String status = rs.getString(5);
            tickets = new Ticket(id, event_id, seat_number, uid, status);
            top++;
            ticketStack[top] = tickets;
        }
    }

    public void push(Ticket ticket) throws SQLException {
        if (top >= ticketStack.length - 1) {
            System.out.println("Stack overflow");
        } else {
            top++;
            ticketStack[top] = ticket;
            bookTicket();
        }
    }

   

    public void bookTicket() throws SQLException {
        String sql = "INSERT INTO Tickets (event_id, seat_number, user_id, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketStack[top].event_id);
            stmt.setString(2, ticketStack[top].seatNumber);
            stmt.setInt(3, ticketStack[top].userId);
            stmt.setString(4, ticketStack[top].status);
            stmt.executeUpdate();
           
        }
    }
    void cancel_ticket(int id)throws SQLException{
        Connection conn=DatabaseUtil.getConnection();
        CallableStatement ct=conn.prepareCall("{Call delete_ticket(?)}");
        ct.setInt(1, id);
        ct.executeUpdate();
        System.out.println("Deletion Done");
    }
    void update_ticket(int id,int event_id,String seat_number,int user_id,String status)throws SQLException{
        Connection conn=DatabaseUtil.getConnection();
        CallableStatement ct=conn.prepareCall("{Call update_ticket(?,?,?,?,?)}");
        ct.setInt(1, id);
        ct.setInt(2,event_id);
        ct.setString(3, seat_number);
        ct.setInt(4,user_id);
        ct.setString(5, status);
        ct.executeUpdate();
        System.out.println("Updation Done");
    }

    public void StackDisplayAllTickets() {
        if (top == -1) {
            System.out.println("Stack is empty");
        } else {
            System.out.println("All tickets:");
            for (int i = top; i >= 0; i--) {
                System.out.println(ticketStack[i]);
            }
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
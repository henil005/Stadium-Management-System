package Stadium;
import java.sql.*;
import java.util.*;
class DatabaseUtil {
    static final String URL = "jdbc:mysql://localhost:3306/stadiummanagementportal";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}



class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        TicketManager ticketManager = new TicketManager();
        EventManager eventManager = new EventManager();
        UserManager userManager = new UserManager();

        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. Add Event");
            System.out.println("2. Update Event");
            System.out.println("3. Delete Event");
            System.out.println("4. Add Events to linkedlist and display");
            System.out.println("5. Add Ticket");
            System.out.println("6. View All Tickets");
            System.out.println("7. Add User");
            System.out.println("8. Update User");
            System.out.println("9. Delete User");
            System.out.println("10. Cancel Ticket");
            System.out.println("11. Update Ticket");
            System.out.println("12. Login User");
            System.out.println("13. Display Users");
            System.out.println("14. Authenticate Admin");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter event name:");
                    String eventName = scanner.nextLine();
                    System.out.println("Enter event date and time (yyyy-mm-dd hh:mm:ss):");
                    String eventDateTimeStr = scanner.nextLine();
                    System.out.println("Enter event type:");
                    String eventType = scanner.nextLine();
                    Timestamp eventDateTime = Timestamp.valueOf(eventDateTimeStr);
                    eventManager.addEvent(eventName, eventDateTime, eventType);
                    break;
                case 2:
                    System.out.println("Enter event ID:");
                    int eventIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.println("Enter new event name:");
                    String newEventName = scanner.nextLine();
                    System.out.println("Enter new event date and time (yyyy-mm-dd hh:mm:ss):");
                    String newEventDateTimeStr = scanner.nextLine();
                    System.out.println("Enter new event type:");
                    String newEventType = scanner.nextLine();
                    Timestamp newEventDateTime = Timestamp.valueOf(newEventDateTimeStr);
                    eventManager.updateEvent(eventIdToUpdate, newEventName, newEventDateTime, newEventType);
                    break;
                case 3:
                    System.out.println("Enter event ID to delete:");
                    int eventIdToDelete = scanner.nextInt();
                    eventManager.delete_Event(eventIdToDelete);
                    break;
                case 4:
                    EventList events = eventManager.getEvents();
                    events.displayEvents();
                    break;
                case 5:
                System.out.println("Enter ticket id");
                int tid=scanner.nextInt();
                    System.out.println("Enter ticket event ID:");
                    int ticketEventId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.println("Enter seat number:");
                    String seatNumber = scanner.nextLine();
                    System.out.println("Enter user ID:");
                    int userId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Ticket ticket = new Ticket(tid, ticketEventId, seatNumber, userId, "Available");
                    ticketManager.push(ticket);
                    break;
                case 6:
                    ticketManager.StackDisplayAllTickets();
                    break;
              
                case 7:
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter membership level:");
                    String membershipLevel = scanner.nextLine();
                    userManager.registerUser(username, password, email, membershipLevel);
                    break;
                case 8:
                    System.out.println("Enter user ID:");
                    int userIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.println("Enter new username:");
                    String newUsername = scanner.nextLine();
                    System.out.println("Enter new password:");
                    String newPassword = scanner.nextLine();
                    System.out.println("Enter new email:");
                    String newEmail = scanner.nextLine();
                    System.out.println("Enter new membership level:");
                    String newMembershipLevel = scanner.nextLine();
                    userManager.updateUser(userIdToUpdate, newUsername, newPassword, newEmail, newMembershipLevel);
                    break;
                case 9:
                    System.out.println("Enter user ID to delete:");
                    int userIdToDelete = scanner.nextInt();
                    userManager.deleteUser(userIdToDelete);
                    break;
                case 10:
                    System.out.println("Enter ticket ID to cancel:");
                    int ticketIdToCancel = scanner.nextInt();
                    ticketManager.cancel_ticket(ticketIdToCancel);
                    break;
                case 11:
                    System.out.println("Enter ticket ID to update:");
                    int ticketIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.println("Enter new event_ID");
                    int event_id=scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new seat number:");
                    String newSeatNumber = scanner.next();
                    System.out.println("Enter user_ID");
                    int user_id=scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new status:");
                    String newStatus = scanner.nextLine();
                    ticketManager.update_ticket(ticketIdToUpdate,event_id, newSeatNumber,user_id, newStatus);
                    break;
                    case 12:
                    userManager.authenticateUser();
                    break;
                    case 13:
                    userManager.displayUsers();
                    break;
                    case 14:
                    userManager.authenticateAdmin();
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
           
        }
        
    }
}

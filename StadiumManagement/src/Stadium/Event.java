package Stadium;
import java.sql.*;


public class Event {
    int id;
    String name;
    Timestamp dateTime;
    String type;
    Event next;

    public Event(int id, String name, Timestamp dateTime, String type) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.type = type;
        this.next = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", type='" + type + '\'' +
                '}';
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
class EventManager {

    void addEvent(String name, Timestamp dateTime, String type) throws SQLException {
        String sql = "INSERT INTO Events (name, date, type) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setTimestamp(2, dateTime); // Use Timestamp for a DATETIME column
            stmt.setString(3, type);
            stmt.executeUpdate();
            System.out.println("Insertion done");
        }
    }

    void updateEvent(int id, String name, Timestamp dateTime, String type) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             CallableStatement ct = conn.prepareCall("{Call update_event(?,?,?,?)}")) {
            ct.setInt(1, id);
            ct.setString(2, name);
            ct.setTimestamp(3, dateTime);
            ct.setString(4, type);
            ct.executeUpdate();
            System.out.println("Updation Done");
        }
    }

    void delete_Event(int id) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection();
             CallableStatement ct = conn.prepareCall("{Call delete_event(?)}")) {
            ct.setInt(1, id);
            ct.executeUpdate();
            System.out.println("Deletion Done");
        }
        EventList events = new EventList();
        Event deletedEvent = events.removeFirstEvent();
        if (deletedEvent != null) {
            System.out.println("Deleted event's ID: " + deletedEvent.getId());
            System.out.println("Deleted event's Name: " + deletedEvent.getName());
            System.out.println("Deleted event's Type: " + deletedEvent.getType());
        }
    }

    EventList getEvents() throws SQLException {
        Connection conn=DatabaseUtil.getConnection();
        EventList events = new EventList();
        String fetchEvent = "SELECT * FROM events";
        PreparedStatement statement = conn.prepareStatement(fetchEvent);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            Event event = new Event(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("date"),rs.getString("type"));
            events.add(event);
        }
        return events;
    }
}
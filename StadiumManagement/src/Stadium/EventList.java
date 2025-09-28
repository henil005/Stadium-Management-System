package Stadium;
public class EventList {
    private Event first;

    public EventList() {
        first = null;
    }

    public void add(Event event) {
        if (isEmpty()) {
            first = event;
        } else {
            Event temp = first;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = event;
        }
    }

    public Event removeFirstEvent() {
        if (isEmpty()) {
            System.out.println("List is empty.");
            return null;
        }
        Event event = first;
        first = first.next;
        event.next = null; // Disconnect the removed event from the list
        return event;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void displayEvents() {
        if (isEmpty()) {
            System.out.println("Event List is empty.");
            return;
        }
        System.out.println("Event List:");
        Event temp = first;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }
}

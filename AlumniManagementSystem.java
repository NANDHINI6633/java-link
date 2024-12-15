import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class Alumni {
    private int id;
    private String name;
    private String email;
    private String year;
    private String occupation;

    public Alumni(int id, String name, String email, String year, String occupation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.year = year;
        this.occupation = occupation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getYear() {
        return year;
    }

    public String getOccupation() {
        return occupation;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Email: " + email +
                ", Year: " + year + ", Occupation: " + occupation;
    }
}

class Event {
    private int id;
    private String name;
    private String date;
    private String description;
    private List<Alumni> invitedAlumni;

    public Event(int id, String name, String date, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.invitedAlumni = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void inviteAlumni(Alumni alumni) {
        invitedAlumni.add(alumni);
    }

    public List<Alumni> getInvitedAlumni() {
        return invitedAlumni;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Date: " + date +
                ", Description: " + description;
    }
}

public class AlumniManagementSystem extends JFrame {
    private List<Alumni> alumniList;
    private List<Event> eventList;
    private int nextAlumniId;
    private int nextEventId;

    private JTextArea displayArea;

    public AlumniManagementSystem() {
        alumniList = new ArrayList<>();
        eventList = new ArrayList<>();
        nextAlumniId = 1;
        nextEventId = 1;

        setupUI();
    }

    private void setupUI() {
        setTitle("Alumni Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton addAlumniButton = new JButton("Add Alumni");
        addAlumniButton.addActionListener(e -> addAlumni());

        JButton viewAlumniButton = new JButton("View Alumni");
        viewAlumniButton.addActionListener(e -> viewAlumni());

        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> addEvent());

        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.addActionListener(e -> viewEvents());

        JButton inviteAlumniButton = new JButton("Invite Alumni to Event");
        inviteAlumniButton.addActionListener(e -> inviteAlumniToEvent());

        JButton viewInvitesButton = new JButton("View Event Invites");
        viewInvitesButton.addActionListener(e -> viewEventInvites());

        buttonPanel.add(addAlumniButton);
        buttonPanel.add(viewAlumniButton);
        buttonPanel.add(addEventButton);
        buttonPanel.add(viewEventsButton);
        buttonPanel.add(inviteAlumniButton);
        buttonPanel.add(viewInvitesButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addAlumni() {
        String name = JOptionPane.showInputDialog(this, "Enter Alumni Name:");
        String email = JOptionPane.showInputDialog(this, "Enter Alumni Email:");
        String year = JOptionPane.showInputDialog(this, "Enter Graduation Year:");
        String occupation = JOptionPane.showInputDialog(this, "Enter Occupation:");

        if (name != null && email != null && year != null && occupation != null) {
            Alumni alumni = new Alumni(nextAlumniId++, name, email, year, occupation);
            alumniList.add(alumni);
            displayMessage("Alumni added successfully!");
        } else {
            displayMessage("Action canceled.");
        }
    }

    private void viewAlumni() {
        if (alumniList.isEmpty()) {
            displayMessage("No alumni records available.");
            return;
        }

        StringBuilder sb = new StringBuilder("Alumni List:\n");
        for (Alumni alumni : alumniList) {
            sb.append(alumni).append("\n");
        }
        displayMessage(sb.toString());
    }

    private void addEvent() {
        String name = JOptionPane.showInputDialog(this, "Enter Event Name:");
        String date = JOptionPane.showInputDialog(this, "Enter Event Date (DD/MM/YYYY):");
        String description = JOptionPane.showInputDialog(this, "Enter Event Description:");

        if (name != null && date != null && description != null) {
            Event event = new Event(nextEventId++, name, date, description);
            eventList.add(event);
            displayMessage("Event added successfully!");
        } else {
            displayMessage("Action canceled.");
        }
    }

    private void viewEvents() {
        if (eventList.isEmpty()) {
            displayMessage("No events available.");
            return;
        }

        StringBuilder sb = new StringBuilder("Event List:\n");
        for (Event event : eventList) {
            sb.append(event).append("\n");
        }
        displayMessage(sb.toString());
    }

    private void inviteAlumniToEvent() {
        int eventId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Event ID:"));
        int alumniId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Alumni ID:"));

        Event event = null;
        Alumni alumni = null;

        for (Event e : eventList) {
            if (e.getId() == eventId) {
                event = e;
                break;
            }
        }

        for (Alumni a : alumniList) {
            if (a.getId() == alumniId) {
                alumni = a;
                break;
            }
        }

        if (event != null && alumni != null) {
            event.inviteAlumni(alumni);
            displayMessage("Alumni " + alumni.getName() + " invited to event " + event.getName() + " successfully!");
        } else {
            displayMessage("Invalid Event ID or Alumni ID.");
        }
    }

    private void viewEventInvites() {
        int eventId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Event ID to View Invites:"));

        for (Event event : eventList) {
            if (event.getId() == eventId) {
                StringBuilder sb = new StringBuilder("Invited Alumni for Event: " + event.getName() + "\n");
                for (Alumni alumni : event.getInvitedAlumni()) {
                    sb.append(alumni).append("\n");
                }
                displayMessage(sb.toString());
                return;
            }
        }
        displayMessage("Event with ID " + eventId + " not found.");
    }

    private void displayMessage(String message) {
        displayArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AlumniManagementSystem system = new AlumniManagementSystem();
            system.setVisible(true);
        });
    }
}

import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class UserFile {
    
    public static void main(String[] args) {
        String outputFile = "user.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Write header
            writer.write("userID,Name,Username,Password,Email,Contact,Address,Level,Role");
            writer.newLine();

            // Process each file
            readStudents(writer, "student_enroll.txt");
            readReceptionists(writer, "receptionist.txt");
            readTutors(writer, "tutor.txt");
            readAdmins(writer, "admin.txt");

            System.out.println("All users merged into user.txt successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
            printUserFile(outputFile);
    }

    private static void readStudents(BufferedWriter writer, String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = splitCSV(line);
                if (parts.length >= 9) {
                    String userID = parts[0];
                    String name = parts[1];
                    String username = parts[2];
                    String password = parts[3];
                    String email = parts[4];
                    String contact = parts[5];
                    String address = parts[6];
                    String level = parts[7];
                    String role = "student";

                    writer.write(String.join(",", userID, name, username, password, email, contact, address, level, role));
                    writer.newLine();
                }
            }
        }
    }
    
    private static void readReceptionists(BufferedWriter writer, String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    String userID = parts[0];
                    String name = parts[1];
                    String username = parts[2];
                    String password = parts[3];
                    String email = parts[4];
                    String contact = parts[5];
                    String address = "null";
                    String level = "null";
                    String role = "receptionist";

                    writer.write(String.join(",", userID, name, username, password, email, contact, address, level, role));
                    writer.newLine();
                }
            }
        }
    }

    private static void readTutors(BufferedWriter writer, String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 7) {
                    String userID = parts[0];
                    String name = parts[1];
                    String username = parts[2];
                    String password = parts[3];
                    String email = parts[4];
                    String contact = parts[5];
                    String level = parts[6];
                    String address = "null";
                    String role = "tutor";

                    writer.write(String.join(",", userID, name, username, password, email, contact, address, level, role));
                    writer.newLine();
                }
            }
        }
    }

    private static void readAdmins(BufferedWriter writer, String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    String userID = parts[0];
                    String name = parts[1];
                    String username = parts[2];
                    String password = parts[3];
                    String email = parts[4];
                    String contact = parts[5];
                    String address = "null";
                    String level = "null";
                    String role = "admin";

                    writer.write(String.join(",", userID, name, username, password, email, contact, address, level, role));
                    writer.newLine();
                }
            }
        }
    }
    
    // Handle CSV with quoted fields containing commas (e.g., "123 Main St, City")
    private static String[] splitCSV(String line) {
        List <String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                tokens.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        tokens.add(current.toString().trim());
        return tokens.toArray(new String[0]);
    }
    private static void printUserFile(String filename) {
        System.out.println("\nContents of " + filename + ":");
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

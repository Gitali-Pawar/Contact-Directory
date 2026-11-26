import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

// Class representing a single Contact record
class Contact {
    String name;
    String phone;
    String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " | Phone: " + phone + " | Email: " + email;
    }
}

// Custom Hash Table implementation using Separate Chaining
class HashDirectory {
    private int size;
    private ArrayList<LinkedList<Contact>> table;

    // Constructor: Initializes the hash table with empty LinkedLists (buckets)
    public HashDirectory(int size) {
        this.size = size;
        this.table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(new LinkedList<>());
        }
    }

    // Hash Function: Converts contact name to a valid bucket index (0 to size - 1)
    private int hashFunction(String key) {
        int hashSum = 0;
        String lowerKey = key.toLowerCase();
        for (int i = 0; i < lowerKey.length(); i++) {
            hashSum += (int) lowerKey.charAt(i); // Add ASCII value of each character
        }
        return Math.abs(hashSum % size);
    }

    // Insert or Update a Contact: Average O(1) time complexity
    public void addContact(String name, String phone, String email) {
        int index = hashFunction(name);
        LinkedList<Contact> bucket = table.get(index);

        // Check if contact already exists in the chain and update it
        for (Contact c : bucket) {
            if (c.name.equalsIgnoreCase(name)) {
                c.phone = phone;
                c.email = email;
                System.out.println("Updated '" + name + "' at Bucket [" + index + "].");
                return;
            }
        }

        // Otherwise, add new contact to the end of the LinkedList chain
        bucket.add(new Contact(name, phone, email));
        System.out.println("Added '" + name + "' at Bucket [" + index + "].");
    }

    // Search a Contact by Name: Average O(1) time complexity
    public void searchContact(String name) {
        int index = hashFunction(name);
        LinkedList<Contact> bucket = table.get(index);

        for (Contact c : bucket) {
            if (c.name.equalsIgnoreCase(name)) {
                System.out.println("Found at Bucket [" + index + "] -> " + c);
                return;
            }
        }
        System.out.println("Contact '" + name + "' not found (Checked Bucket [" + index + "]).");
    }

    // Delete a Contact by Name: Average O(1) time complexity
    public void deleteContact(String name) {
        int index = hashFunction(name);
        LinkedList<Contact> bucket = table.get(index);

        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).name.equalsIgnoreCase(name)) {
                bucket.remove(i);
                System.out.println("Deleted '" + name + "' from Bucket [" + index + "].");
                return;
            }
        }
        System.out.println("Contact '" + name + "' not found.");
    }

    // Display the entire Hash Table structure to visualize buckets and collisions
    public void displayDirectory() {
        System.out.println("\n========= HASH TABLE DIRECTORY =========");
        for (int i = 0; i < size; i++) {
            LinkedList<Contact> bucket = table.get(i);
            System.out.print("Bucket [" + i + "]: ");
            if (bucket.isEmpty()) {
                System.out.println("Empty");
            } else {
                for (int j = 0; j < bucket.size(); j++) {
                    System.out.print(bucket.get(j).name + " (" + bucket.get(j).phone + ")");
                    if (j < bucket.size() - 1) {
                        System.out.print("  -->  "); // Visualizes the LinkedList chain
                    }
                }
                System.out.println();
            }
        }
        System.out.println("========================================\n");
    }
}

// Main Class with Interactive Console Menu
public class ContactDirectory {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Table size 11 makes collisions easy to demonstrate during your presentation
        HashDirectory directory = new HashDirectory(11);

        // Pre-loaded sample data
        directory.addContact("Aarav", "9876543210", "aarav@email.com");
        directory.addContact("Priya", "9123456780", "priya@email.com");
        directory.addContact("Rohan", "9988776655", "rohan@email.com");
        directory.addContact("Neha", "9012345678", "neha@email.com");
        directory.addContact("Gitali", "9821016842", "gitali@email.com");
        directory.addContact("Raj", "9372964829", "raj@email.com");
        directory.addContact("Disha", "9267483689", "disha@email.com");
        directory.addContact("Navya", "9759278347", "navya@email.com");


        while (true) {
            System.out.println("\n--- CONTACT DIRECTORY MENU ---");
            System.out.println("1. Add / Update Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. View Hash Table (Buckets & Chains)");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine().trim();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine().trim();
                    directory.addContact(name, phone, email);
                    break;
                case 2:
                    System.out.print("Enter Name to search: ");
                    String searchName = scanner.nextLine().trim();
                    directory.searchContact(searchName);
                    break;
                case 3:
                    System.out.print("Enter Name to delete: ");
                    String deleteName = scanner.nextLine().trim();
                    directory.deleteContact(deleteName);
                    break;
                case 4:
                    directory.displayDirectory();
                    break;
                case 5:
                    System.out.println("Exiting Contact Directory. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Select 1 to 5.");
            }
        }
    }
}

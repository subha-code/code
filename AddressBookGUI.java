package AddressBookSystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class AddressBookGUI extends Application {
    private AddressBook addressBook;
    private static final String STORAGE_FILE = "address_book.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        addressBook = new AddressBook();
        loadContactsFromFile(); // Load contacts from file when application starts

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();
        Label emailLabel = new Label("Email Address:");
        TextField emailField = new TextField();

        Button addButton = new Button("Add Contact");
        Button removeButton = new Button("Remove Contact");
        Button searchButton = new Button("Search Contact");
        Button displayButton = new Button("Display All Contacts");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        addButton.setOnAction(event -> {
            String name = nameField.getText();
            String phoneNumber = phoneField.getText();
            String emailAddress = emailField.getText();
            if (validateInput(name, phoneNumber, emailAddress)) {
                addressBook.addContact(new Contact(name, phoneNumber, emailAddress));
                saveContactsToFile(); // Save contacts to file after adding a new contact
                resultArea.setText("Contact added successfully.");
                clearFields(nameField, phoneField, emailField);
            }
        });

        removeButton.setOnAction(event -> {
            String name = nameField.getText();
            Contact contact = addressBook.searchContact(name);
            if (contact != null) {
                addressBook.removeContact(contact);
                saveContactsToFile(); // Save contacts to file after removing a contact
                resultArea.setText("Contact removed successfully.");
            } else {
                resultArea.setText("Contact not found.");
            }
            clearFields(nameField, phoneField, emailField);
        });

        searchButton.setOnAction(event -> {
            String name = nameField.getText();
            Contact contact = addressBook.searchContact(name);
            if (contact != null) {
                resultArea.setText(contact.toString());
            } else {
                resultArea.setText("Contact not found.");
            }
            clearFields(nameField, phoneField, emailField);
        });

        displayButton.setOnAction(event -> {
            resultArea.setText(addressBook.getAllContactsInfo());
        });

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(phoneLabel, 0, 1);
        gridPane.add(phoneField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(addButton, 0, 3);
        gridPane.add(removeButton, 1, 3);
        gridPane.add(searchButton, 0, 4);
        gridPane.add(displayButton, 1, 4);
        gridPane.add(resultArea, 0, 5, 2, 1);

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setTitle("Address Book System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadContactsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STORAGE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    String emailAddress = parts[2];
                    addressBook.addContact(new Contact(name, phoneNumber, emailAddress));
                }
            }
        } catch (IOException e) {
            // If file not found or other IO error occurs, just log it
            e.printStackTrace();
        }
    }

    private void saveContactsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STORAGE_FILE))) {
            for (Contact contact : addressBook.getAllContacts()) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmailAddress());
                writer.newLine();
            }
        } catch (IOException e) {
            // If file writing fails, just log it
            e.printStackTrace();
        }
    }

    private boolean validateInput(String name, String phoneNumber, String emailAddress) {
        if (name.isEmpty() || phoneNumber.isEmpty() || emailAddress.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Validation Error");
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
            return false;
        }
        // You can add more validation logic here for phone number format, email format, etc.
        return true;
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}


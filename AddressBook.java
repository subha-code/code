package AddressBookSystem;

import java.util.ArrayList;

public class AddressBook {
    private ArrayList<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null; // Contact not found
    }

    public String getAllContactsInfo() {
        StringBuilder info = new StringBuilder();
        for (Contact contact : contacts) {
            info.append(contact.toString()).append("\n");
        }
        return info.toString();
    }

    public ArrayList<Contact> getAllContacts() {
        return contacts;
    }
}


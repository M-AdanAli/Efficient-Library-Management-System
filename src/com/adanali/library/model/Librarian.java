package com.adanali.library.model;

/**
 * Represents a librarian user in the library system.
 */
public class Librarian extends User {

    public Librarian(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public String getRole() {
        return "Librarian";
    }

    // Wil add librarian-specific methods here in the future, if needed.
}

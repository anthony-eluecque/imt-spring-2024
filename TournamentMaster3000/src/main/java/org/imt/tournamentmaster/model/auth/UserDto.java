package org.imt.tournamentmaster.model.auth;


public class UserDto {

    private String email;

    private String password;

    private String firstName;

    private String lastName;


    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}

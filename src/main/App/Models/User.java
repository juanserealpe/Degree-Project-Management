package Models;

public class User {
    private String Name;
    private String LastName;
    private String PhoneNumber;

    public User(String name, String lastName, String phoneNumber) {
        this.Name = name;
        this.LastName = lastName;
        this.PhoneNumber = phoneNumber;
    }

    public String getName() {return Name;}
    public void setName(String name) {this.Name = name;}

    public String getLastName() {return LastName;}
    public void setLastName(String lastName) {this.LastName = lastName;}

    public String getPhoneNumber() {return PhoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.PhoneNumber = phoneNumber;}

}

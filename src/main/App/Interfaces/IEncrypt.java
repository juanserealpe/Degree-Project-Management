package Interfaces;

public interface IEncrypt {

    String Encrypt(String password);
    boolean Check(String password, String hashpassword);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package App.interfaces;

/**
 *
 * @author Bucket
 */
public interface IValidatorService {
    public void isValidPassword(String password) throws Exception;
    public void isValidEmail(String email) throws Exception;
    public void isValidTelephone(String telephone) throws Exception;
}

package bg.coincraft.userservice.service;

public interface PasswordService {

    String encrypt(String password);
    String decrypt(String password);
}

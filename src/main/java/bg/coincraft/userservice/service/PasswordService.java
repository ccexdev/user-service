package bg.coincraft.userservice.service;

public interface PasswordService {

    String encrypt(String password);
    boolean matches(String password);
}

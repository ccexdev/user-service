package bg.coincraft.userservice.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER(1),
    ADMIN(2);

    private final int value;
    UserRole(int value) {
        this.value = value;
    }

    public static UserRole fromValue(int value) {
        for (UserRole role : UserRole.values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid UserRole value " + value);
    }
}

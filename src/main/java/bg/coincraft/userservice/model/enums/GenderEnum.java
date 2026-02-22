package bg.coincraft.userservice.model.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {

    MALE(1),
    FEMALE(2);

    private final int value;

    GenderEnum(int value) {
        this.value = value;
    }

    public static GenderEnum fromValue(int value) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.value == value) {
                return genderEnum;
            }
        }
        throw new IllegalArgumentException("Invalid GenderEnum value " + value);
    }
}

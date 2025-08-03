package bg.coincraft.userservice.model.enums.converter;

import bg.coincraft.userservice.model.enums.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
        return userRole.getValue();
    }

    @Override
    public UserRole convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return UserRole.fromValue(integer);
    }
}

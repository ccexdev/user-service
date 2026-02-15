package bg.coincraft.userservice.model.enums.converter;

import bg.coincraft.userservice.model.enums.GenderEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderEnumConverter implements AttributeConverter<GenderEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GenderEnum genderEnum) {
        if (genderEnum == null) {
            return null;
        }
        return genderEnum.getValue();
    }

    @Override
    public GenderEnum convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }
        return GenderEnum.fromValue(value);
    }
}

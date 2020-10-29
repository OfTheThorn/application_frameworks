package com.matthiasvdd.opdracht.data.converters;

import com.matthiasvdd.opdracht.models.CategoryEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

// Auto apply will automatically apply this converter to all mapped attributes of Category type. No need for @Converter
// annotation
@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<CategoryEnum, String> {
    @Override
    public String convertToDatabaseColumn(CategoryEnum attribute) {
        if(attribute == null)
            return null;
        return attribute.getCode();
    }

    @Override
    public CategoryEnum convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return null;
        return Stream.of(CategoryEnum.values())
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

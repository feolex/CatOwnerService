package ru.feolex.ServiceLayer.Models.Serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.feolex.ServiceLayer.Enums.ColorMapper;
import ru.feolex.ServiceLayer.Models.CatModel;

import java.io.IOException;

public class CatModelSerializator extends StdSerializer<CatModel> {

    public CatModelSerializator() {
        this(null);
    }

    public CatModelSerializator(Class<CatModel> t) {
        super(t);
    }

    @Override
    public void serialize(CatModel value,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", value.getId());
        jsonGenerator.writeStringField("name", value.getName());
        jsonGenerator.writeStringField("birthdate", value.getBirthDate());
        jsonGenerator.writeStringField("breed", value.getBreed());
        jsonGenerator.writeStringField("color", ColorMapper.PrintColorName(value.getColor()));
        jsonGenerator.writeNumberField("ownerId", value.getOwner().getId());

        if(value.getFriendsIds().isEmpty()){
            jsonGenerator.writeStringField("hasFriends", String.valueOf(false));
        }else{
            jsonGenerator.writeStringField("hasFriends", String.valueOf(true));

            jsonGenerator.writeArrayFieldStart("friendsIds");
            jsonGenerator.writeStartArray();
            for (Long friendsId : value.getFriendsIds()) {
                jsonGenerator.writeNumber(friendsId);
            }
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndObject();
    }
}

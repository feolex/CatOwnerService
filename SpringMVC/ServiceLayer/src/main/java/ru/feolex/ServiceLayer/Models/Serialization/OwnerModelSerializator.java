package ru.feolex.ServiceLayer.Models.Serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.feolex.ServiceLayer.Models.OwnerModel;

import java.io.IOException;

public class OwnerModelSerializator extends StdSerializer<OwnerModel> {

    public OwnerModelSerializator() {
        this(null);
    }

    public OwnerModelSerializator(Class<OwnerModel> t) {
        super(t);
    }

    @Override
    public void serialize(OwnerModel value,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", value.getId());
        jsonGenerator.writeStringField("name", value.getName());
        jsonGenerator.writeStringField("birthdate", value.getBirthDate());

        jsonGenerator.writeStringField("hasCats", String.valueOf(!value.getCats().isEmpty()));

        if(!value.getCatsIds().isEmpty()){
            jsonGenerator.writeArrayFieldStart("catsIds");
//            jsonGenerator.writeStartArray();
            for (Long catId : value.getCatsIds()) {
                jsonGenerator.writeNumber(catId);
            }
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndObject();
    }
}

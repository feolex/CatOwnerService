package ru.feolex.SecurityLayer.Security.Serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.feolex.SecurityLayer.Security.ApiUser;
import ru.feolex.ServiceLayer.Models.OwnerModel;

import java.io.IOException;

public class ApiUserSerialization extends StdSerializer<ApiUser> {

    public ApiUserSerialization(){
        this(null);
    }
    protected ApiUserSerialization(Class<ApiUser> t) {
        super(t);
    }

    @Override
    public void serialize(ApiUser value,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", value.getId());
        jsonGenerator.writeStringField("email", value.getEmail());
        jsonGenerator.writeStringField("password", value.getPassword());
        jsonGenerator.writeStringField("role", value.getRole().name());
        jsonGenerator.writeNumberField("ownerId", value.getOwnerId());
        jsonGenerator.writeEndObject();
    }
}

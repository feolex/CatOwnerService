package ru.feolex.SecurityLayer.Security.Serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.SecurityLayer.Security.ApiUser;
import ru.feolex.SecurityLayer.Security.Role;
import ru.feolex.ServiceLayer.Models.OwnerModel;

import java.io.IOException;

public class ApiUserDeserialization extends StdDeserializer<ApiUser> {

    public ApiUserDeserialization(){
        this(null);
    }
    protected ApiUserDeserialization(Class<?> vc) {
        super(vc);
    }

    @Override
    public ApiUser deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Long id = node.get("id").longValue();
        String email = node.get("email").asText();
        String password = node.get("password").asText();
        Role role = Role.valueOf(node.get("role").asText());
        Long ownerId = node.get("ownerId").longValue();

        return new ApiUser(id, email, password, role, new Owner(ownerId));
    }
}

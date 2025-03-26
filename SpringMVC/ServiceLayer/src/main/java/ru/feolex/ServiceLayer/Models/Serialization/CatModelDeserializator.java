package ru.feolex.ServiceLayer.Models.Serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.feolex.ServiceLayer.Enums.Color;
import ru.feolex.ServiceLayer.Enums.ColorMapper;
import ru.feolex.ServiceLayer.Models.CatModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CatModelDeserializator extends StdDeserializer<CatModel> {
    public CatModelDeserializator() {
        this(null);
    }

    public CatModelDeserializator(Class<?> vc) {
        super(vc);
    }

    @Override
    public CatModel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Long id = node.get("id").longValue();
        String name = node.get("name").asText();
        String birthdate = node.get("birthdate").asText();
        String breed = node.get("breed").asText();
        Color color = ColorMapper.MapColor(node.get("color").asText());
        Long ownerId = node.get("ownerId").longValue();

        boolean hasFriends = node.get("hasFriends").booleanValue();

        List<Long> friendsIdsList = new ArrayList<>();
        if (hasFriends) {
            Iterator<JsonNode> friendsdsIterator = node.get("friendsIds").elements();

            while (friendsdsIterator.hasNext()) {
                friendsIdsList.add(friendsdsIterator.next().longValue());
            }
        }

        return new CatModel(id, name, birthdate, color, breed, ownerId, friendsIdsList);
    }
}

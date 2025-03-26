package ru.feolex.ServiceLayer.Models.Serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.feolex.ServiceLayer.Models.CatModel;
import ru.feolex.ServiceLayer.Models.OwnerModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OwnerModelDeserializator extends StdDeserializer<OwnerModel> {
    public OwnerModelDeserializator(){
        this(null);
    }

    public OwnerModelDeserializator(Class<?> vc) {
        super(vc);
    }

    @Override
    public OwnerModel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Long id = node.get("id").longValue();
        String name = node.get("name").asText();
        String birthdate = node.get("birthdate").asText();

        boolean hasCats = node.get("hasCats").asBoolean(true);

        List<Long> catIdsList = new ArrayList<>();
        if(hasCats) {

            Iterator<JsonNode> catsIds = node.get("catsIds").elements();

            while (catsIds.hasNext()) {
                catIdsList.add(catsIds.next().longValue());
            }
        }
        return new OwnerModel(id, name, birthdate, catIdsList.stream().map(CatModel::new).toList());
    }
}

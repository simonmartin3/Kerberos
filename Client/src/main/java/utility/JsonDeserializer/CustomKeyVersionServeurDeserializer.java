package utility.JsonDeserializer;

import Crypto.Lib.SecretKeyConverter;
import DTO.KeyVersionServeur;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomKeyVersionServeurDeserializer extends StdDeserializer<KeyVersionServeur> {
    public CustomKeyVersionServeurDeserializer()
    {
        this(null);
    }
    protected CustomKeyVersionServeurDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public KeyVersionServeur deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        System.out.println("ON EST DANS LE DESERIALIZER");
        KeyVersionServeur kvs = new KeyVersionServeur();

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        System.out.println(node);
        System.out.println("Get 1 : " + node.get(0));
        JsonNode jsonNode = node.get("version");
        System.out.println("Version : "+ jsonNode.asInt());
        kvs.setVersion(jsonNode.asInt());

        jsonNode = node.get("nomDuServeur");
        System.out.println("Nom du serveur : " + jsonNode.asText());
        kvs.setNomDuServeur(jsonNode.asText());

        jsonNode = node.get("secretKey");
        String algorithm = jsonNode.get("algorithm").asText();
        System.out.println("Algorithm : " + jsonNode.get("algorithm").asText());
        String encoded = jsonNode.get("encoded").asText();
        kvs.setSecretKey(SecretKeyConverter.stringToSecretKey(encoded,algorithm));
        return kvs;
    }
}

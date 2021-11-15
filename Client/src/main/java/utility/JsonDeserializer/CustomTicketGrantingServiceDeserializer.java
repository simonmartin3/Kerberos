package utility.JsonDeserializer;

import Crypto.Lib.ConverterStringByte;
import DTO.KeyVersionServeur;
import DTO.TicketGrantingService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javax.crypto.SealedObject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class CustomTicketGrantingServiceDeserializer extends StdDeserializer<TicketGrantingService> {

    public CustomTicketGrantingServiceDeserializer()
    {
        this(null);
    }
    public CustomTicketGrantingServiceDeserializer(Class<?> vc)
    {
        super(vc);
    }

    @Override
    public TicketGrantingService deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TicketGrantingService tgs = new TicketGrantingService();
        System.out.println("ON EST DANS LE DESERIALIZER");

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        JsonNode jsonNode = node.get("nomServeur");
        tgs.setNomServeur(jsonNode.asText());
        System.out.println("Nom du serveur : " + jsonNode.asText());
        String sealedObject = node.get("ticketGrantingServiceCrypted").asText();
        System.out.println("SealedObject Byte length : " + sealedObject);
        ByteArrayInputStream bis = new ByteArrayInputStream(ConverterStringByte.convertStringToArrayByte(sealedObject));
        ObjectInput in = new ObjectInputStream(bis);
        try {
            tgs.setTicketGrantingServiceCrypted((SealedObject) in.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return tgs;
    }
}

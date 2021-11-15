package utility.JsonSerializer;

import DTO.ClientAuthentifier;
import DTO.KeyVersionServeur;
import DTO.TicketGrantingService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class CustomClientAuthentifierSerializer extends StdSerializer<ClientAuthentifier> {

    public CustomClientAuthentifierSerializer()
    {
        this (null);
    }
    public CustomClientAuthentifierSerializer(Class<ClientAuthentifier> t)
    {
        super(t);
    }

    @Override
    public void serialize(ClientAuthentifier clientAuthentifier,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("nom", clientAuthentifier.getNom());
        jsonGenerator.writeNumberField("categorie",clientAuthentifier.getCategorie());
        Map<String, KeyVersionServeur> mapKey = clientAuthentifier.getMapKvs();
        jsonGenerator.writeObjectFieldStart("mapKvs");
        ObjectMapper mapper = new ObjectMapper();
        boolean first = true;
        for(Map.Entry<String, KeyVersionServeur> entry : mapKey.entrySet())
        {
            String fieldName = null;
            if(first)
            {
                first = false;
                fieldName="\""+entry.getKey()+"\""+":";
            }
            else
                fieldName=",\""+entry.getKey()+"\""+":";
            jsonGenerator.writeRaw(fieldName);
            String s=serializeKeyVersionServeur(entry.getValue());
            System.out.println(s.substring(1, s.length()-1));
            jsonGenerator.writeRaw(s);
        }
        jsonGenerator.writeEndObject();

        Map<String, TicketGrantingService> mapTgs = clientAuthentifier.getTgs();
        jsonGenerator.writeObjectFieldStart("tgs");
        first = true;
        for(Map.Entry<String, TicketGrantingService> entry : mapTgs.entrySet())
        {
            String fieldName = null;
            if(first)
            {
                first = false;
                fieldName="\""+entry.getKey()+"\""+":";
            }
            else
                fieldName=",\""+entry.getKey()+"\""+":";
            jsonGenerator.writeRaw(fieldName);

            String s = serializeTicketGrantingService(entry.getValue());
            System.out.println(s.substring(1, s.length()-1));
            jsonGenerator.writeRaw(s);
        }
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }


    private String serializeTicketGrantingService(TicketGrantingService tgs) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule moduleSerializer = new SimpleModule();
        moduleSerializer.addSerializer(TicketGrantingService.class,new CustomTicketGrantingServiceSerializer());
        mapper.registerModule(moduleSerializer);

        return mapper.writeValueAsString(tgs);

    }

    private String serializeKeyVersionServeur(KeyVersionServeur kvs) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(kvs);
    }


}

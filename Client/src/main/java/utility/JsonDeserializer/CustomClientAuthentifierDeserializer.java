package utility.JsonDeserializer;

import DTO.ClientAuthentifier;
import DTO.KeyVersionServeur;
import DTO.TicketGrantingService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomClientAuthentifierDeserializer extends StdDeserializer<ClientAuthentifier> {
    public CustomClientAuthentifierDeserializer()
    {
        this (null);
    }


    public CustomClientAuthentifierDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public ClientAuthentifier deserialize(JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ClientAuthentifier ca = new ClientAuthentifier();
        System.out.println("CLIENT AUTHENTIFIER DESERIALIZE");
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        ca.setNom(node.get("nom").asText());
        ca.setCategorie(node.get("categorie").asInt());
        Map<String, KeyVersionServeur> mapKey = new HashMap<String, KeyVersionServeur>();
        System.out.println(node.has("mapKvs"));
        System.out.println("\""+node.get("mapKvs")+"\"");
        String mapKvsString = "\""+node.get("mapKvs")+"\"";
        Map<String,String> vMapKvsString = new HashMap<>();
        int endIndex=mapKvsString.indexOf("}},",0);
        int startIndex = 2;
        String s = null;
        System.out.println("END INDEX  = ");
        while(endIndex> -1)
        {
            endIndex +=2;
            System.out.println("End Index : " + endIndex + " / start index = " + startIndex);
            s = mapKvsString.substring(startIndex,endIndex);
            System.out.println("S  = " +s);
            vMapKvsString.put(s.substring(1, s.indexOf(":")-1),s.substring(s.indexOf(":")+1));
            startIndex=endIndex+1;
            endIndex = mapKvsString.indexOf("}},",startIndex);
        }
        s = mapKvsString.substring(startIndex);
        System.out.println("S  = " +s);
        vMapKvsString.put(s.substring(1, s.indexOf(":")-1),s.substring(s.indexOf(":")+1));

        for(Map.Entry<String,String> entry: vMapKvsString.entrySet()){
            System.out.println("AHH : "+entry.getKey() + " value : " +entry.getValue());

            KeyVersionServeur kvs = deserializeKeyVersionServeur(entry.getValue());
            System.out.println(kvs);
            mapKey.put(entry.getKey(),kvs);
        }
        ca.setMapKvs(mapKey);


        String tgsString =  "\""+node.get("tgs")+"\"";
        System.out.println(tgsString);
        Map<String,String> mapTgsString = new HashMap<>();
        endIndex=tgsString.indexOf("},",0);
        startIndex = 2;
        while(endIndex> -1)
        {
            endIndex +=1;
            System.out.println("End Index : " + endIndex + " / start index = " + startIndex);
            s = tgsString.substring(startIndex,endIndex);
            mapTgsString.put(s.substring(1, s.indexOf(":")-1),s.substring(s.indexOf(":")+1));
            startIndex=endIndex+1;
            endIndex = tgsString.indexOf("},",startIndex);
        }
        s = tgsString.substring(startIndex);
        mapTgsString.put(s.substring(1, s.indexOf(":")-1),s.substring(s.indexOf(":")+1));

        Map<String, TicketGrantingService> ticketGrantingServiceMap = new HashMap<>();
        for(Map.Entry<String,String> entry: mapTgsString.entrySet()){
            System.out.println("AHH : "+entry.getKey() + " value : " + entry.getValue());

            ticketGrantingServiceMap.put(entry.getKey(), deserializeTicketGrantingService(entry.getValue()));

        }
        ca.setTgs(ticketGrantingServiceMap);
        return ca;
    }


    private KeyVersionServeur deserializeKeyVersionServeur(String s) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(KeyVersionServeur.class, new CustomKeyVersionServeurDeserializer());
        mapper.registerModule(module);

        return mapper.readValue(s, KeyVersionServeur.class);
    }

    private TicketGrantingService deserializeTicketGrantingService(String s) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(TicketGrantingService.class, new CustomTicketGrantingServiceDeserializer());
        mapper.registerModule(module);

        return mapper.readValue(s, TicketGrantingService.class);
    }
}

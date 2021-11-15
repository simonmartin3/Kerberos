package utility.JsonSerializer;

import Crypto.Lib.ConverterStringByte;
import DTO.TicketGrantingService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CustomTicketGrantingServiceSerializer extends StdSerializer<TicketGrantingService> {

    public CustomTicketGrantingServiceSerializer()
    {
        this (null);
    }

    public CustomTicketGrantingServiceSerializer(Class<TicketGrantingService> vs)
    {
        super(vs);
    }

    @Override
    public void serialize(TicketGrantingService ticketGrantingService,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("nomServeur", ticketGrantingService.getNomServeur());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(ticketGrantingService.getTicketGrantingServiceCrypted());
        out.flush();;
        jsonGenerator.writeStringField("ticketGrantingServiceCrypted", ConverterStringByte.convertArrayByteToString(baos.toByteArray()));
        jsonGenerator.writeEndObject();
    }
}

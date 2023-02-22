package com.company.quitter.util;

import com.company.quitter.model.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {
    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator jGen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jGen.writeStartObject();
        jGen.writeStringField("id", user.getId());
        jGen.writeStringField("username", user.getUsername());
        jGen.writeStringField("email", user.getEmail());
        jGen.writeObjectField("userProfile", user.getUserProfile());
        jGen.writeEndObject();
    }
}

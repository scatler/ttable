package com.scatler.ttable.encoder;

import com.scatler.ttable.message.TestMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class TestMessageEncoder implements Encoder.Text<TestMessage> {
    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(TestMessage msg) {
        String jsmsg = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsmsg = mapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsmsg;
    }
}

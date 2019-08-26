package com.scatler.ttable.controller;

import com.scatler.ttable.encoder.TestMessageEncoder;
import com.scatler.ttable.message.TestMessage;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(
        value = "/timetable",
        encoders = {TestMessageEncoder.class}
)
public class TimeTableEndpoint {

    private static final Logger logger = Logger.getLogger("TimeTable");
    /**
     * All open WebSocket sessions
     */
    static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void openConnection(Session session) {
        peers.add(session);
        logger.log(Level.INFO, "Session ID : " + session.getId());
    }

    @OnClose
    public void closedConnection(Session session) {
        //TODO: check peers
        /* Remove this connection from the queue */
        //peers.remove(session);
        logger.log(Level.INFO, "Connection closed.");
    }

    @OnError
    public void error(Session session, Throwable t) {
        //TODO: check peers
        //peers.remove(session);
        logger.log(Level.INFO, t.toString());
        logger.log(Level.INFO, "Connection error.");
    }

    public static void send(TestMessage msg) {
        /* Send updates to all open WebSocket sessions for this match */
        for (Session session : peers) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendObject(msg);
                    logger.log(Level.INFO, "Message sent", msg);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

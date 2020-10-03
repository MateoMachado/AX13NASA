package com.mycompany.ax13.nasa;

import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.DeleteSessionOptions;
import com.ibm.watson.assistant.v2.model.RuntimeResponseGeneric;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.model.MessageInput;
import java.util.List;
import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) {

        String apiKey = "zTu-dtkHM1rrrqVeczu9wQyEC381fi3II-SnvzSlgA2q";
        String fecha = "2020-10-03";

        // Suppress log messages in stdout.
        LogManager.getLogManager().reset();

        // Set up Assistant service.
        Authenticator authenticator = new IamAuthenticator(apiKey); // replace with API key
        Assistant service = new Assistant(fecha, authenticator);
        String assistantId = "44fce09b-af2b-4f18-b8fb-f669fd124692"; // replace with assistant ID

        // Create session.
        CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder(assistantId).build();
        SessionResponse session = service.createSession(createSessionOptions).execute().getResult();
        String sessionId = session.getSessionId();

        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text("Hola")
                .build();

        // Start conversation with empty message.
        MessageOptions messageOptions = new MessageOptions.Builder(assistantId,
                sessionId).input(input).build();
        MessageResponse response = service.message(messageOptions).execute().getResult();

        // Print the output from dialog, if any. Assumes a single text response.
        List<RuntimeResponseGeneric> responseGeneric = response.getOutput().getGeneric();
        if (responseGeneric.size() > 0) {
            if (responseGeneric.get(0).responseType().equals("text")) {
                System.out.println(responseGeneric.get(0).text());
            }
        }
        // We're done, so we delete the session.
        DeleteSessionOptions deleteSessionOptions = new DeleteSessionOptions.Builder(assistantId, sessionId).build();
        service.deleteSession(deleteSessionOptions).execute();
    }
}

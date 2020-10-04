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
import java.util.logging.LogManager;
import java.util.List;

/**
 *
 * @author nicop
 */
public class Chatbot {

    private final String apiKey;

    private final String date;

    private final String assistantID;

    private String sessionID;

    private Assistant service;

    public Chatbot(String apiKey, String assistantID, String date) {
        this.apiKey = apiKey;
        this.assistantID = assistantID;
        this.date = date;
        setUpBot();
    }

    private void setUpBot() {
        // Suppress log messages in stdout.
        LogManager.getLogManager().reset();

        // Set up Assistant service.
        Authenticator authenticator = new IamAuthenticator(apiKey);
        service = new Assistant(date, authenticator);

        // Create session.
        CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder(assistantID).build();
        SessionResponse session = service.createSession(createSessionOptions).execute().getResult();
        sessionID = session.getSessionId();
    }

    public String sendMessage(String text) {
        // Create Message
        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text(text)
                .build();
        MessageOptions messageOptions = new MessageOptions.Builder(assistantID, sessionID).input(input).build();
        MessageResponse response = service.message(messageOptions).execute().getResult();

        return answer(response);
    }

    private String answer(MessageResponse response) {
        if (response != null) {
            List<RuntimeResponseGeneric> responseGeneric = response.getOutput().getGeneric();
            if (responseGeneric.size() > 0) {
                String responseText = "";
                for (RuntimeResponseGeneric aResponse : responseGeneric) {
                    responseText += getResponse(aResponse) + "\n";
                }
                return responseText.isEmpty() ? responseText : responseText.substring(0, responseText.length() - 1);
            }
        }

        return "No estoy seguro de lo que dijiste";
    }

    private String getResponse(RuntimeResponseGeneric response) {
        switch (response.responseType()) {
            case "text":
                return response.text();
            case "suggestion":
                return "No estoy seguro de lo que dijiste"; //cambiar
            default:
                return "Error - Solo soporto texto";
        }
    }

    public void finishSession() {
        // We're done, so we delete the session.
        DeleteSessionOptions deleteSessionOptions = new DeleteSessionOptions.Builder(assistantID, sessionID).build();
        service.deleteSession(deleteSessionOptions).execute();
    }
}

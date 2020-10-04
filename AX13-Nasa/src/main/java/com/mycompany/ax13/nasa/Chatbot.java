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
import com.ibm.watson.assistant.v2.model.MessageContextSkills;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageInputOptions;
import com.ibm.watson.assistant.v2.model.MessageOutput;
import com.ibm.watson.assistant.v2.model.RuntimeIntent;
import java.util.logging.LogManager;
import java.util.List;
import java.util.Map;

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

    private static Person actualUser;

    public Chatbot(String apiKey, String assistantID, String date) {
        this.apiKey = apiKey;
        this.assistantID = assistantID;
        this.date = date;
        setUpBot();
    }

    public static Person getUser() {
        return actualUser;
    }

    /**
     * Crea y autentica sesion con el IBM Cloud Watson Assistant
     */
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
        MessageInputOptions inputOptions = new MessageInputOptions.Builder()
                .returnContext(true)
                .build();
        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text(text)
                .options(inputOptions)
                .build();
        MessageOptions messageOptions = new MessageOptions.Builder(assistantID, sessionID).input(input).build();
        MessageResponse response = service.message(messageOptions).execute().getResult();
        return answer(response);
    }

    private String answer(MessageResponse response) {
        if (response != null) {
            if (actualUser == null) {
                actualUser = createUser(response.getContext().skills(), response.getOutput());
            }
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

    private Person createUser(MessageContextSkills context, MessageOutput output) {
        Map<String, Object> contextVariables = context.getProperties().get("main skill").userDefined();
        if (contextVariables != null) {
            String name = String.valueOf(contextVariables.getOrDefault("nombre", "Usuario"));
            String lastName = String.valueOf(contextVariables.getOrDefault("apellido", ""));
            String genre = String.valueOf(contextVariables.getOrDefault("sexo", "Indefinido"));
            double age = (double) contextVariables.getOrDefault("edad", 0);
            double height = (double) contextVariables.getOrDefault("altura", 0);
            double weight = (double) contextVariables.getOrDefault("peso", 0);
            Person person = new Person(name, lastName, age, genre.toLowerCase(), height, weight);
            return person;
        }
        return null;
    }

    public void finishSession() {
        // We're done, so we delete the session.
        DeleteSessionOptions deleteSessionOptions = new DeleteSessionOptions.Builder(assistantID, sessionID).build();
        service.deleteSession(deleteSessionOptions).execute();
    }
}

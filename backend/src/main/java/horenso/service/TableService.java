package horenso.service;

import horenso.exceptions.InvalidTableIdException;
import horenso.model.ChatMessage;

public interface TableService {
    void broadcastChatMessage(long tableId, ChatMessage message);

    void addUserToTableObservers(long tableId, String username) throws InvalidTableIdException;

    void removeUserFromTableObservers(long tableId, String username) throws InvalidTableIdException;

    void sendChatMessage(long tableId, String username, String message) throws InvalidTableIdException;
}

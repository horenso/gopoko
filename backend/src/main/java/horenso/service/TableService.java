package horenso.service;

import horenso.exceptions.InvalidTableIdException;
import horenso.exceptions.SeatOccupiedException;
import horenso.model.ChatMessage;
import horenso.model.SeatedUser;
import horenso.model.TableInfo;

import java.util.List;

public interface TableService {
    void broadcastChatMessage(long tableId, ChatMessage message);

    void addUserToTableObservers(long tableId, String username) throws InvalidTableIdException;

    void seatUserOnTable(long tableId, short seat, String username) throws InvalidTableIdException,
            SeatOccupiedException;

    void removeUserFromTableObservers(long tableId, String username) throws InvalidTableIdException;

    void sendChatMessage(long tableId, String username, String message) throws InvalidTableIdException;

    List<SeatedUser> getSeatedUsers(long tableId) throws InvalidTableIdException;

    TableInfo getTableInfo(long tableId) throws InvalidTableIdException;
}

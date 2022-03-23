package horenso.service.impl;

import horenso.endpoint.websocket.WebsocketSessionManager;
import horenso.endpoint.websocket.notification.ChatMessageNotification;
import horenso.endpoint.websocket.notification.UserSeatedNotification;
import horenso.exceptions.InvalidTableIdException;
import horenso.exceptions.SeatOccupiedException;
import horenso.model.ChatMessage;
import horenso.model.ObservingUser;
import horenso.model.SeatedUser;
import horenso.model.TableInfo;
import horenso.persistence.entity.HoldemTable;
import horenso.persistence.entity.ObservingUserKey;
import horenso.persistence.entity.User;
import horenso.persistence.repository.HoldemTableRepository;
import horenso.persistence.repository.ObservingUserRepository;
import horenso.persistence.repository.UserRepository;
import horenso.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TableServiceImpl implements TableService {
    private final HoldemTableRepository holdemTableRepository;
    private final ObservingUserRepository observingUserRepository;
    private final UserRepository userRepository;
    private final WebsocketSessionManager websocketSessionManager;

    @Override
    public void broadcastChatMessage(long tableId, ChatMessage message) {
//        holdemTableRepository.fin
    }

    @Override
    public synchronized void addUserToTableObservers(long tableId, String username) throws InvalidTableIdException {
        HoldemTable table = getTableFromTableId(tableId);
        User user = userRepository.findFirstByName(username).get();

        ObservingUserKey id = new ObservingUserKey();
        id.setHoldemTableId(tableId);
        id.setUserId(user.getId());

        horenso.persistence.entity.ObservingUser observingUser = new horenso.persistence.entity.ObservingUser();
        observingUser.setId(id);
        observingUser.setUser(user);
        observingUser.setHoldemTable(table);
        observingUserRepository.save(observingUser);
    }

    @Override
    public void seatUserOnTable(long tableId, short seat, String username) throws InvalidTableIdException,
            SeatOccupiedException {
        HoldemTable table = getTableFromTableId(tableId);
        User user = userRepository.findFirstByName(username).get();

        // Check if the seat is occupied
        Optional<SeatedUser> userOnSeat = observingUserRepository.getSeatedUser(tableId, seat);
        if (userOnSeat.isPresent()) {
            throw new SeatOccupiedException(String.format("The seat %d on table %d is already occupied.",
                    seat, tableId));
        }

        // Seat user
        ObservingUserKey id = new ObservingUserKey();
        id.setHoldemTableId(tableId);
        id.setUserId(user.getId());

        horenso.persistence.entity.ObservingUser observingUser = new horenso.persistence.entity.ObservingUser();
        observingUser.setId(id);
        observingUser.setUser(user);
        observingUser.setHoldemTable(table);
        observingUser.setSeatNumber(seat);
        observingUserRepository.save(observingUser);

        List<ObservingUser> usersOnTable = observingUserRepository.getObservingUser(tableId);
        usersOnTable.forEach(u -> {
            UserSeatedNotification userSeatedNotification = new UserSeatedNotification(tableId, username, seat);
            websocketSessionManager.sendToUser(u.getUsername(), userSeatedNotification);
        });
    }

    @Override
    public void removeUserFromTableObservers(long tableId, String username) throws InvalidTableIdException {
        HoldemTable table = getTableFromTableId(tableId);
        User user = userRepository.findFirstByName(username).get();

        ObservingUserKey id = new ObservingUserKey();
        id.setHoldemTableId(table.getId());
        id.setUserId(user.getId());
        observingUserRepository.deleteById(id);
    }

    @Override
    public void sendChatMessage(long tableId, String username, String message) throws InvalidTableIdException {
        getTableFromTableId(tableId); // To assert that the table id is valid
        List<ObservingUser> userList = observingUserRepository.getObservingUser(tableId);
        LocalDateTime now = LocalDateTime.now();
//        LocalDateT
        userList.forEach(u -> {
            ChatMessageNotification notification = new ChatMessageNotification(username, message, now);
            websocketSessionManager.sendToUser(u.getUsername(), notification);
        });
        System.out.println(userList);
    }

    @Override
    public List<SeatedUser> getSeatedUsers(long tableId) throws InvalidTableIdException {
        getTableFromTableId(tableId);
        return observingUserRepository.getSeatedUsers(tableId);
    }

    @Override
    public TableInfo getTableInfo(long tableId) throws InvalidTableIdException {
        HoldemTable table = getTableFromTableId(tableId);
        return new TableInfo(table.getNumberOfUsers(), getSeatedUsers(tableId));
    }

    private HoldemTable getTableFromTableId(long tableId) throws InvalidTableIdException {
        Optional<HoldemTable> tableOptional = holdemTableRepository.findById(tableId);
        if (tableOptional.isEmpty()) {
            throw new InvalidTableIdException(String.format("table with id %d doesn't exist", tableId));
        }
        return tableOptional.get();
    }
}

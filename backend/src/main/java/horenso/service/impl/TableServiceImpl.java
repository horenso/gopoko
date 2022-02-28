package horenso.service.impl;

import horenso.endpoint.websocket.WebsocketSessionManager;
import horenso.endpoint.websocket.notification.ChatMessageNotification;
import horenso.exceptions.InvalidTableIdException;
import horenso.model.ChatMessage;
import horenso.persistence.entity.HoldemTable;
import horenso.persistence.entity.ObservingUser;
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

        ObservingUser observingUser = new ObservingUser();
        observingUser.setId(id);
        observingUser.setUser(user);
        observingUser.setHoldemTable(table);
        observingUserRepository.save(observingUser);
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
        List<String> usernames = observingUserRepository.findUsernamesOfSeatedUsers(tableId);
        LocalDateTime now = LocalDateTime.now();
//        LocalDateT
        usernames.forEach(u -> {
            ChatMessageNotification notification = new ChatMessageNotification(username, message, now);
            websocketSessionManager.sendToUser(u, notification);
        });
        System.out.println(usernames);
    }

    private HoldemTable getTableFromTableId(long tableId) throws InvalidTableIdException {
        Optional<HoldemTable> tableOptional = holdemTableRepository.findById(tableId);
        if (tableOptional.isEmpty()) {
            throw new InvalidTableIdException(String.format("table with id %d doesn't exist", tableId));
        }
        return tableOptional.get();
    }
}

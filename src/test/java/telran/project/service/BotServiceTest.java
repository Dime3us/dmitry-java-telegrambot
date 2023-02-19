package telran.project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import telran.project.dto.*;
import telran.project.repository.UserRepository;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BotServiceTest {

    @Autowired
    UserRepository repository;

    @Autowired
    BotService botService;


    Update update = new Update();
    User user = new User();

    Chat chat = new Chat();

    Message message = new Message();

    @Test

    public void onStartCommandReceived() {

        user.setId(1234L);

        chat.setId(1234L);
        chat.setFirstName("Test");

        message.setChat(chat);
        message.setMessageId(1234);
        message.setFrom(user);
        update.setMessage(message);

        assertEquals(repository.findById(1234L), Optional.empty());

        assertEquals(botService.onStartCommandReceived(new StartCommand(update)), "Hello Test, You have been registered in the service");

        assertFalse(repository.findById(1234L).isEmpty());
        String name = repository.findById(1234L).get().getName();
        assertEquals(name, "Test");

    }

    @Test
    public void onStopCommandReceived() {
        user.setId(1234L);
        chat.setId(1234L);
        chat.setFirstName("Test");
        message.setChat(chat);
        message.setMessageId(1234);
        message.setFrom(user);
        update.setMessage(message);

              assertEquals(botService.onStopCommandReceived(new StopCommand(update)),
                "Bye Test, You have finished communicating with this bot");

        assertEquals(repository.findById(1234L), Optional.empty());

    }
}



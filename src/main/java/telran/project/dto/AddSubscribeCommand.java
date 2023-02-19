package telran.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@NoArgsConstructor
@Component
public class AddSubscribeCommand implements Command {

    private Long chatId;
    Update update;
    Chat chat;

    public AddSubscribeCommand(Update update) {
        this.update = update;
        execute(update);
    }

    @Override
    public void execute(Update update) {
        chatId = update.getMessage().getChatId();
        chat = update.getMessage().getChat();
    }
}

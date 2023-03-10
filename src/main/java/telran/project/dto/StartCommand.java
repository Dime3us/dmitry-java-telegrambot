package telran.project.dto;


import lombok.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import telran.project.controller.BotController;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StartCommand implements Command {
    private Long chatId;
    private Chat chat;
    Update update;

    public StartCommand(Update update) {
        this.update = update;
        execute(update);
    }

    @Override
    public void execute(Update update) {
        chatId = update.getMessage().getChatId();
        chat = update.getMessage().getChat();
    }
}



package telran.project.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


@Getter
@NoArgsConstructor
@Component
public class SetAdminCommand implements Command {

    String password;
    Update update;
    Long chatId;

    public SetAdminCommand(Update update) {
        this.update = update;
        execute(update);
    }

    public SetAdminCommand(Update update, String password) {
        this.update = update;
        this.password = password;
        execute(update);
    }

    @Override
    public void execute(Update update) {
        chatId = update.getMessage().getChatId();
    }
}

package telran.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@NoArgsConstructor
@Component
public class GetRateCommand implements Command {
    private Long chatId;
    Update update;
    String message;


    public GetRateCommand(Update update) {
        this.update = update;
        execute(update);
    }

    @Override
    public void execute(Update update) {
        chatId = update.getMessage().getChatId();
        message = update.getMessage().getText();
    }
}

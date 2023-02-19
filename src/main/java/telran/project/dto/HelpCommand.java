package telran.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@NoArgsConstructor
@Component
public class HelpCommand implements Command {

    private final String textToSend = "With this bot, you will always be aware of the current exchange rate of your chosen currencies." +
            "To find out the exchange rate, send two currencies once in the format *** *** (example -> usd eur <-). " +
            "If you want to subscribe to daily messages with the courses of your " +
            "choice send the above command with ok at the end (example -> usd eur ok <-)." +
            "To unsubscribe from the update, send a message in the format *** *** no (example -> usd eur no <-)";
    private Long chatId;
    private Update update;

    public String getTextToSend() {
        return textToSend;
    }

    public HelpCommand(Update update) {
        this.update = update;
        execute(update);
    }

    @Override
    public void execute(Update update) {
        chatId = update.getMessage().getChatId();
    }
}

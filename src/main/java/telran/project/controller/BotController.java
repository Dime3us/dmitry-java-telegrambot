package telran.project.controller;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telran.project.dto.*;
import telran.project.entity.CurrencySubscriptions;
import telran.project.repository.UserRepository;
import telran.project.service.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
@Setter
//@NoArgsConstructor

@Service
public class BotController extends TelegramLongPollingBot implements MessageSender{
    private Map<Long, String> subscriptions = new HashMap<>();
    @Autowired
    private BotService service;

    @Autowired
    final BotConfig config;

    @Autowired
    private UserRepository userRepository;


    public BotController(BotConfig config) {
        this.config = config;
        MenuCommandList commandList = new MenuCommandList();

        try {
            this.execute(new SetMyCommands(commandList.getListOfCommands(), new BotCommandScopeDefault(), null));

        } catch (TelegramApiException e) {

            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    public String getBotUsername() {
        return config.getBotName();
    }


    public String getBotToken() {
        return config.getToken();
    }


    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            String answer;
            long chatId = update.getMessage().getChatId();
            if (message.matches("[a-zA-Z]{3}\\s*[a-zA-Z]{3}")) {
                answer = service.onGetRateCommandReceived(new GetRateCommand(update));
                sendMessage(chatId, answer);
                System.out.println(update.getMessage());
            }
            if (message.matches("[a-zA-Z]{3}\\s*[a-zA-Z]{3}\\s*(ok)\\s*")
                    || message.matches("[a-zA-Z]{3}\\s*[a-zA-Z]{3}\\s*(ok)\\s*(|1H|1h|1D|1d)\\s*")) {
                System.out.println("check");
                answer = service.onGetRateCommandReceived(new GetRateCommand(update));
                sendMessage(chatId, answer);

            }
            if (message.matches("[a-zA-Z]{3}\\s*[a-zA-Z]{3}\\s*(no)")) {
                answer = service.onGetRateCommandReceived(new GetRateCommand(update));
                sendMessage(chatId, answer);

            }
            if (update.getMessage().isCommand()) {
                switch (message) {
                    case "/start" -> {
                        answer = service.onStartCommandReceived(new StartCommand(update));
                        sendMessage(chatId, answer);
                    }
                    case "/stop" -> {
                        answer = service.onStopCommandReceived(new StopCommand(update));
                        sendMessage(chatId, answer);
                    }
                    case "/help" -> {
                        answer = service.onHelpCommandReceived(new HelpCommand(update));
                        sendMessage(chatId, answer);
                    }
//                    case "/admin" -> {
//                        answer = service.onSetAdminCommandReceived(new SetAdminCommand(update));
//                        sendMessage(chatId, answer);
//                    }
                    case "/list" -> {
                        answer = service.onGetCurrenciesListCommandReceived(new GetCurrenciesList(update));
                        sendMessage(chatId, answer);
                    }
                    case "/unsubscribe_all" -> {
                        answer = service.unSubscribe(chatId);
                        sendMessage(chatId, answer);
                    }
                    default -> sendMessage(chatId, "Command not recognized");
                }
            }

        }
    }

    @Override
    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }


}
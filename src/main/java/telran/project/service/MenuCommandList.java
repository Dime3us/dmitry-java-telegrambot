package telran.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import telran.project.repository.UserRepository;

import java.util.List;


public class MenuCommandList {

    @Autowired
    private UserRepository userRepository;
    final List<BotCommand> listOfCommands = List.of
            (
                    new BotCommand("/start", "use the command to start the bot"),
                    new BotCommand("/stop", "use the command to stop the bot"),
                    new BotCommand("/help", "info how to use this bot"),
                    new BotCommand("/list", "list of available currencies"),
                    new BotCommand("/unsubscribe_all", "unsubscribe from all updates")
            );


    public List<BotCommand> getListOfCommands() {

        return listOfCommands;
    }
}

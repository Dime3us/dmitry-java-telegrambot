package telran.project.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import telran.project.dto.*;
import telran.project.entity.CurrencySubscriptions;
import telran.project.entity.User;
import telran.project.repository.CurrSubscrRepository;
import telran.project.repository.UserRepository;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
//@Component
// @Setter
// @RequiredArgsConstructor

public class BotService {
    final String WRONGFORMAT = "Make sure the entered data is correct!";
    private MessageSender sender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CurrSubscrRepository currSubscrRepository;
    @Autowired
    private CurrencyService currencyService;

    private final String NOTREGISTERED = "To start chatting with this bot, please use the start command";

    @Autowired
    public void setMessageSender(@Lazy MessageSender sender) {
        this.sender = sender;
    }


    public String onStartCommandReceived(StartCommand startCommand) {
        var chatId = startCommand.getChatId();
        var chat = startCommand.getChat();
        String name = chat.getFirstName();

        if (userRepository.findById(startCommand.getChatId()).isEmpty()) {
            User user = new User();
            user.setChatId(chatId);
            user.setName(name);
            user.setRegisteredTime(new Date(System.currentTimeMillis()));
            user.setIsActive(false);
            user.setIsAdmin(false);
            userRepository.save(user);
            return "Hello " + name + ", You have been registered in the service";
        }

        return name + ", You are already registered in this bot";

    }

    public String onStopCommandReceived(StopCommand stopCommand) {
        var chatId = stopCommand.getChatId();
        var chat = stopCommand.getChat();
        String name = chat.getFirstName();
        userRepository.deleteById(chatId);
        return "Bye " + name + ", You have finished communicating with this bot";
    }

    public String onHelpCommandReceived(HelpCommand helpCommand) {
        var chatId = helpCommand.getChatId();
        if (userRepository.findById(chatId).isEmpty()) return NOTREGISTERED;
        return helpCommand.getTextToSend();
    }

    public void onGetStatCommandReceived(GetStatCommand getStatCommand) {

        long isActiveCount = userRepository.countByIsActive(true);
        long isUnActive = userRepository.countByIsActive(false);

        String statToSend = "To date, " + (isActiveCount + isUnActive) + " people have subscribed to the bot. " +
                "\nNumber of active users is : " + isActiveCount + "\nNumber of inactive users is : " + isUnActive;

        List<User> adminsList = userRepository.findByIsAdmin(true);

        for (User user : adminsList) {
            sender.sendMessage(user.getChatId(), statToSend);
        }

    }


    public String onSetAdminCommandReceived(SetAdminCommand setAdminCommand) {
        var chatId = setAdminCommand.getChatId();

        if (userRepository.findById(chatId).isEmpty()) {
            return NOTREGISTERED;
        }

        User user = userRepository.findById(chatId).get();
        if (user.getIsAdmin()) return "You are already an admin of this bot";
        else {
            sender.sendMessage(chatId, "Enter password!");
            user.setIsAdmin(true);
            userRepository.save(user);
            return "Done";
        }
    }

    public String onGetRateCommandReceived(GetRateCommand getRateCommand) {

        var chatId = getRateCommand.getChatId();

        String message = getRateCommand.getMessage().toUpperCase().replaceAll(" ", "");
        String currencyFrom = message.substring(0, 3);
        String currencyTo = message.substring(3, 6);
        String schedTime = message.length() == 10 ? message.substring(8, 10) : "1D";
        System.out.println(message);
        try {
            if (message.length() == 8 || message.length() == 10) {
                String okOrNo = message.substring(6, 8);
                if (okOrNo.equals("OK")) {
                    return addSubToRepo(chatId, currencyFrom, currencyTo, schedTime);
                }
                if (okOrNo.equals("NO")) {
                    return unSubscribe(chatId, currencyFrom, currencyTo);
                }
            }
            return currencyService.getCurrencyRate(currencyFrom, currencyTo);
        } catch (IOException e) {
            return WRONGFORMAT;
        }
    }

    public boolean repositoryContains(long chatId, String from, String to) {
        if (currSubscrRepository.findByChatId(chatId).isEmpty()) {
            return true;
        }
        List<CurrencySubscriptions> csList = currSubscrRepository.findByChatId(chatId);
        for (CurrencySubscriptions cs : csList
        ) {
            if (cs.getCurr_from().equals(from) && cs.getCurr_to().equals(to))
                return false;
        }
        return true;
    }

    public String addSubToRepo(long chatId, String from, String to, String schedTime) {

        if (repositoryContains(chatId, from, to)) {
            CurrencySubscriptions cs = new CurrencySubscriptions();
            cs.setChatId(chatId);
            cs.setCurr_from(from);
            cs.setCurr_to(to);
            cs.setScheduler_time(schedTime);
            currSubscrRepository.save(cs);
            try {
                return currencyService.getCurrencyRate(from, to) +
                        "\nYou have subscribed to exchange rate updates from " + from + " to " + to;
            } catch (IOException e) {
                return WRONGFORMAT;
            }
        }
        return "You are already subscribed to updates of this currency";

    }

    public void sendSubWithScheduler(String schedTime) {
        String from;
        String to;
        Long chatId;

        Iterable<CurrencySubscriptions> csList = currSubscrRepository.findAll();
        for (CurrencySubscriptions cs : csList
        ) {
            if (schedTime.equals(cs.getScheduler_time())) {
                from = cs.getCurr_from();
                to = cs.getCurr_to();
                chatId = cs.getChatId();
                try {
                    sender.sendMessage(chatId, currencyService.getCurrencyRate(from, to));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String unSubscribe(long chatId, String from, String to) {
        if (repositoryContains(chatId, from, to)) {
            return "You are not subscribed to the update you specified";
        }
        Iterable<CurrencySubscriptions> csList = currSubscrRepository.findByChatId(chatId);
        for (CurrencySubscriptions cs : csList
        ) {
            if (cs.getCurr_from().equals(from) && cs.getCurr_to().equals(to)) {
                currSubscrRepository.delete(cs);
                return "You have unsubscribed from updates on the exchange rate from " + from + " to " + to;
            }
        }
        return WRONGFORMAT;
    }

}


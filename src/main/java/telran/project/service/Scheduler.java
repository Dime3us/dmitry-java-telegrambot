package telran.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import telran.project.dto.GetStatCommand;

@Component
public class Scheduler {
    @Autowired
    BotService botService;

    @Scheduled(cron = "0 30 21 ? * MON-FRI")
    //  @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() {
        botService.onGetStatCommandReceived(new GetStatCommand());
    }

 //   @Scheduled(cron = "0 0 08 ? * MON-FRI")
 @Scheduled(cron = "0 05 22 ? * MON-FRI")
    public void currencySubDayScheduler() {
        botService.sendSubWithScheduler("1D");
    }


   // @Scheduled(cron = "0 0 8-18 * * MON-FRI")
   @Scheduled(cron = "0 04 22 ? * MON-FRI")
    public void currencySubHourScheduler() {
        botService.sendSubWithScheduler("1H");
    }
}
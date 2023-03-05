package telran.project.dto;

import org.telegram.telegrambots.meta.api.objects.Update;

public class GetCurrenciesList implements Command {

      Update update;


    public GetCurrenciesList(Update update) {
        this.update = update;
        execute(update);
    }


    @Override
    public void execute(Update update) {

    }
}

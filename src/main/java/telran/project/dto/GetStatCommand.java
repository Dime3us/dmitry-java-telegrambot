package telran.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import telran.project.repository.UserRepository;

@Getter
@NoArgsConstructor
@Component
public class GetStatCommand implements Command{


    @Override
    public void execute(Update update) {

    }
}

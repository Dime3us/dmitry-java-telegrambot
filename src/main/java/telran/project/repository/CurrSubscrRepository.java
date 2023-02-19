package telran.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import telran.project.entity.CurrencySubscriptions;

import java.util.List;
@Repository
public interface CurrSubscrRepository extends CrudRepository<CurrencySubscriptions, Long> {
    List<CurrencySubscriptions> findByChatId(long chatId);

}

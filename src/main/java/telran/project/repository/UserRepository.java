package telran.project.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import telran.project.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    long countByIsActive(boolean isActive);

    List<User> findByIsAdmin(boolean isAdmin);


}
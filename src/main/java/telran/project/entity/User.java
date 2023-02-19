package telran.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    private String name;
    private Date registeredTime;
    private Boolean isActive;
    private Boolean isAdmin;



}

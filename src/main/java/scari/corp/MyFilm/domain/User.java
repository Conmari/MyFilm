package scari.corp.MyFilm.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Поле должно быть заполнено")
    private String name;
    @NotBlank(message = "Поле должно быть заполнено")
    private String password;
    @NotBlank(message = "Поле должно быть заполнено")
    @Column(unique = true)
    private String email;
    @Column(name = "numbertel")
    @NotBlank(message = "Поле должно быть заполнено")
    private String number;
    private LocalDateTime dataRegistration = LocalDateTime.now();
//    private boolean active;

    // standard constructors / setters / getters / toString
}
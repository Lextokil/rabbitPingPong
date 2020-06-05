package core.dbh2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pong")
public class PongH2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String msg;
    private LocalDate dataProcessamento = LocalDate.now();

}

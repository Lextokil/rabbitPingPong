package core.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Getter
@Setter
@Document
@NoArgsConstructor
@AllArgsConstructor
public class PongMongo {

    @Id
    private String id;
    private String msg;
    private LocalDate dataProcessamento = LocalDate.now();

}

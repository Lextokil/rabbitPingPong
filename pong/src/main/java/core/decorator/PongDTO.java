package core.decorator;

import core.dbh2.PongH2;
import core.mongo.PongMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PongDTO {

    private String id;
    private String msg;
    private LocalDate dataProcessamento;

    public static PongDTO ofH2(PongH2 pongH2) {
        return new PongDTO(pongH2.getId(), pongH2.getMsg(), pongH2.getDataProcessamento());
    }
    public static PongDTO ofMongo(PongMongo pongMongo) {
        return new PongDTO(pongMongo.getId(), pongMongo.getMsg(), pongMongo.getDataProcessamento());
    }

}

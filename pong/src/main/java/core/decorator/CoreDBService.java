package core.decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CoreDBService {

    @Autowired
    private DbMongoService dbMongoService;

    @Autowired
    private DbH2Service dbH2Service;


    public PongDTO mongoSaveOrUpdate(PongDTO pongDTO) {
        return new DatabaseDecorator(dbMongoService).saveOrUpdate(pongDTO);
    }

    public void mongoDeleteAll() {
        new DatabaseDecorator(dbMongoService).deleteAll();
    }

    public List<PongDTO> mongoFindAll() {
        return new DatabaseDecorator(dbMongoService).findAll();
    }

    public List<PongDTO> mongoSaveAll(List<PongDTO> pongDTOS) {
        return new DatabaseDecorator(dbMongoService).saveAll(pongDTOS);
    }

    public PongDTO h2SaveOrUpdate(PongDTO pongDTO) {
        return new DatabaseDecorator(dbH2Service).saveOrUpdate(pongDTO);
    }

    public void h2DeleteAll() {
        new DatabaseDecorator(dbH2Service).deleteAll();
    }

    public List<PongDTO> h2FindAll() {
        return new DatabaseDecorator(dbH2Service).findAll();
    }

    public List<PongDTO> h2SaveAll(List<PongDTO> pongDTOS) {
        return new DatabaseDecorator(dbH2Service).saveAll(pongDTOS);
    }
}

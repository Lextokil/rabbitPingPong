package core.decorator;

import core.mongo.PongMongo;
import core.mongo.PongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DbMongoRepository implements IPongRepository {

    @Autowired
    private PongRepository pongRepository;

    @Override
    public List<PongDTO> findAll() {
        return pongRepository.findAll().stream().map(pongMongo -> new PongDTO(pongMongo.getId(), pongMongo.getMsg(), pongMongo.getDataProcessamento())).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        pongRepository.deleteAll();
    }

    @Override
    public PongDTO saveOrUpdate(PongDTO pongDTO) {
        try {
            if (Math.random() <= 0.3) {
                throw new RuntimeException("CABUM");
            }
            PongMongo pm = new PongMongo(pongDTO.getId(), pongDTO.getMsg(), pongDTO.getDataProcessamento());
            pm = pongRepository.save(pm);
            pongDTO.setId(pm.getId());
            return pongDTO;
        }catch (Exception e){
            throw new IllegalStateException(pongDTO.getMsg() +" ...CORONA INFECTED YOUR MONGODB SAVING IN H2 ");
        }

    }

    @Override
    public List<PongDTO> saveAll(List<PongDTO> pongDTOS) {
        List<PongMongo> pongMongos = pongDTOS.stream().map(pongDTO -> new PongMongo(pongDTO.getId(), pongDTO.getMsg(), pongDTO.getDataProcessamento())).collect(Collectors.toList());
        pongRepository.saveAll(pongMongos);
        return new ArrayList<>(pongMongos.stream().map(pongMongo -> new PongDTO(pongMongo.getId(), pongMongo.getMsg(), pongMongo.getDataProcessamento())).collect(Collectors.toList()));
    }
}

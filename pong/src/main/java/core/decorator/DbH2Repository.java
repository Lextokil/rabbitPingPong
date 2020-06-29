package core.decorator;

import core.dbh2.PongH2;
import core.dbh2.PongH2Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DbH2Repository implements IPongRepository {

    @Autowired
    private PongH2Repository pongH2Repository;

    @Override
    public List<PongDTO> findAll() {
        List<PongDTO> returnList = new ArrayList<>();
        pongH2Repository.findAll().forEach(pongH2 -> returnList.add(new PongDTO(pongH2.getId(), pongH2.getMsg(), pongH2.getDataProcessamento())));
        return returnList;
    }

    @Override
    public void deleteAll() {
        log.info("CLEANING H2...");
        pongH2Repository.deleteAll();
        log.info("--------------------------------------------------");
    }

    @Override
    public PongDTO saveOrUpdate(PongDTO pongDTO) {
        pongDTO.setMsg(pongDTO.getMsg() + " FROM H2");
        return PongDTO.ofH2(pongH2Repository.save(new PongH2(pongDTO.getId(), pongDTO.getMsg(), pongDTO.getDataProcessamento())));
    }

    @Override
    public List<PongDTO> saveAll(List<PongDTO> pongDTOS) {
        List<PongH2> pongH2s = pongDTOS.stream().map(pongDTO -> new PongH2(pongDTO.getId(), pongDTO.getMsg(), pongDTO.getDataProcessamento())).collect(Collectors.toList());
        pongH2Repository.saveAll(pongH2s);
        return new ArrayList<PongDTO>(pongH2s.stream().map(pongH2 -> new PongDTO(pongH2.getId(), pongH2.getMsg(), pongH2.getDataProcessamento())).collect(Collectors.toList()));
    }
}

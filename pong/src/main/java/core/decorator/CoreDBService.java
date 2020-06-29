package core.decorator;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CoreDBService {


    private final IPongRepository iPongRepository;

    public CoreDBService(IPongRepository iPongRepository) {
        this.iPongRepository = iPongRepository;
    }


    public PongDTO SaveOrUpdate(PongDTO pongDTO) {
        return iPongRepository.saveOrUpdate(pongDTO);
    }

    public void DeleteAll() {
        iPongRepository.deleteAll();
    }

    public List<PongDTO> FindAll() {
        return  iPongRepository.findAll();
    }

    public List<PongDTO> SaveAll(List<PongDTO> pongDTOS) {
        return iPongRepository.saveAll(pongDTOS);
    }

}

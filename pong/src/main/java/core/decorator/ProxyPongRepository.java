package core.decorator;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ProxyPongRepository implements IPongRepository {

    private final List<IPongRepository> iPongRepositorieswrapper;


    public ProxyPongRepository(List<IPongRepository> iPongRepositorieswrapper) {
        this.iPongRepositorieswrapper = iPongRepositorieswrapper;
    }


    @Override
    public List<PongDTO> findAll() {
        for (IPongRepository iPongRepository : iPongRepositorieswrapper) {
            try {
                return iPongRepository.findAll();
            } catch (Exception e) {
                log.error("Error in findAll of: {}", iPongRepository.getClass().getName(), e);
            }
        }
        throw new IllegalStateException("Error in all findAll's");
    }

    @Override
    public void deleteAll() {
        for (IPongRepository iPongRepository : iPongRepositorieswrapper) {
            try {
                iPongRepository.deleteAll();
                break;
            } catch (Exception e) {
                log.error("Error in deleteAll of: {} ", iPongRepository.getClass().getName(), e);
            }
        }
        throw new IllegalStateException("Error in all deleteAll's");
    }

    @Override
    public PongDTO saveOrUpdate(PongDTO pongDTO) {
        for (IPongRepository iPongRepository : iPongRepositorieswrapper) {
            try {
                return iPongRepository.saveOrUpdate(pongDTO);
            } catch (Exception e) {
                log.error("Error in saveOrUpdate of: {}", iPongRepository.getClass().getName(), e);
            }
        }
        throw new IllegalStateException("Error in all saveOrUpdate's");
    }

    @Override
    public List<PongDTO> saveAll(List<PongDTO> pongDTOS) {
        for (IPongRepository iPongRepository : iPongRepositorieswrapper) {
            try {
                return iPongRepository.saveAll(pongDTOS);
            } catch (Exception e) {
                log.error("Error in saveAll of: {}", iPongRepository.getClass().getName(), e);
            }
        }
        throw new IllegalStateException("Error in all saveAll's");
    }
}

package core.decorator;


import java.util.List;

public interface IPongRepository {

    List<PongDTO> findAll();

    void deleteAll();

    PongDTO saveOrUpdate(PongDTO pongDTO);

    List<PongDTO> saveAll(List<PongDTO> pongDTOS);
}

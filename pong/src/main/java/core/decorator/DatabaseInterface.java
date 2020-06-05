package core.decorator;


import java.util.List;

public interface DatabaseInterface {

    List<PongDTO> findAll();

    void deleteAll();

    PongDTO saveOrUpdate(PongDTO pongDTO);

    List<PongDTO> saveAll(List<PongDTO> pongDTOS);
}

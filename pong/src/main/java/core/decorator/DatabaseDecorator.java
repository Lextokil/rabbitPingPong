package core.decorator;

import java.util.List;

public class DatabaseDecorator implements DatabaseInterface {

    private DatabaseInterface wrapper;

    public DatabaseDecorator(DatabaseInterface wrapper) {
        this.wrapper = wrapper;
    }


    @Override
    public List<PongDTO> findAll() {
        return wrapper.findAll();
    }

    @Override
    public void deleteAll() {
        wrapper.deleteAll();
    }

    @Override
    public PongDTO saveOrUpdate(PongDTO pongDTO) {
        return wrapper.saveOrUpdate(pongDTO);
    }

    @Override
    public List<PongDTO> saveAll(List<PongDTO> pongDTOS) {
        return wrapper.saveAll(pongDTOS);
    }
}

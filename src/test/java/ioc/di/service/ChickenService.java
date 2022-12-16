package ioc.di.service;

import ioc.di.annotation.Autowired;
import ioc.di.annotation.Service;
import ioc.di.domain.Chicken;
import ioc.di.repository.ChickenRepository;

@Service
public class ChickenService {

    @Autowired
    private ChickenRepository chickenRepository;

    public Chicken getChicken(String chickenName) {
        return chickenRepository.findByName(chickenName);
    }

    public Chicken addChicken(Chicken chicken) {
        return chickenRepository.save(chicken);
    }

    public Chicken modifyChicken(Chicken chicken) {
        return chickenRepository.save(chicken);
    }

    public void deleteChicken(Chicken chicken) {
        chickenRepository.remove(chicken);
    }
}

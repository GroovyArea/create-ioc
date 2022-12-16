package ioc.di.repository;

import ioc.di.annotation.Repository;
import ioc.di.domain.Chicken;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ChickenRepository {

    private final Map<String, Chicken> database = new ConcurrentHashMap<>();

    public Chicken findByName(String chickenName) {
        return database.get(chickenName);
    }

    public Chicken save(Chicken chicken) {
        database.put(chicken.getName(), chicken);
        return chicken;
    }

    public void remove(Chicken chicken) {
        database.remove(chicken.getName());
    }
}

package co.com.mercadolibre.quasar.repository;

import co.com.mercadolibre.quasar.model.db.Satellites;
import org.springframework.data.repository.CrudRepository;

public interface SatelliteRepository extends CrudRepository<Satellites, String> {
}

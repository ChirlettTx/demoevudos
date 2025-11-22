package cl.ipss.demoevudos.repository;

import cl.ipss.demoevudos.models.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
  
}
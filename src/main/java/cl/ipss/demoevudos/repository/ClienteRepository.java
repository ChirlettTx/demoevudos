package cl.ipss.demoevudos.repository;
import cl.ipss.demoevudos.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    //revisar para agregar las busquedas por email o nombre,
}

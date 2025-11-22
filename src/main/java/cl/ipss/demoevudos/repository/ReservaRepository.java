package cl.ipss.demoevudos.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ipss.demoevudos.models.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;


public interface ReservaRepository extends
JpaRepository<Reserva, Long>{
    List<Reserva> findByFecha(LocalDate fecha);
    boolean existsByFechaAndHoraAndMesaId(LocalDate fecha, LocalTime hora, Long mesaId);
    List<Reserva> findByMesaIdAndFechaAndHora(Long mesaId, LocalDate fecha, LocalTime hora);
    List<Reserva> findByFechaBetween(LocalDate inicio, LocalDate fin);
    
}

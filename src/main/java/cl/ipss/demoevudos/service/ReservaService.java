package cl.ipss.demoevudos.service;
//aqui deberia ir la logica del servicio

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import cl.ipss.demoevudos.models.Reserva;

public interface ReservaService {
    List<Reserva> listarTodas();
    Reserva guardar(Reserva reserva);
    Optional<Reserva> buscarPorId(Long id);
    void eliminar(Long id);
    List<Reserva> reservasEnRango(LocalDateTime desde, LocalDateTime hasta);
    
    
} 

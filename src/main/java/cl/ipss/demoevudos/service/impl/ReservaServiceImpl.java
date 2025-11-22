package cl.ipss.demoevudos.service.impl;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.ipss.demoevudos.models.Reserva;
import cl.ipss.demoevudos.repository.ReservaRepository;
import cl.ipss.demoevudos.service.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository){
        this.reservaRepository = reservaRepository;
    }

    @Override
    public List<Reserva> listarTodas(){
        return reservaRepository.findAll();
    }

    @Override 
    public Reserva guardar(Reserva reserva){
        // 2. Buscar conflictos según mesa y horario
        List<Reserva> existentes =
                reservaRepository.findByMesaIdAndFechaAndHora(
                    reserva.getMesa().getId(),
                    reserva.getFecha(),
                    reserva.getHora()
                );

        // 3. Si ya existe una reserva para esa mesa/hora → error
        if (!existentes.isEmpty() && reserva.getId() == null) {
            throw new IllegalArgumentException("La mesa ya está reservada en ese horario.");
        }

        return reservaRepository.save(reserva);
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id){
        return reservaRepository.findById(id);
    }

    @Override
    public void eliminar(Long id){
        reservaRepository.deleteById(id);
    }

    @Override
    public List<Reserva> reservasEnRango(LocalDateTime desde, LocalDateTime hasta) {
        // Convertir desde/hasta en fecha-hora usando tus campos reales
        LocalDate desdeFecha = desde.toLocalDate();
        LocalDate hastaFecha = hasta.toLocalDate();

        return reservaRepository.findByFechaBetween(desdeFecha, hastaFecha);
    }
}

package cl.ipss.demoevudos.controllers;

import cl.ipss.demoevudos.dto.ReservaDTO;
import cl.ipss.demoevudos.models.Mesa;
import cl.ipss.demoevudos.models.Reserva;
import cl.ipss.demoevudos.repository.ClienteRepository;
import cl.ipss.demoevudos.repository.MesaRepository;
import cl.ipss.demoevudos.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private static final String ATTR_MESAS = "mesas";
    private static final String ATTR_CLIENTES = "clientes";
    private static final String ATTR_RESERVA_DTO = "reservaDTO";
    private static final String VIEW_FORM = "reservas/form";
    private static final String VIEW_LISTA = "reservas/lista";
    private static final String REDIRECT_LISTA = "redirect:/reservas";

    private final ReservaRepository reservaRepository;
    private final MesaRepository mesaRepository;
    private final ClienteRepository clienteRepository;

    public ReservaController(
        ReservaRepository reservaRepository,
        MesaRepository mesaRepository,
        ClienteRepository clienteRepository
    ) {
        this.reservaRepository = reservaRepository;
        this.mesaRepository = mesaRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaRepository.findAll());
        return VIEW_LISTA;
    }

    @GetMapping({"/nueva", "/nuevo"})
    public String mostrarFormularioNuevaReserva(Model model) {
        model.addAttribute(ATTR_RESERVA_DTO, new ReservaDTO());
        model.addAttribute(ATTR_MESAS, mesaRepository.findAll());
        model.addAttribute(ATTR_CLIENTES, clienteRepository.findAll());
        return VIEW_FORM;
    }

    @PostMapping("/guardar")
    public String guardarReserva(
        @Valid @ModelAttribute(ATTR_RESERVA_DTO) ReservaDTO reservaDTO,
        BindingResult result,
        Model model) {

        model.addAttribute(ATTR_MESAS, mesaRepository.findAll());
        model.addAttribute(ATTR_CLIENTES, clienteRepository.findAll());
            System.out.println("FechaHora ingresada: " + reservaDTO.getFechaHora());    
        if (result.hasErrors()) {
            return VIEW_FORM;
        }

        // Validar capacidad de la mesa
        Mesa mesa = mesaRepository.findById(reservaDTO.getMesaId()).orElse(null);
        if (mesa != null && reservaDTO.getCantidadPersonas() > mesa.getCapacidad()) {
            result.rejectValue("cantidadPersonas", "error.reservaDTO",
                    "La cantidad de personas excede la capacidad de la mesa (" + mesa.getCapacidad() + ").");
            return VIEW_FORM;
        }

        // Valida colisión de fecha y hora
        boolean existe = reservaRepository.existsByFechaAndHoraAndMesaId(
                reservaDTO.getFechaHora().toLocalDate(),
                reservaDTO.getFechaHora().toLocalTime(),
                reservaDTO.getMesaId()
        );
        if (existe) {
            result.reject("error.horarioOcupado", "Ya existe una reserva para esta mesa en ese horario.");
            return VIEW_FORM;
        }

        Reserva reserva = new Reserva();
        reserva.setFecha(reservaDTO.getFechaHora().toLocalDate());
        reserva.setHora(reservaDTO.getFechaHora().toLocalTime());
        reserva.setMesa(mesa);
        reserva.setCliente(clienteRepository.findById(reservaDTO.getClienteId()).orElse(null));
        reserva.setCantidadPersonas(reservaDTO.getCantidadPersonas());
        reserva.setEstado("ACTIVA");

        // Si id es distinto de null, es edición
        if (reservaDTO.getId() != null) {
            reserva.setId(reservaDTO.getId());
        }

        reservaRepository.save(reserva);
        return REDIRECT_LISTA;
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de reserva inválido: " + id));

        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setClienteId(reserva.getCliente().getId());
        dto.setMesaId(reserva.getMesa().getId());
        dto.setCantidadPersonas(reserva.getCantidadPersonas());
        dto.setFechaHora(reserva.getFecha().atTime(reserva.getHora()));

        model.addAttribute(ATTR_RESERVA_DTO, dto);
        model.addAttribute(ATTR_MESAS, mesaRepository.findAll());
        model.addAttribute(ATTR_CLIENTES, clienteRepository.findAll());
        return VIEW_FORM;
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        reservaRepository.deleteById(id);
        return REDIRECT_LISTA;
    }

    @GetMapping("/buscar")
    public String buscarPorFecha(@RequestParam("fecha") String fechaStr, Model model) {
        LocalDate fecha = LocalDate.parse(fechaStr);
        List<Reserva> reservas = reservaRepository.findByFecha(fecha);
        model.addAttribute("reservas", reservas);
        model.addAttribute("fechaBuscada", fecha);
        return VIEW_LISTA;
    }
}

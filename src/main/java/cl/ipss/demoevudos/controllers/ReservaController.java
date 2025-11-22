package cl.ipss.demoevudos.controllers;

import cl.ipss.demoevudos.dto.ReservaDTO;
import cl.ipss.demoevudos.models.Cliente;
import cl.ipss.demoevudos.models.Mesa;
import cl.ipss.demoevudos.models.Reserva;
import cl.ipss.demoevudos.repository.ClienteRepository;
import cl.ipss.demoevudos.repository.MesaRepository;
import cl.ipss.demoevudos.repository.ReservaRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // ⭐ LISTAR TODAS LAS RESERVAS
    @GetMapping
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaRepository.findAll());
        return "reservas/lista";
    }

    // ⭐ FORMULARIO PARA CREAR NUEVA RESERVA
    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        ReservaDTO reservaDTO = new ReservaDTO();
        model.addAttribute("reservaDTO", reservaDTO);

        model.addAttribute("mesas", mesaRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());

        return "reservas/form";
    }

    // ⭐ GUARDAR RESERVA
    @PostMapping("/guardar")
    public String guardarReserva(
            @Valid @ModelAttribute("reservaDTO") ReservaDTO reservaDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("mesas", mesaRepository.findAll());
            model.addAttribute("clientes", clienteRepository.findAll());
            return "reservas/form";
        }

        // Validación anti-duplicado
        boolean existe = reservaRepository.existsByFechaAndHoraAndMesaId(
        reservaDTO.getFechaHora().toLocalDate(),
        reservaDTO.getFechaHora().toLocalTime(),
        reservaDTO.getMesaId()
        );

        if (existe) {
            result.rejectValue("hora", "error.reservaDTO",
                    "Ya existe una reserva para esta mesa en ese horario.");
            model.addAttribute("mesas", mesaRepository.findAll());
            model.addAttribute("clientes", clienteRepository.findAll());
            return "reservas/form";
        }

        Reserva reserva = new Reserva();
        reserva.setFecha(reservaDTO.getFechaHora().toLocalDate());
        reserva.setHora(reservaDTO.getFechaHora().toLocalTime());
        reserva.setMesa(mesaRepository.findById(reservaDTO.getMesaId()).orElse(null));
        reserva.setCliente(clienteRepository.findById(reservaDTO.getClienteId()).orElse(null));
        reserva.setEstado("ACTIVA"); // opcional pero recomendado

        reservaRepository.save(reserva);

        return "redirect:/reservas";
    }

    // ⭐ ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        reservaRepository.deleteById(id);
        return "redirect:/reservas";
    }

    // ⭐ BUSCAR POR FECHA
    @GetMapping("/buscar")
    public String buscarPorFecha(@RequestParam("fecha") String fechaStr, Model model) {

        LocalDate fecha = LocalDate.parse(fechaStr);
        List<Reserva> reservas = reservaRepository.findByFecha(fecha);

        model.addAttribute("reservas", reservas);
        model.addAttribute("fechaBuscada", fecha);

        return "reservas/lista";
    }
}

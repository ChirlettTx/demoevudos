package cl.ipss.demoevudos.controllers;

import cl.ipss.demoevudos.models.Mesa;
import cl.ipss.demoevudos.repository.MesaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mesas")
public class MesaController {

    private final MesaRepository mesaRepository;

    public MesaController(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    // Listar mesas
    @GetMapping
    public String listarMesas(Model model) {
        List<Mesa> mesas = mesaRepository.findAll();
        model.addAttribute("mesas", mesas);
        return "mesas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaMesa(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "mesas/form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de mesa inválido: " + id));
        model.addAttribute("mesa", mesa);
        return "mesas/form";
    }

    @PostMapping("/guardar")
    public String guardarNuevaMesa(@ModelAttribute Mesa mesa) {
        mesaRepository.save(mesa);
        return "redirect:/mesas";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarMesa(@PathVariable Long id) {
        mesaRepository.deleteById(id);
        return "redirect:/mesas";
    }

}

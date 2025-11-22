package cl.ipss.demoevudos.controllers;

import cl.ipss.demoevudos.models.Mesa;
import cl.ipss.demoevudos.repository.MesaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    //Listar mesas
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

    @PostMapping("/guardar")
    public String guardarNuevaMesa(@ModelAttribute Mesa mesa) {
        mesaRepository.save(mesa);
        return "redirect:/mesas";
    }


}

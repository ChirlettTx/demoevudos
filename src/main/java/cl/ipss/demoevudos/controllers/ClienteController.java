package cl.ipss.demoevudos.controllers;

import cl.ipss.demoevudos.models.Cliente;
import cl.ipss.demoevudos.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Listar clientes
    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes/lista"; 
    }

    // Mostrar formulario para nuevo cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    // Guardar el nuevo cliente enviado por POST
    @PostMapping("/nuevo")
    public String guardarNuevoCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("cliente", cliente);
        return "clientes/form"; // Usa el mismo formulario que para crear
    }
    @PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
    clienteRepository.deleteById(id);
    return "redirect:/clientes";
    }




}

package cl.ipss.demoevudos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Página principal
    @GetMapping({"/", "/home"})
    public String index(Model model) {
        model.addAttribute("titulo", "Bienvenido a Evu2 Web");
        model.addAttribute("mensaje", "Esta es la página de inicio");
        return "index";
    }

    // Vista de clientes desde Home
    @GetMapping("/home/clientes")
    public String clientes(Model model) {
        model.addAttribute("titulo", "Clientes");
        model.addAttribute("mensaje", "Lista de clientes desde HomeController");
        return "clientes";
    }

    // Vista de mesas desde Home
    @GetMapping("/home/mesas")
    public String mesas(Model model) {
        model.addAttribute("titulo", "Mesas");
        model.addAttribute("mensaje", "Lista de mesas desde HomeController");
        return "mesas";
    }

    // Vista de reservas desde Home (NO choca con ReservaController)
    @GetMapping("/home/reservas")
    public String reservasHome(Model model) {
        model.addAttribute("titulo", "Reservas");
        model.addAttribute("mensaje", "Reservas desde HomeController");
        return "reservas_home";
    }
}

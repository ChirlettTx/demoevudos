package cl.ipss.demoevudos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final String ATTR_TITULO = "titulo";
    private static final String ATTR_MENSAJE = "mensaje";

    // Página principal
    @GetMapping({"/", "/home"})
    public String index(Model model) {
        model.addAttribute(ATTR_TITULO, "Bienvenido a Evu2 Web");
        model.addAttribute(ATTR_MENSAJE, "Esta es la página de inicio");
        return "index";
    }

    // Vista de clientes desde Home
    @GetMapping("/home/clientes")
    public String clientes(Model model) {
        model.addAttribute(ATTR_TITULO, "Clientes");
        model.addAttribute(ATTR_MENSAJE, "Lista de clientes desde HomeController");
        return "clientes";
    }

    // Vista de mesas desde Home
    @GetMapping("/home/mesas")
    public String mesas(Model model) {
        model.addAttribute(ATTR_TITULO, "Mesas");
        model.addAttribute(ATTR_MENSAJE, "Lista de mesas desde HomeController");
        return "mesas";
    }

    // Vista de reservas desde Home
    @GetMapping("/home/reservas")
    public String reservasHome(Model model) {
        model.addAttribute(ATTR_TITULO, "Reservas");
        model.addAttribute(ATTR_MENSAJE, "Reservas desde HomeController");
        return "reservas_home";
    }
}

package cl.ipss.demoevudos;

import cl.ipss.demoevudos.models.Cliente;
import cl.ipss.demoevudos.models.Mesa;
import cl.ipss.demoevudos.repository.ClienteRepository;
import cl.ipss.demoevudos.repository.MesaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepo;
    private final MesaRepository mesaRepo;

    public DataLoader(ClienteRepository clienteRepo, MesaRepository mesaRepo) {
        this.clienteRepo = clienteRepo;
        this.mesaRepo = mesaRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (mesaRepo.count() == 0) {
            Mesa m1 = new Mesa(); m1.setNombre("Mesa 1"); m1.setCapacidad(2); mesaRepo.save(m1);
            Mesa m2 = new Mesa(); m2.setNombre("Mesa 2"); m2.setCapacidad(4); mesaRepo.save(m2);
        }
        if (clienteRepo.count() == 0) {
            Cliente c1 = new Cliente(); c1.setNombre("Ana Perez"); c1.setEmail("ana@ejemplo.com"); c1.setTelefono("987654321"); clienteRepo.save(c1);
            Cliente c2 = new Cliente(); c2.setNombre("Juan Lopez"); c2.setEmail("juan@ejemplo.com"); c2.setTelefono("912345678"); clienteRepo.save(c2);
        }
    }
}

package gm.Zona_Fit;

import gm.Zona_Fit.modelo.Cliente;
import gm.Zona_Fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger LOGGER = LoggerFactory.getLogger(ZonaFitApplication.class);


	public static void main(String[] args) {
		LOGGER.info("Iniciando app");
		//levanta fabrica de spring
		SpringApplication.run(ZonaFitApplication.class, args);
		LOGGER.info("App finalizada :)");
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner console = new Scanner(System.in);

		LOGGER.info("App Zona Fita GYM");
		boolean salir = false;
		int opc = 0;
		while (salir != true){
			mostrarMenu();
			opc = Integer.parseInt(console.nextLine());
			switch (opc){
				case 1:
					agregarCliente(clienteServicio, console);
					break;
				case 2:
					buscarCliente(clienteServicio, console);
					break;
				case 3:
					modificarCliente(clienteServicio, console);
					break;
				case 4:
					eliminarCliente(clienteServicio, console);
					break;
				case 5:
					listarClientes(clienteServicio );
					break;
				case 9:
					salir = true;
					LOGGER.info("Saliendo del programa...");
					break;
				default:
					System.out.println("Debe ingresar una opcion válida (1-5) o 9 para salir...");
			}
		}
	}

	String nl = System.lineSeparator();

	public void mostrarMenu(){
		System.out.println("\n*** MENU ZONA FIT GYM ***");
		System.out.println("""
				Opciones: 
					1. Agregar Cliente
					2. Buscar Cliente
					3. Modificar Cliente
					4. Eliminar Cliente
					5. Listar Clientes
					9. Salir
				""");
		System.out.println("*************************");
		System.out.print("Ingrese la operacion que desee realizar (1-5): ");

	}

	// 1
	public void agregarCliente(IClienteServicio clienteServicio, Scanner read){
		System.out.println("*** Agregar cliente ***");
		System.out.print("Ingrese el nombre del cliente: ");
		String nombre = read.nextLine();

		System.out.print("Ingrese el apellido del cliente: ");
		String apellido = read.nextLine();

		System.out.print("Ingresa la membresia del cliente: ");
		int membresia = Integer.parseInt(read.nextLine());

		Cliente cliente = new Cliente();
		cliente.setNombre(nombre);
		cliente.setApellido(apellido);
		cliente.setMembresia(membresia);

		try {
			clienteServicio.guardarCliente(cliente);
			System.out.println("Cliente agregado: " + cliente);
		}catch (Exception e){
			System.out.println("Error al agregar cliente" );
			e.printStackTrace();
		}
	}

	// 2
	public void buscarCliente(IClienteServicio clienteServicio, Scanner read){
		System.out.println("*** Buscar cliente por Id ***");
		System.out.print("Ingrese el Id del cliente que desea buscar: ");
		int idCliente = Integer.parseInt(read.nextLine());
		Cliente cliente = clienteServicio.buscarClientePorId(idCliente);

		if(cliente != null){
			System.out.println("!Cliente encontrado¡");
			System.out.println(cliente);
		}else{
			System.out.println("Cliente con Id: " + idCliente + " no fue encontrado.");
		}

	}

	// 3
	public void modificarCliente(IClienteServicio clienteServicio, Scanner read){
		System.out.println("*** Modificar Cliente ***");
		System.out.print("Ingrese el Id del cliente que desea modificar: ");
		int idCliente = Integer.parseInt(read.nextLine());
		Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
		if (cliente != null){
			System.out.println("¡Cliente " + cliente.getNombre() + " con Id " + idCliente + " encontrado! ");
			System.out.print("Ingrese el nuevo nombre (ENTER para conservar original): ");
			String nombre = read.nextLine();
			if(nombre.trim().isEmpty()){
				nombre = cliente.getNombre();
			}

			System.out.print("Ingrese el nuevo apellido (ENTER) para conservar original): ");
			String apellido = read.nextLine();
			if (apellido.trim().isEmpty()){
				apellido = cliente.getApellido();
			}

			System.out.print("Ingrese la nueva membresia: (ENTER para conservar original): ");
			int nMembresia = 0;
			String membresia = read.nextLine();
			if (membresia.trim().isEmpty()){
				nMembresia = cliente.getMembresia();
			}else{
				nMembresia = Integer.parseInt(membresia);
			}

			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setMembresia(nMembresia);
			clienteServicio.guardarCliente(cliente);
			clienteServicio.guardarCliente(cliente);

			System.out.println("Cliente modificado: " + cliente);

		}else{
			System.out.println("Cliente con Id: " + idCliente + " No encontrado.");
		}
	}

	// 4
	public void eliminarCliente(IClienteServicio clienteServicio, Scanner scanner) {
		System.out.println("*** Eliminar Cliente ***");
		System.out.print("Ingresa el Id del Cliente que desea eliminar: ");
		int idCliente = Integer.parseInt(scanner.nextLine());

		Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
		if(cliente != null){
			LOGGER.info("¿Esta seguro de eliminar a " + cliente + "? (S/N) ");
			var opc = scanner.nextLine().trim().toLowerCase();
			switch (opc){
				case "s":
					clienteServicio.eliminarCliente(cliente);
					LOGGER.info(cliente + " eliminado exitosamente" + nl);
					break;
				case "n":
					LOGGER.info("Operacion cancelada" + nl);
					return;

				default:
					LOGGER.info("Operacion cancelada: debe ingresar una opcion valida (S/N)");
			}
		}else{
			LOGGER.info("Cliente con  Id= " + idCliente + " no fue encontrado");
		}

	}

	// 5
	public void listarClientes(IClienteServicio clienteServicio){
		LOGGER.info("*** Lista de Clientes ***" + nl);
		List<Cliente> clienteList = clienteServicio.listarClientes();
		clienteList.forEach(System.out::println);
	}






}

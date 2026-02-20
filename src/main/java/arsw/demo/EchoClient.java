package arsw.demo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class EchoClient implements EchoServer {

    private String nombre;
    private int puertoLocal;
    private EchoServer servidor;

    public void ejecutaServicio() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("=== CONFIGURACIÓN DEL CLIENTE ===");
            System.out.print("Puerto local: ");
            puertoLocal = sc.nextInt();
            sc.nextLine();
            System.out.print("IP servidor: ");
            String ipServidor = sc.nextLine();
            System.out.print("Puerto servidor: ");
            int puertoServidor = sc.nextInt();
            sc.nextLine();
            System.out.print("Tu nombre: ");
            nombre = sc.nextLine();
            EchoServer stub = (EchoServer) UnicastRemoteObject.exportObject(this, puertoLocal);
            Registry registry = LocateRegistry.getRegistry(ipServidor, puertoServidor);
            servidor = (EchoServer) registry.lookup("servidorChat");
            servidor.registrarCliente(stub, nombre);
            System.out.println("\n=== CHAT INICIADO ===");
            System.out.println("Escribe 'salir' para desconectarte\n");

            while (true) {
                System.out.print(nombre + "> ");
                String mensaje = sc.nextLine();

                if (mensaje.equalsIgnoreCase("salir")) {
                    break;
                }

                servidor.enviarMensaje(mensaje, nombre);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    @Override
    public void recibirMensaje(String mensaje, String remitente) throws RemoteException {
        System.out.println("\n[" + remitente + "]: " + mensaje);
        System.out.print(nombre + "> ");
    }

    @Override
    public String enviarMensaje(String mensaje, String remitente) throws RemoteException {
        return null;
    }

    @Override
    public void registrarCliente(EchoServer clienteCallback, String nombre) throws RemoteException {
    }

    public static void main(String[] args) {
        new EchoClient().ejecutaServicio();
    }
}
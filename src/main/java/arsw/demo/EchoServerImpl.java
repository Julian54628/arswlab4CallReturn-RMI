package arsw.demo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class EchoServerImpl implements EchoServer {

    private static ArrayList<EchoServer> clientes = new ArrayList<>();
    private static ArrayList<String> nombres = new ArrayList<>();
    private int puertoServidor;

    public EchoServerImpl() {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("=== CONFIGURACIÓN DEL SERVIDOR DEL CHAT ===");
            System.out.print("Puerto para publicar el servidor: ");
            puertoServidor = sc.nextInt();
            sc.nextLine();

            Registry registry = LocateRegistry.createRegistry(puertoServidor);
            EchoServer stub = (EchoServer) UnicastRemoteObject.exportObject(this, 0);
            registry.rebind("servidorChat", stub);

            System.out.println("\n=== SERVIDOR DE CHAT INICIADO ===");
            System.out.println("Puerto: " + puertoServidor);
            System.out.println("Esperando que ingresen clientes\n");

        } catch (Exception e) {
            System.err.println("ERROR EN SERVIDOR:");
            e.printStackTrace();
        }
    }

    @Override
    public String enviarMensaje(String mensaje, String remitente) throws RemoteException {
        System.out.println("[" + remitente + "]: " + mensaje);

        for (int i = 0; i < clientes.size(); i++) {
            String nombreCliente = nombres.get(i);
            if (!nombreCliente.equals(remitente)) {
                try {
                    EchoServer cliente = clientes.get(i);
                    cliente.recibirMensaje(mensaje, remitente);
                } catch (Exception e) {
                    System.err.println("Error enviando a " + nombreCliente);
                }
            }
        }
        return "OK";
    }

    @Override
    public void registrarCliente(EchoServer clienteCallback, String nombre) throws RemoteException {
        clientes.add(clienteCallback);
        nombres.add(nombre);
        System.out.println( nombre + " se conectó");
    }

    @Override
    public void recibirMensaje(String mensaje, String remitente) throws RemoteException {
    }

    public static void main(String[] args) {
        new EchoServerImpl();
    }
}
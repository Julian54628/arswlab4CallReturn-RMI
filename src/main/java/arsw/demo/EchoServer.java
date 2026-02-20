package arsw.demo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EchoServer extends Remote {
    public String enviarMensaje(String mensaje, String remitente) throws RemoteException;
    public void registrarCliente(EchoServer clienteCallback, String nombre) throws RemoteException;
    public void recibirMensaje(String mensaje, String remitente) throws RemoteException;
}
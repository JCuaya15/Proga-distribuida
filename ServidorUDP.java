import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.Buffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorUDP {
    private static String numeroDeVocales(String frase) {
        int res = 0;
        String fraseMin = frase.toLowerCase();
        for (int i = 0; i < fraseMin.length(); ++i) {
            switch(fraseMin.charAt(i)) {
                case 'a': case 'á':
                case 'e': case 'é':
                case 'i': case 'í':
                case 'o': case 'ó':
                case 'u': case 'ú':
                    res++;
                    break;
                    default:
                    // se ignoran las demás letras
            }
        }
        
        String aux = ""+res ;
        return aux;
    }
    public static void main(String[] args) throws SocketException {
        final int SERVER_PORT = 1024;
        byte[] buffer = new byte[1024];
        byte[] buffer2 = new byte[1024];
        try{
            System.out.println("Iniciado el servidor UDP");
            DatagramSocket socketUDP = new DatagramSocket(SERVER_PORT);
            while(true){
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(peticion);
                System.out.println("Recibo la informacion del cliente");
                String mensaje = new String(peticion.getData()).trim();
                System.out.println(mensaje);
                while ((mensaje != null) && (!mensaje.equals("END OF SERVICE"))) {
                    // Contar la cantidad de vocales que
                    String respuesta = numeroDeVocales(mensaje);
                    
                    buffer2 = respuesta.getBytes();
                    // Enviar la respuesta al cliente
                    int puertoCliente = peticion.getPort();
                    
                    InetAddress direccion = peticion.getAddress();
                    
                    DatagramPacket respuestaUDP = new DatagramPacket(buffer2, buffer2.length, direccion, puertoCliente);
                    
                    socketUDP.send(respuestaUDP);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

}



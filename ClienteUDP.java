import java.io.*;
import java.util.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.UnknownHostException;
import java.net.InetAddress;

public class ClienteUDP {

    public static void main(String[] args) {
        //puerto del servidor
        final int SERVER_PORT=1024;
        //buffer donde se almacenara los mensajes
        byte[] buffer = new byte[1024];
        byte[] buffer2 = new byte[1024];
        try {
            //puerto del servidor
            InetAddress SERVER_ADDRESS = InetAddress.getByName("localhost");
            //Creo el socket de UDP
            DatagramSocket socketUDP = new DatagramSocket();
            
            BufferedReader entradaStandard = new BufferedReader(new InputStreamReader(System.in));

            String mensaje1 = "";
            String mensaje = "";
            while (!(mensaje.equals("END OF SERVICE"))) {
				System.out.print("Text: ");
				mensaje1 = entradaStandard.readLine();
                buffer = mensaje1.getBytes();
				if (mensaje != null) {
                    DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, SERVER_ADDRESS,SERVER_PORT);
                    socketUDP.send(pregunta);
                    DatagramPacket peticion = new DatagramPacket(buffer2, buffer2.length);
                    socketUDP.receive(peticion);
                    mensaje = new String(peticion.getData());
					if (mensaje != null) {
						System.out.println("Server answer: '" + mensaje1 + "' has "  + mensaje + " vowels"  );
					} else {
						System.out.println("Comm. is closed!");
					}
				} else {
					System.err.println("Wrong input!");
				}
            }
            socketUDP.close();
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}


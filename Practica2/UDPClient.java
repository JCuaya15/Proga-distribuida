import java.net.*;
import java.io.*;
import java.io.BufferedReader;

public class UDPClient {
    public static void main(String args[]) {

        BufferedReader entradaStandard = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket aSocket = null;

        try {
            String cadena = "";
            int serverPort = 6001;
            byte[] m = cadena.getBytes(); // valores iniciales
            aSocket = new DatagramSocket();

            InetAddress aHost = InetAddress.getByName(args[0]);
            DatagramPacket respuesta = new DatagramPacket(m, m.length);
            DatagramPacket r = new DatagramPacket(m, cadena.length(), aHost, serverPort);

            System.out.print("Ingresa la cadena: ");
            cadena = entradaStandard.readLine();

            while (!(cadena.equals("END OF SERVICE"))) {

              if (cadena != null) {
                byte[] b = cadena.getBytes();
                r = new DatagramPacket(b, cadena.length(), aHost, serverPort);
                aSocket.send(r);
                byte[] buffer = new byte[1000];
                respuesta = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(respuesta);

                if (respuesta != null) {
                  System.out.println("El numero de vocales es :" + new String(respuesta.getData()));
                } else {
                  System.out.println("Comm. is closed!");
                }
              } else {
                System.err.println("Wrong input!");
              }

              System.out.print("Ingresa la cadena: ");
              cadena = entradaStandard.readLine();

            }

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
}
}

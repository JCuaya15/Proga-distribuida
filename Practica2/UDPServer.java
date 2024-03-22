import java.net.*;
import javax.print.DocFlavor.BYTE_ARRAY;
import java.io.*;

public class UDPServer {

	private static int numeroDeVocales(String frase) {
		int res = 0;
		String fraseMin = frase.toLowerCase();

		for (int i = 0; i < fraseMin.length(); i++) {
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

		return res;
	}

	public static void main(String args[]) {
		DatagramSocket aSocket = null;
		try {
			aSocket = new DatagramSocket(6001);
			// create socket at agreed port

			while (true) {
				byte[] buffer = new byte[1000];
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				String frase = new String(request.getData());
				String aux = new String( numeroDeVocales(frase) +"");
				byte [] v = aux.getBytes();
				DatagramPacket reply = new DatagramPacket(v, aux.length(), request.getAddress(), request.getPort());
				aSocket.send(reply);
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}
}

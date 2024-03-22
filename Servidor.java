//SERVIOR CON HILOS//
import java.net.Socket;
import java.net.ServerSocket;
import java.util.*;
import java.io.*;


public class Servidor{
	//Crear un socket servidor
	//sino se logra abortar programa
	private static ServerSocket creaListenSocket(int serverSockNum){
		ServerSocket server = null;
		try{
			server = new ServerSocket(serverSockNum);
		}catch (IOException e) {
		System.err.println("Problems in port: " + serverSockNum);
		System.exit(-1);
		}
		return server;
	}

  	//Establecer conexión con el servidor y devolver socket
  	//sino se logra abortar programa

	private static Socket creaClientSocket(ServerSocket server){
		Socket res = null;
		try {
			res = server.accept();
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}
		return res;
	}
	


	public static void main(String[] args) {

		Scanner leer =new Scanner(System.in);
		//int SERVER_PORT = 1024;
		int SERVER_PORT = 0;
		System.out.println("Dame el puerto para el servidor >= 1024: ");
		SERVER_PORT = leer.nextInt();
		System.out.println(SERVER_PORT);
		ServerSocket serverSocket = null; 	//para escuchar
		Socket clientSocket = null; 
		hilo hilo;
		serverSocket = creaListenSocket(SERVER_PORT);
		System.out.println("Servidor iniciado");
		while(true){
			// Espero la conexion del cliente
			clientSocket = creaClientSocket(serverSocket);
			hilo = new hilo(clientSocket);
			hilo.start();
			
			
			
		}
	}
	static class hilo extends Thread{
		Socket hilo = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		DataInputStream entradaCliente = null;
		DataOutputStream salidaCliente = null;
	
	
	
		private hilo(Socket hilo){
			this.hilo = hilo;
		}
	
		//Devuelve la cantidad de vocales de la frase
		private static int numeroDeVocales(String frase) {
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
			return res;
		}
	
	
		public void run(){
			PrintWriter salHaciaCliente = null;
			BufferedReader entDesdeCliente = null;
	
			try{
				salHaciaCliente = new PrintWriter(hilo.getOutputStream(), true);
				entDesdeCliente = new BufferedReader(new InputStreamReader(hilo.getInputStream()));
	
			}catch (IOException e) {
				System.err.println(e);
				System.exit(-1);
			}
	
			// Contar las vocales de las frases enviadas por el cliente
			String inputLine = "";
			try{
				inputLine = entDesdeCliente.readLine();
	
				while ((inputLine != null) && (!inputLine.equals("END OF SERVICE"))) {
					// Contar la cantidad de vocales que
	
					String respuesta = "'" + inputLine + "' has " + + numeroDeVocales(inputLine) + " vowels";
	
					// Enviar la respuesta al cliente
					salHaciaCliente.println(respuesta);
	
					// Recibir nueva petición del cliente
					inputLine = entDesdeCliente.readLine();
				}
				// Al cerrar cualquier canal de comunicación
				// utilizado por un socket, éste se cierra.
				// Para asegurarse que se envíen las respuestas que
				// están en el buffer se cierra el OutputStream.
				salHaciaCliente.close();
	
				// se cierra el socket
				hilo.close();
			}catch (IOException e) {
				System.err.println(e);
				System.exit(-1);
			}
			System.out.println("Bye, bye.");
		}
	
	}
}





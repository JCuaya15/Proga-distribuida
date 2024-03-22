import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.io.*;
/**
 * Remote Class for the "Hello, world!" example.
 */
public class Hello extends UnicastRemoteObject implements HelloInterface {
  private String message;
  /**
   * Construct a remote object
   * @param msg the message of the remote object, such as "Hello, world!".
   * @exception RemoteException if the object handle cannot be constructed.
   */
  public Hello (String msg) throws RemoteException {
      int res = 0;
      String fraseMin = msg.toLowerCase();
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
      String respuesta = "'" + msg + "' has " + + res + " vowels";
      message = respuesta;
  }
  /**
   * Implementation of the remotely invocable method.
   * @return the message of the remote object, such as "Hello, world!".
   * @exception RemoteException if the remote invocation fails.
   */
  public String say() throws RemoteException {
    return message;
  }
  /*public static int numeroDeVocales(String frase) {*/
  //Devuelve la cantidad de vocales de la frase
  




}

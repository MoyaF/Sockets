package utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class UDPClient {

    public static void main(String args[]) {

        Socket socket=null;
        String str;
        BufferedReader userInput = null;
        BufferedReader serverResponse = null;
        PrintWriter outputStream = null;

        try {
            //Seteo el host y el puerto del servidor
            socket = new Socket("localhost", 3000);

            userInput = new BufferedReader(new InputStreamReader(System.in));

            serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outputStream = new PrintWriter(socket.getOutputStream());

            System.out.println("Mi direccion ip : "+socket.getLocalAddress());
            System.out.println("Enviar al servidor: (Enviar x para terminar la conexion):");
            String response;

            try{
                str = userInput.readLine();
                while(str.compareTo("X")!=0){
                    outputStream.println(str );
                    outputStream.flush();
                    response = serverResponse.readLine();
                    System.out.println("Respuesta del servidor : "+response);
                    str =userInput.readLine();
                }

            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("Error al leer el socket");
             }
            finally{
                try {
                    //cerramos toodo//
                    serverResponse.close();
                    outputStream.close();
                    userInput.close();
                    socket.close();
                    System.out.println("Conexion cerrada");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    System.err.print("Se cago todo justo cuando estabas terminando");
                }
            }
        } catch (ConnectException e){
            System.err.println("Imposible establecer conexion, el servidor no esta funcionando");
        } catch (UnknownHostException e) {
            System.err.println("Hostname invalido");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
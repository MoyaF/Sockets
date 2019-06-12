package utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class UDPClient  implements Observer {

    Socket socket;
    String str;
    BufferedReader userInput;
    BufferedReader serverResponse;
    PrintWriter outputStream;
    ManageInput manageInput;
    ManageOutput manageOutput;
    String host;
    String port;
    Thread inputThread;
    Thread outputThread;

    public UDPClient() {

    }

    public void run(){
        try {

            userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Ingrese la direccion del host o X para salir");
            host = userInput.readLine();
            if(!host.equals("X")){
                System.out.println("Ingrese el puerto");
                port = userInput.readLine();
                socket = new Socket(host, Integer.valueOf(port));


                serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                outputStream = new PrintWriter(socket.getOutputStream());

                manageInput = new ManageInput(socket, userInput, serverResponse, outputStream, this);

                manageOutput = new ManageOutput(socket, userInput, serverResponse, outputStream, this);

                inputThread = new Thread(manageInput);
                outputThread = new Thread(manageOutput);
                inputThread.start();
                outputThread.start();
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch(ConnectException e){
            System.err.println("El servidor no esta funcionando");
            System.out.println("Intente de nuevo");
            this.run();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            socket.close();
            System.out.println("Conexion cerrada");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.print("Se cago todo justo cuando estabas terminando");
        }
    }
}

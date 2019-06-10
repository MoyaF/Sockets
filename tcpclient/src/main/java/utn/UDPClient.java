package utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

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
    Boolean connected = Boolean.TRUE;
    Thread inputThread;
    Thread outputThread;

    public UDPClient() {

    }

    public void run(){
        try {

            userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Ingrese la direccion del host");
            host = userInput.readLine();
            System.out.println("Ingrese el puerto");
            port = userInput.readLine();
            socket = new Socket(host, Integer.valueOf(port));


            serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outputStream = new PrintWriter(socket.getOutputStream());

            manageInput = new ManageInput(socket,userInput,serverResponse,outputStream,this);

            manageOutput = new ManageOutput(socket,userInput,serverResponse,outputStream,this);

            inputThread = new Thread(manageInput);
            outputThread = new Thread(manageOutput);
            inputThread.start();
            outputThread.start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Observable observable, Object o) {
        inputThread.interrupt();
        outputThread.interrupt();

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
}

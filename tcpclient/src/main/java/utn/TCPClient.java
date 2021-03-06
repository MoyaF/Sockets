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

public class TCPClient implements Observer {

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

    public TCPClient() {

    }

    public void run() {
        try {

            userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Ingrese la direccion del host o X para salir");
            host = userInput.readLine();
            if (!host.equals("X")) {
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
            System.err.println("El host: " + host + " no existe, intente con un host valido");
            this.run();
        } catch (NumberFormatException e) {
            System.err.println("Por favor, ingrese un numero en el puerto");
            this.run();
        } catch (ConnectException e) {
            System.err.println("El servidor no esta funcionando o el puerto: " + port + " no es el correcto");
            System.err.println("Intente de nuevo");
            this.run();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            socket.close();
            inputThread.interrupt();
            outputThread.interrupt();

            System.out.println("Conexion cerrada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

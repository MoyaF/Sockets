package utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;


public class ManageInput extends Observable implements Runnable {

    Socket socket;
    String str;
    BufferedReader userInput;
    BufferedReader serverResponse;
    PrintWriter outputStream;

    public ManageInput(Socket socket, BufferedReader userInput, BufferedReader serverResponse, PrintWriter outputStream, UDPClient udp) {
        super();
        this.socket = socket;
        this.userInput = userInput;
        this.serverResponse = serverResponse;
        this.outputStream = outputStream;
        this.addObserver(udp);
    }

    @Override
    public void run() {
        try {
            str = serverResponse.readLine();
            while (str.compareTo("X") != 0) {
                System.out.println("Mensaje del servidor : " + str);
                str = serverResponse.readLine();
            }
            System.out.println("El servidor cerro la conexion");
            setChanged();
            notifyObservers();
        }catch(SocketException e){

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


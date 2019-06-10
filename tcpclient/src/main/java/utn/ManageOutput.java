package utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

public class ManageOutput extends Observable implements Runnable {

    Socket socket;
    String str;
    BufferedReader userInput;
    BufferedReader serverResponse;
    PrintWriter outputStream;

    public ManageOutput(Socket socket, BufferedReader userInput, BufferedReader serverResponse, PrintWriter outputStream, UDPClient udp) {
        super();
        this.socket = socket;
        this.userInput = userInput;
        this.serverResponse = serverResponse;
        this.outputStream = outputStream;
        this.addObserver(udp);
    }
    @Override
    public void run() {
        System.out.println("Enviar al servidor: (Enviar X para terminar la conexion):");

        try{
            str = userInput.readLine();
            while(str.compareTo("X")!=0){
                outputStream.println(str );
                outputStream.flush();
                str =userInput.readLine();
            }
            setChanged();
            notifyObservers();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Error al leer el socket");
        }
    }

}

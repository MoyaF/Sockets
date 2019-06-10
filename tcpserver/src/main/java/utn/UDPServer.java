package utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Observable;

public class UDPServer extends Observable{

    ServerSocket ss;
    BufferedReader userInput;
    String str;
    ThreadAcceptCon acceptCon;


    public void startServer() {
        try{
            userInput = new BufferedReader(new InputStreamReader(System.in));
            ss = new ServerSocket(3000);
            System.out.println("Servidor escuchando");
            acceptCon = new ThreadAcceptCon(this,ss);
            acceptCon.start();

            System.out.println("Enviar a todos los clientes: (Enviar X para terminar la conexion):");
            str = userInput.readLine();
            while(str.compareTo("X")!=0){
                setChanged();
                notifyObservers(str);
                str = userInput.readLine();
            }
            setChanged();
            notifyObservers(str);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        try {
            ss.close();
            acceptCon.close();
            acceptCon.interrupt();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


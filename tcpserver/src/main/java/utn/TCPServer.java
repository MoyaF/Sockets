package utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class TCPServer extends Observable{

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
                sendToAllClients(str);
                str = userInput.readLine();
            }
            sendToAllClients(str);
            TimeUnit.SECONDS.sleep(1);
            userInput.close();
            ss.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void sendToAllClients(String str) {
        setChanged();
        notifyObservers(str);
    }


}


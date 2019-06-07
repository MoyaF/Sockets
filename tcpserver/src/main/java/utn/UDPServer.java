package utn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


public class UDPServer {
    public static void main(String args[]){


        Socket s;
        ServerSocket ss = null;
        try{
            ss = new ServerSocket(3000);
            System.out.println("Servidor escuchando");

            while(true){

                try{
                s= ss.accept();
                System.out.println("Conectado con "+s.getLocalAddress().getHostAddress());
                ServerThread st=new ServerThread(s);
                st.setName("Conexion con"+ s.getLocalAddress());
                st.start();
                }

                catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Se hizo mierda la conexion");

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


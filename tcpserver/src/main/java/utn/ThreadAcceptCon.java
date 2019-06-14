package utn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ThreadAcceptCon extends Thread{

    TCPServer ms;
    ServerSocket ss;
    Socket s;
    ThreadClient tc;

    public ThreadAcceptCon(TCPServer ms, ServerSocket ss)
    {
        super();
        this.ms = ms;
        this.ss = ss;
    }
    @Override
    public void run() {
        while(!ss.isClosed()){
            try{
                s= ss.accept();
                System.out.println("Conectado con "+s.getLocalAddress().getHostAddress());
                tc=new ThreadClient(s);
                ms.addObserver(tc);
                tc.setName("Conexion con"+ s.getLocalAddress());
                tc.start();
            }

            catch(SocketException e){
                System.out.println("El server dejo de escuchar");

            } catch (IOException e) {
                System.err.println("Error I/O");
            }
        }
    }

}

package utn;

import java.net.ServerSocket;
import java.net.Socket;

public class ThreadAcceptCon extends Thread{

    UDPServer ms;
    ServerSocket ss;
    Socket s;
    ThreadClient tc;

    public ThreadAcceptCon(UDPServer ms, ServerSocket ss)
    {
        super();
        this.ms = ms;
        this.ss = ss;
    }
    @Override
    public void run() {
        while(true){
            try{
                s= ss.accept();
                System.out.println("Conectado con "+s.getLocalAddress().getHostAddress());
                tc=new ThreadClient(s);
                ms.addObserver(tc);
                tc.setName("Conexion con"+ s.getLocalAddress());
                tc.start();
            }

            catch(Exception e){
                e.printStackTrace();
                System.out.println("Se hizo mierda la conexion");

            }
        }
    }

    public void close() {
        tc.interrupt();
    }
}

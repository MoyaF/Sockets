package utn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ThreadAcceptCon extends Thread{

    TCPServer ms;
    ServerSocket ss;
    Socket s;
    ThreadClient tc;
    List<ThreadClient> threadClients;

    public ThreadAcceptCon(TCPServer ms, ServerSocket ss)
    {
        super();
        this.ms = ms;
        this.ss = ss;
        this.threadClients = new ArrayList<>();
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
                threadClients.add(tc);
            }

            catch(SocketException e){
                System.out.println("Server socket cerrado con exito");

            } catch (IOException e) {
                System.err.println("Error I/O");
            }
        }
    }

    public void close(){
        for(ThreadClient t : threadClients)
        {
            t.close();
        }

    }

}

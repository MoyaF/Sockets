package utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

class ThreadClient extends Thread implements Observer {

    private String str;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private Socket s;


    public ThreadClient(Socket s){
        super();
        this.s=s;
        this.str = "";
        this.inputStream = null;
        this.outputStream = null;
        try{
            inputStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outputStream =new PrintWriter(s.getOutputStream());

        }catch(IOException e){
            System.err.println("I/O error en el thread " + Thread.currentThread().getName());
        }
    }

    public void run() {

        try {
            str = inputStream.readLine();
            while(str.compareTo("X")!=0){

                outputStream.println(str);
                outputStream.flush();
                System.out.println("Respuesta al cliente desde (thread "+this.getId()+") a (" + s.getLocalAddress() +") :  "+str);
                str = inputStream.readLine();

            }
        } catch (IOException e) {

            System.out.println("El cliente "+this.getId()+" se hizo mierda abruptamente");
        }
        catch(NullPointerException e){
            System.out.println("El cliente "+this.getId()+" cerro la conexion");
        }

        finally{
            try{
                System.out.println("Cerrando conexion con" + s.getLocalAddress());
                if (inputStream !=null){
                    System.out.println("Cerrando Input Stream...");
                    inputStream.close();
                    System.out.println("Input Stream Cerrado");
                }

                if(outputStream !=null){
                    System.out.println("Cerrando Output Stream...");
                    outputStream.close();
                    System.out.println("Socket Out Stream Cerrado");
                }
                if (s!=null){
                    System.out.println("Cerrando Socket...");
                    s.close();
                    System.out.println("Socket Cerrado");
                }

            }
            catch(IOException ie){
                System.out.println("Error cerrando Socket");
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof String) {
            String msg = (String) o;
            outputStream.println(msg);
            outputStream.flush();
            if(!msg.equals("X"))
                System.out.println("Mensaje a todos los clientes -> (" + s.getLocalAddress() + ") :  " + msg);
            else
                System.out.println("Cerrando conexion");
        }

        }
}
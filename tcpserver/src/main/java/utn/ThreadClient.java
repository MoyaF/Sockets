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
                str = "Hola soy el server, me enviaste esto; "+str;
                outputStream.println(str);
                outputStream.flush();
                System.out.println("Respuesta a (" + s.getLocalAddress() +") :  "+str);
                str = inputStream.readLine();

            }
        }
        catch(NullPointerException e){
            System.out.println("El cliente "+this.getId()+" cerro la conexion");
        }
        catch (IOException e) {

        }

        finally{
            this.close();

            }
        }


    public void close(){
        try{
            String str = " ip: " + s.getLocalAddress() + " id: " + this.getId();
            System.out.println("Cerrando conexion con" + str);
            if (s!=null){

                s.close();
                System.out.println("Conexion cerrada con " + str);

            }

        }
        catch(IOException ie){
            System.err.println("Error cerrando Socket");
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (!s.isClosed()) {
            if (o instanceof String) {
                String msg = (String) o;
                msg = "Soy el server: " + msg;
                outputStream.println(msg);
                outputStream.flush();
                if (!msg.equals("X")) {
                    System.out.println("Mensaje enviado a -> (" + s.getLocalAddress() + ") :  " + msg);
                }else{
                    System.out.println("Cerrando conexion");
                }
            }

        } else {

        }
    }
}
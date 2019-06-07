package utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerThread extends Thread{

    private String str;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private Socket s;
    private Boolean connected;


    public ServerThread(Socket s){
        this.s=s;
        this.str = null;
        this.inputStream = null;
        this.outputStream = null;
        this.connected = Boolean.TRUE;
    }

    public void run() {
        try{
            inputStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outputStream =new PrintWriter(s.getOutputStream());

        }catch(IOException e){
            System.err.println("I/O error en el thread " + Thread.currentThread().getName());
        }

        try {
            str= inputStream.readLine();
            while(str.compareTo("X")!=0){

                outputStream.println(str);
                outputStream.flush();
                System.out.println("Respuesta al cliente desde (thread "+this.getId()+") a (" + s.getLocalAddress() +") :  "+str);
                str= inputStream.readLine();
            }
        } catch (IOException e) {

            System.out.println("El cliente "+this.getId()+" se hizo mierda abruptamente");
        }
        catch(NullPointerException e){
            System.out.println("El cliente "+this.getId()+" se cerro");
        }

        finally{
            try{
                System.out.println("Cerrando conexion");
                if (inputStream !=null){
                    inputStream.close();
                    System.out.println("Socket Input Stream Cerrado");
                }

                if(outputStream !=null){
                    outputStream.close();
                    System.out.println("Socket Out Stream Cerrado");
                }
                if (s!=null){
                    s.close();
                    System.out.println("Socket Cerrado");
                }

            }
            catch(IOException ie){
                System.out.println("Error cerrando Socket");
            }
        }
    }

}
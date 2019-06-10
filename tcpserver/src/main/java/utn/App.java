package utn;



public class App {
    public static void main(String args[]) {

        UDPServer ms = new UDPServer();
        ms.startServer();
        ms.closeServer();

    }
}


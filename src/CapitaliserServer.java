import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CapitaliserServer {
    public static void main(String[] args) throws Exception{
        try(ServerSocket listener = new ServerSocket(59898)){
            System.out.println("The server is running...");
            ExecutorService pool = Executors.newFixedThreadPool(20); //a pool pf workers ready to work for us
            while(true){
                pool.execute(new Capitaliser(listener.accept())); //pass through the socket that we created
            }
        }
    }

    private static class Capitaliser implements Runnable{
        private Socket socket;

        Capitaliser(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //"true" is whether or not this is going to flush the info automatically after its sent
                while(in.hasNextLine()){
                    out.println(in.nextLine().toUpperCase());
                }
            } catch(Exception e){
                System.out.println("Error message");
            } finally {
                try{
                    socket.close();
                } catch(IOException e){

                }
            }
        }
    }
}

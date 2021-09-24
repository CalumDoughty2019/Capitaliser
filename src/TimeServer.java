import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeServer {
    public static void main(String[] args) throws Exception {
        try (ServerSocket listener = new ServerSocket(59898)) {
            System.out.println("The server is running...");
            ExecutorService pool = Executors.newFixedThreadPool(20); //a pool pf workers ready to work for us
            while (true) {
                pool.execute(new TimeServer.Time(listener.accept())); //pass through the socket that we created
            }
        }
    }

    private static class Time implements Runnable{
        private Socket socket;

        Time(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                LocalTime currentTime = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //"true" is whether or not this is going to flush the info automatically after its sent
                while(in.hasNextLine()){
                    out.println(currentTime.format(formatter));
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

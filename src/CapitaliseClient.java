import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CapitaliseClient {
    public static void main(String[] args) throws Exception{
        if(args.length != 1){
            System.out.println("Error message but in the client this time");
            return;
        }
        try(Socket socket = new Socket(args[0], 59898)){
            System.out.println("Enter lines of text then Ctrl+D or Ctrl+C to quit");
            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while(scanner.hasNextLine()){
                out.println(scanner.nextLine());
                System.out.println(in.nextLine());
            }
        }
    }
}

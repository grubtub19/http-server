import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer {

	static int port = 7090;
	static String website = "0";
	
	public static void main(String [] args) {
		Scanner s = new Scanner(System.in); 
		boolean valid = false;
		System.out.println("Which website shall it be? (0 or 1)..");
		while(!valid) {
			System.out.println("Which website shall it be? (0 or 1)");
			String input = s.nextLine();
			if(input.equals("0")) {
				website = "0";
				valid = true;
			} else if(input.equals("1")) {
				website = "1";
				valid = true;
			}
		}
		if(args.length > 1) {
			port = Integer.parseInt(args[0]);
		}
		//System.out.println("Http Server Started on port " + port);
		new HttpServer(port);
	}
	
	public HttpServer(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			while(true) {
				(new ResponseServer(ss.accept(), website)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

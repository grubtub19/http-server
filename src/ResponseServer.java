import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

public class ResponseServer extends Thread {
	private Socket sock;
	private PrintWriter writer;
	private BufferedReader reader;
	Files fileStream;
	private File file;
	private String methodLine;
	private int contentLength;
	private Pattern fileName = Pattern.compile(" /(.*?) ");
	private Pattern notImp = Pattern.compile("OPTIONS|PUT|DELETE|TRACE|CONNECT");
	private Pattern HTTPMethod = Pattern.compile("^([^ ]*?) ");
	private Pattern fileType = Pattern.compile("[.](.*?)$");
	private Pattern trueFileName = Pattern.compile("\\.([^.]*)$");
	private Matcher matcher;
	private String filePath = "public_html/";
	private String imagePath = "images/";
	public ResponseServer(Socket s, String website) {
		if(website.equals("1")) {
			filePath = "public_html2/";
		}
		sock = s;
		try {
			writer = new PrintWriter(sock.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch(IOException e) {
			System.out.println("ResponseServer catch statement");
		}
	}
	
	public void run() {
		
		try {
			write(read());

			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int read() throws IOException {
		return parseMethodLine();
	}
	
	private int parseMethodLine() {
		try {
			methodLine = reader.readLine();
			if(methodLine != null) {
				System.out.println("1: " + methodLine);
				int num = 2;
				String line = " ";
				String contentHeader = "Content-Length: ";
				while(reader.ready() && !(line = reader.readLine()).equals("")) {
					if (line.startsWith(contentHeader)) {
							contentLength = Integer.parseInt(line.substring(contentHeader.length()));
					}
					System.out.println(num + ": " +  line);
					num++;
				}
				
				matcher = HTTPMethod.matcher(methodLine);
				matcher.find();
				if(matcher.group(1).equals("GET")) {
					System.out.println("This is a get");
					getFile();
					if (!file.isFile()) {
						System.out.println("File not found");
						System.out.println(file.getAbsolutePath());
						return 404;
					}
				} else if (matcher.group(1).equals("POST")) {
					System.out.println("This is a post");
					//PostRequest post = new PostRequest(reader);
					
					recFile();
				} else {
					System.out.println("Neither: " + matcher.group(1));
					return 501;
				}
				return 200;
			} else {
				return 400;
			}
		} catch (IOException e) { 
			e.printStackTrace();
			return 400;
		}
	}
	
	private void recFile() {
		matcher = fileName.matcher(methodLine);
		matcher.find();
		filePath += matcher.group(1);
		System.out.println("PATHZ: " + filePath);
		return;
		
	}
	
	private File getFile() {
		matcher = fileName.matcher(methodLine);
		matcher.find();
		filePath += matcher.group(1);
		//filePath = filePath.replaceAll("(?<!(http:|https:))[//]+", "/");
		System.out.println("filePath = " + filePath);
		file = new File(filePath);
		System.out.println("ABSOLUTE PATH: " + file.getAbsolutePath());
		try {
			System.out.println("Canonical PATH: " + file.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("PATH: "+ file.getPath());
		matcher = trueFileName.matcher(methodLine);
		matcher.find();
		String extension = matcher.group(1);
		System.out.println("extension: " + extension);
		if (extension.equals("php")) {
			execPHP(filePath, true, null);
			file = new File(changeExtension(filePath, "html"));
		} else if (extension.equals("html")){
			execPHP(changeExtension(filePath,".html"), true, null);
		} else {
			System.out.println("DIRECTORY");
			File testFile = new File(filePath + ".php");
			if(testFile.isFile()) {
				execPHP(filePath + ".php", true, null);
				file = new File(filePath + ".html");
			} else {
				testFile = new File(filePath + "\\index.php");
				if(testFile.isFile()) {
					execPHP(filePath + "\\index.php", true, null);
					file = new File(filePath + "\\index.html");
				} else {
					System.out.println("not a file???");
				}
			}
		}
		return file;
	}
	
	private boolean execPHP(String path, boolean outputHTTP, String[] args) {
		try {
			System.out.println(changeExtension(path,"html"));
			FileOutputStream fos = new FileOutputStream(changeExtension(path,"html"));
			String parameters = "public_html\\php\\php.exe " + path + " " + changeExtension(path,"html") + " " + args;
			if(args != null) {
				for (String arg : args) {
					parameters += " " + arg;
				}
			}
			Process p = Runtime.getRuntime().exec(parameters);
			if(outputHTTP) {
				StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
				StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT", fos);
				errorGobbler.start();
				outputGobbler.start();
				System.out.println("waiting on batch file...");
				int exitVal = p.waitFor();
				System.out.println("Exit Value: " + exitVal);
				fos.flush();
				fos.close();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String changeExtension(String path, String extension) {
		matcher = trueFileName.matcher(path);
		matcher.find();
		path = path.substring(0, matcher.start());
		path += "." + extension;
		return path;
	}
	
	private boolean write(int i) { 
		if(i != 200) {
			System.out.println("i = " + i);
			writeError(i);
		} else if (file == null)
			writePost();
		else
			writeNormal(i);
		return true;
	}
	
	private void writeError(int i) {
		writeStatusLine(i);
		writer.print("\r\n");
		System.out.print("\r\n");
		writer.flush();
	}
	
	private void writePost() {
		
	}
	
	private void writeNormal(int i)  {
		writeStatusLine(i);
		writeServerLine();
		writeContentLength();
		writeContentType();
		writer.print("\r\n");
		System.out.print("\r\n");
		writer.flush();
		writeFile();
	
	}
	
	private void writeFile() {
		int b;
		try {
			sock.getOutputStream().write(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			System.out.println("Reading from File Failed");
			e.printStackTrace();
		}	
	}
	
	private void writeStatusLine(int i) {
		writer.write("HTTP/1.1 " + i + " " + reasonPhrase(i) +"\r\n" );
		System.out.print("HTTP/1.1 " + i + " " + reasonPhrase(i) +"\r\n" );
	}
	
	private void writeServerLine() {
		writer.write("Server: cs4333httpserver/1.0.2\r\n");
		System.out.print("Server: cs4333httpserver/1.0.2\r\n");
	}
	
	private void writeContentLength() {
		writer.write("Content-Length: " + file.length() + "\r\n");
		System.out.print("Content-Length: " + file.length() + "\r\n");
	}
	
	private void writeContentType() {
		writer.write("Content-Type: ");
		System.out.print("Content-Type: ");
		
		matcher = trueFileName.matcher(file.getAbsolutePath());
		if(matcher.find()) {
			writer.write(fileNameToContentType(matcher.group(1)) + "\r\n");
			System.out.print(fileNameToContentType(matcher.group(1)) + "\r\n");
		} else {
			System.out.println("Pattern Failure: Content-Type");
		}
	}
	
	private String fileNameToContentType(String name) {
		switch (name) {
		case "html":
		case "htm":
			return "text/html";
		case "gif":
			return "image/gif";
		case "jpg":
		case "jpeg":
			return "image/jpeg";
		case "pdf":
			return "application/pdf";
		case "txt":
			return "text/plain";
		case "png":
			return "image/png";
		case "css":
			return "text/css";
		case "js":
			return "application/javascript";
		case "woff":
			return "application/x-font-woff";
		case "woff2":
			return "application/font-woff2";
		case "dll":
			return "application/x-msdownload";
		default:
			return "error/" + name;
		}
	}
	
	private String reasonPhrase(int i) {
		switch (i) {
		case 200:
			return "OK";
		case 400:
			return "Bad Request";
		case 404:
			return "Not Found";
		case 501:
			return "Not Implemented";
		default:
			return "Error Message Not Supported";
		}
	}
}
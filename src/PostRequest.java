/*import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostRequest {
	
	public PostRequest(BufferedReader reader) {
		String line;
		Matcher matcher;
		String formData = "Content-Disposition: form-data; ";
		String typeHeader = "Content-Type: ";
		Pattern namePattern = Pattern.compile("name=\"([^\"; ]*?)\"");
		Pattern fileNamePattern = Pattern.compile("filename=\"([^\"; ]*?)\"");
		ImageForm image = new ImageForm();
		TextForm imageName = new TextForm();
		try {
			while(reader.ready() && (line = reader.readLine()) != null) {
				System.out.println(line);
				String name = "";
				String fileName = "";
				String type = "";
				byte[] body = {};
				if(line.startsWith(formData)) {
					//System.out.println("-----FORM DATA-----");
					//System.out.println(line);
					line = line.substring(formData.length());
					//System.out.println(line);
					matcher = namePattern.matcher(line);
					if(matcher.find()) {
						name = matcher.group(1);
						//System.out.println("Name: " + name);
					}
					matcher = fileNamePattern.matcher(line);
					if(matcher.find()) {
						fileName = matcher.group(1);
						//System.out.println("FileName: " + fileName);
					}
					line = reader.readLine();
					System.out.println(line);
					if(line.startsWith(typeHeader)) {
						line = line.substring(typeHeader.length());
						//System.out.println(line);
						type = line.substring(0);
						//System.out.println("Type: " + type);
						line = reader.readLine();
					} 
					if(line.equals("")) {
						while(!(line = reader.readLine()).startsWith("------WebKitFormBoundary")) {
							ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
							outputStream.write( line.getBytes(StandardCharsets.UTF_8) );
							outputStream.write( body );
							body = outputStream.toByteArray( );
							//System.out.println(line);
						}
						System.out.println(line);
					}
					
					if(type.equals("") && name.equals("name")) {
						System.out.println("Creating imageName");
						imageName = new TextForm(name, body);
					} else if(type.startsWith("image")) {
						System.out.println("Creating image");
						image = new ImageForm(name, fileName, type, body);
					}
				}
			}
			if(imageName.isPopulated() && image.isPopulated()) {
				System.out.print(imageName.getBody() + " END");
				File imageFile = new File(imageName.getBody() + ".png");
				PrintWriter writeToFile = new PrintWriter(imageFile);
				writeToFile.write(image.getBody());
				writeToFile.flush();
				System.out.println("File created at: " + imageFile.getAbsolutePath());
			} else {
				System.out.println("to few fields");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
*/
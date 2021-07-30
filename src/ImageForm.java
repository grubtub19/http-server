
public class ImageForm {
	private String name;
	private String fileName;
	private String type;
	private String body;
	private boolean isPop;
	public ImageForm() {
		isPop = false;
	}
	public ImageForm(String name, String fileName, String type, String body) {
		this.name = name;
		this.fileName = fileName;
		this.type = type;
		this.body = body;
		isPop = true;
	}
	public boolean isPopulated() {
		return isPop;
	}
	public String getFileName() {
		return fileName;
	}
	public String getBody() {
		return body;
	}
}

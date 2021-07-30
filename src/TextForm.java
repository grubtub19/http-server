
public class TextForm {


	private String name;
	private String body;
	private boolean isPop;
	public TextForm() {
		isPop = false;
	}
	public TextForm(String name, String body) {
		this.name = name;
		this.body = body;
		isPop = true;
	}

	public boolean isPopulated() {
		return isPop;
	}
	public String getName() {
		return name;
	}
	public String getBody() {
		return body;
	}
}

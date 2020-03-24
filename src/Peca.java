import java.util.ArrayList;

public class Peca {

	private String numero;
	ArrayList<Peca> fronteiras = new ArrayList<>();
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public ArrayList<Peca> getFronteiras() {
		return fronteiras;
	}
	public void setFronteiras(ArrayList<Peca> fronteiras) {
		this.fronteiras = fronteiras;
	}
	
}

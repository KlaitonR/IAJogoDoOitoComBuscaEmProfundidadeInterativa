
public class Fila {

	 Fronteira inicio, fim;
	 int tamanho;

	public Fila(){
		inicio = fim = null;
	}
	
	public boolean Vazio() {
		
		if(inicio == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public void incerir(Fronteira f) {
		
		if(inicio == null) {
			inicio = f;
			fim = f;
			fim.proximo=null;
			tamanho++;
			
		}else {
			inicio.proximo = f;
			fim = f;
			fim.proximo = null;
			tamanho++;
		}
	}
	
	public void retira() {
		
		inicio = inicio.proximo;
		tamanho--;
	}
	
	public int mostraTamanho() {
		
		int tamanho = 0;
		tamanho = this.tamanho;
		return tamanho;
	}
	
	public Fronteira inicioFila() {
		
		return inicio;
	}
	
	public Fronteira fimFila() {
		return fim;
	}
	
}

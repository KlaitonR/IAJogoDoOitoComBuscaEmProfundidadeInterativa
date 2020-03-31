public class Lista {
	
	Fronteira primeiro, ultimo, atual;
	
	
	public Lista() { 
		primeiro = ultimo = atual = null; 
	
		} 
	
	// Retorna um booleano indicando se a lista está vazia 
	public boolean estaVazio() {
		return primeiro == null; 
	}
	
	// Retorna o número de objetos da lista (tamanho) 
	public int comprimento() {
		int comp = 0; atual = primeiro; 
		while(atual != null) {
			comp++; atual = atual.proximo; 
			} 
		return comp; }

public Fronteira primeiroDaLista() {
	return primeiro;
}

public Fronteira ultimoDaLista() {
	return ultimo;
}

	//Insere um novo objeto como primeiro elemento
	public void inserePrimeiro(String tb[][]) { 
		Fronteira novo = new Fronteira(tb); 
			if (estaVazio()) { primeiro = novo; 
			ultimo = novo; atual = novo; 
			} else {
				novo.proximo = primeiro; primeiro = novo; } }

	
	//Insere um novo objeto como Último elemento 
	public void insereUltimo(String tb[][]) { 
		Fronteira novo = new Fronteira(tb);
		if (estaVazio()) {
			primeiro = novo;
			ultimo = novo;
			atual = novo; 
			}else {
				ultimo.proximo = novo; ultimo = novo; }
		}
	
	public void moveParaPosicao(int pos) {
		atual = primeiro;
		for(int i=0; i<pos; i++) {
			atual= atual.proximo;
		}
	}
	
	// Insere um novo objeto na posição indicada 
	public void insereNaPosicao(String tb[][], int pos) {
		Fronteira novo = new Fronteira(tb);
		moveParaPosicao(pos); 
		novo.proximo=atual.proximo; 
		atual.proximo=novo;
		}

	
	// Retorna a posição de um elemento na lista. Se não existir retorna um número negativo 
	public int buscaFronteira(String tb[][]) { 
		int cont = 0; 
		atual = primeiro;
		while(atual!= null && atual.tb[3][3] != tb[3][3]) { 
			atual = atual.proximo; 
			cont ++; 
			}
		if (atual != null) return cont; 
		return -1;
	}
	
	// Retorna o elemento na posição indicada
	public String fronteiraNaPosicao(int pos) { 
		moveParaPosicao(pos);
		return atual.tb[3][3];
		}
	
	// Remove o Último elemento da lista 
	public void removeUltimo() { 
		int pos; 
		pos=comprimento()-1;
		moveParaPosicao(pos); 
		atual.proximo=null; 
		ultimo = atual; 
	}
	
	// Remove da lista o elemento indicado
	public void removeFronteira(String tb[][]) { 
		int pos; 
		Fronteira temp; 
		pos= buscaFronteira(tb); 
		if (pos > -1) {
			temp=atual.proximo; 
			pos=pos-1; 
			moveParaPosicao(pos);
			atual.proximo=temp;
			} 
		}

	public void removeInicio() {
		primeiro = primeiro.proximo;
	}
	
	// Remove o elemento da posição indicada 
	public void removeNaPosicao(int pos) { 
		Fronteira temp;
		moveParaPosicao(pos); 
		temp=atual.proximo; 
		pos=pos-1;
		moveParaPosicao(pos); 
		atual.proximo=temp; 
		}
}

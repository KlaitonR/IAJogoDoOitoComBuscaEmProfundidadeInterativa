import java.util.ArrayList;

public class BuscaGulosa {
	
	Fronteira tbInicial;
	Fronteira tbFinal;
	ArrayList<Fronteira> fronteiras;
	ArrayList<Fronteira> nosDescobertos;
	
	public BuscaGulosa(ArrayList<Fronteira> fronteiras, ArrayList<Fronteira> nosDescobertos, Fronteira tbFinal, Fronteira tbInicial) {
		
		this.tbInicial = tbInicial;
		this.tbFinal = tbFinal;
		this.fronteiras = fronteiras;
		this.nosDescobertos = nosDescobertos;
	}
	
	public static boolean verificaFronteira(Fronteira fronteiraAtual, Fronteira fronteiraFinal ) {

		if(fronteiraAtual.toString().equals(fronteiraFinal.toString())) {
			return true;
		}else {
			return false;
		}	
	}
	
	public boolean expandeFronteira(Fronteira f, Fronteira frontFinal, ArrayList<Fronteira> fronteiras, ArrayList<Fronteira> nosDescobertos) {
		
		boolean verifica = false;
		
		int pI=0; //Para recuperar a posi��o da pe�a vazia
		int pJ=0; //Para recuperar a posi��o da pe�a vazia
		
		for(int i=0;i<=2;i++) {   // La�o para verificar a posi��o da pe�a vazia
			for(int j=0;j<=2;j++) {
				
				if(f.tabuleiro[i][j].equals(" ")) { // pega a posi��o da pe�a vazia
					pI = i;
					pJ = j;
				}
			}
		}
		
		//JOptionPane.showMessageDialog(null, "Posi��o i: " + pI + "Posi��o j: " + pJ + " - ");
		
				/* f.tabuleiro[i][j] tem a posi��o da pe�a atual, ex (i,j) = (1,1)
				 * se subtrairmos o contador i (i--), ficamos com (i--,j) = (0,1)
				 * Isso nos dar� a pe�a vizinha com a pe�a atual
				 * 
				 * Posi��o atual = PA (pe�a vazia na qual vai ser movida)
				 * 
				 * 	  0  1  2  
				 * 	 ----------
				 * 0 |  |  |  |   
				 * 	 ----------     PA = (1,1)
				 * 1 |  |PA|  |
				 * 	 ----------
				 * 2 |  |  |  |
				 * 	 ----------
				 * 
				 * Se diminuirmos i-- e o valor for maior que zero, significa que esta posi��o existe na matriz
				 * e a pe�a na POSI��O ANALISADA (XX) � vizinha de PA
				 * 
				 *    0  1  2  
				 * 	 ----------
				 * 0 |  |XX|  |   
				 * 	 ----------     PA = (1,1) => (i--,j) = (0,1)
				 * 1 |  |PA|  |     XX = (0,1) = vizinho de PA
				 * 	 ----------
				 * 2 |  |  |  |
				 * 	 ----------
				 * 
				 * Se somarmos i++ e o valor for menor que dois, significa que esta posi��o existe na matriz
				 * e a pe�a na POSI��O ANALISADA (XX) � vizinha de PA
				 * 
				 *    0  1  2  
				 * 	 ----------
				 * 0 |  |  |  |   
				 * 	 ----------     PA = (1,1) => (i++,j) = (2,1)
				 * 1 |  |PA|  |     XX = (2,1) = vizinho de PA
				 * 	 ----------
				 * 2 |  |XX|  |
				 * 	 ----------
				 * 
				 * O mesmo vale para a soma e subtra��o da posi��o j
				 * 
				 *    0  1  2  
				 * 	 ----------
				 * 0 |  |XX|  |   
				 * 	 ----------     
				 * 1 |XX|PA|XX|     Assim teremos todas as posi��es para onde a pe�a vazia pode se mover
				 * 	 ----------
				 * 2 |  |XX|  |
				 * 	 ----------
				 * 
				*/
	
			if((pI - 1)>= 0 && verifica == false) { //Verifica se existe uma posi��o em cima
				
				Fronteira novaFronteiraParaCima = new Fronteira(); //Cria nova fronteira
				
				String tbParaCima [][] =  new String [3][3];
				
				for(int i=0;i<=2;i++) {   // Atribui os valores antigos ao novo tabuleiro da noa fronteira
					for(int j=0;j<=2;j++) {
						tbParaCima[i][j] = f.tabuleiro[i][j];
					}
				}
				
				novaFronteiraParaCima.tabuleiro = tbParaCima; 
				
				tbParaCima[pI][pJ] = tbParaCima[pI - 1][pJ]; //Faz os movimentos do tabuleiro
				tbParaCima[pI - 1][pJ] = " ";
				
				//Verifica se o tabuleiro criado n�o � igual ao antecessor de seu antecessor (se n�o est� voltando a uma joga que j� foi feita)
				if(!novaFronteiraParaCima.toString().equals(f.antecessor.toString()) && 
				   !novaFronteiraParaCima.toString().equals(tbInicial.toString()) && 
				   verificaDuplaExpansao(novaFronteiraParaCima, nosDescobertos) == false) { //CASO SEJA IGUAL, ESSA FRONTEIRA J� EXISTE
					
					novaFronteiraParaCima.antecessor = f; //Guarda seu antecessor para recuperar o caminho
					novaFronteiraParaCima.profundidade = novaFronteiraParaCima.antecessor.profundidade + 1;
					novaFronteiraParaCima.custo = calculaCusto(f,frontFinal); // Calcula custo
					fronteiras.add(0,novaFronteiraParaCima);  //Adiciona a nova fronteira na lista de fronteiras
					mostraMovimento(novaFronteiraParaCima);
					
					verifica = verificaFronteira(novaFronteiraParaCima, frontFinal); 
					
					nosDescobertos.add(novaFronteiraParaCima);
					
					if(novaFronteiraParaCima.custo == 0) 
						verifica = true;
					
				} 
			}
				
			if((pJ - 1) >= 0 && verifica == false) { //Verifica se existe uma posi��o � Esquerda
				
				Fronteira novaFronteiraEsquerda = new Fronteira(); 
				
				String tbEsquerda [][] =  new String [3][3];
				
				for(int i=0;i<=2;i++) {   
					for(int j=0;j<=2;j++) {
						tbEsquerda[i][j] = f.tabuleiro[i][j];
						}
				}
				
				novaFronteiraEsquerda.tabuleiro = tbEsquerda; 
				
				tbEsquerda[pI][pJ] = tbEsquerda[pI][pJ - 1]; 
				tbEsquerda[pI][pJ - 1] = " ";
				
				if(!novaFronteiraEsquerda.toString().equals(f.antecessor.toString()) && 
				   !novaFronteiraEsquerda.toString().equals(tbInicial.toString()) && 
				   verificaDuplaExpansao(novaFronteiraEsquerda, nosDescobertos) == false) { 
			
					novaFronteiraEsquerda.antecessor = f;
					novaFronteiraEsquerda.profundidade = novaFronteiraEsquerda.antecessor.profundidade + 1;
					novaFronteiraEsquerda.custo = calculaCusto(f, frontFinal);			
					fronteiras.add(0,novaFronteiraEsquerda);
					mostraMovimento(novaFronteiraEsquerda);
					
					verifica = verificaFronteira(novaFronteiraEsquerda, frontFinal);
					
					nosDescobertos.add(novaFronteiraEsquerda);
					
					if(novaFronteiraEsquerda.custo == 0) 
						verifica = true;
					
				}
					
			}
				
			if((pI + 1) <= 2 && verifica == false) {  //Verifica se existe uma posi��o para baixo
				
				Fronteira novaFronteiraParaBaixo = new Fronteira();

				String tbParaBaixo [][] =  new String [3][3];
				
				for(int i=0;i<=2;i++) {  
					for(int j=0;j<=2;j++) {
						tbParaBaixo[i][j] = f.tabuleiro[i][j];
					}
				}
				
				novaFronteiraParaBaixo.tabuleiro = tbParaBaixo; 
				
				tbParaBaixo[pI][pJ] = tbParaBaixo[pI + 1][pJ]; 
				tbParaBaixo[pI + 1][pJ] = " ";
				
				if(!novaFronteiraParaBaixo.toString().equals(f.antecessor.toString()) &&
				   !novaFronteiraParaBaixo.toString().equals(tbInicial.toString()) &&
				   verificaDuplaExpansao(novaFronteiraParaBaixo, nosDescobertos) == false) { 
		
					novaFronteiraParaBaixo.antecessor = f;
					novaFronteiraParaBaixo.profundidade = novaFronteiraParaBaixo.antecessor.profundidade + 1;
					novaFronteiraParaBaixo.custo = calculaCusto(f, frontFinal);
					fronteiras.add(0,novaFronteiraParaBaixo);  
					mostraMovimento(novaFronteiraParaBaixo);
					
					verifica = verificaFronteira(novaFronteiraParaBaixo, frontFinal);
					
					nosDescobertos.add(novaFronteiraParaBaixo);
					
					if(novaFronteiraParaBaixo.custo == 0) 
						verifica = true;
					
				}		
			}
				
			if((pJ + 1) <= 2 && verifica == false) { //Verifica se existe uma posi��o � Direita
				
				Fronteira novaFronteiraDireita = new Fronteira(); 
				
				String tbDireita [][] =  new String [3][3];
				
				for(int i=0;i<=2;i++) {   
					for(int j=0;j<=2;j++) {
						tbDireita[i][j] = f.tabuleiro[i][j];
						}
				}
				
				novaFronteiraDireita.tabuleiro = tbDireita; 
				
				tbDireita[pI][pJ] = tbDireita[pI][pJ + 1]; 
				tbDireita[pI][pJ + 1] = " ";
				
				if(!novaFronteiraDireita.toString().equals(f.antecessor.toString()) &&
				   !novaFronteiraDireita.toString().equals(tbInicial.toString()) &&
				   verificaDuplaExpansao(novaFronteiraDireita, nosDescobertos) == false) { 
				
					novaFronteiraDireita.antecessor = f;
					novaFronteiraDireita.profundidade = novaFronteiraDireita.antecessor.profundidade + 1;
					novaFronteiraDireita.custo = calculaCusto(f, frontFinal);
					fronteiras.add(0,novaFronteiraDireita);
					mostraMovimento(novaFronteiraDireita);
					verifica = verificaFronteira(novaFronteiraDireita, frontFinal);
					
					nosDescobertos.add(novaFronteiraDireita);
					
					if(novaFronteiraDireita.custo == 0) 
						verifica = true;
					
				}	
			}
			
	fronteiras.remove(f); //remove o n� expandido
			
	return verifica;
		
}
	
	public Fronteira menorCusto(ArrayList<Fronteira> fronteiraAtual) {
		
		int menor = 999999999; 
		Fronteira fronteiraMenor = new Fronteira(); 
		
		for(int i=0; i<fronteiraAtual.size();i++) {
			if(fronteiraAtual.get(i).custo<menor) {
				menor = fronteiraAtual.get(i).custo;
				fronteiraMenor = fronteiraAtual.get(i);
			}
		}
		
		return fronteiraMenor;
		
	}

	public static int calculaCusto(Fronteira atual, Fronteira resultado){
		
		int custo = 0;
		
		
		for(int i=0;i<3;i++) {  // percorre as posi��es do tabuleiro atual
			for(int j=0;j<3;j++) {
				
				for(int k=0;k<3;k++) {  // percorre as posi��es do tabuleiro resultado
					for(int l =0;l<3;l++) {
					
						if(!atual.tabuleiro[i][j].equals(resultado.tabuleiro[k][l]) && (!atual.tabuleiro[i][j].equals(" ") && !atual.tabuleiro[k][l].equals(" "))) { // verifica se ambas as posi��es s�o diferente e n�o vazias
							if(i!=k && j!=l) { // se estiver em uma posi��o diferente, significa que ter� uma custo para se mover e chegar ao resultado
								
								custo++; //soma  1 ao custo dos movimentos (soma o numero de pe�a fora do lugar)
								
							}
						}
				
					}
				}
			}
		}
		
		return custo;
	}
	
public static void mostraMovimento(Fronteira fNew) {
		
		String tabuleiroNew = "-------------\n";
		
		for(int i = 0; i<=2; i++) {
			tabuleiroNew += "| ";
			for(int j = 0; j<=2; j++) {
				tabuleiroNew += fNew.tabuleiro[i][j] + " | ";
			}
			
			tabuleiroNew += "\n-------------\n";
		}
		
		System.out.print(tabuleiroNew);
	}

	public static boolean verificaDuplaExpansao(Fronteira FronteiraExpandida, ArrayList<Fronteira> expandidos) { //verificar se um novo n� j� existe
	
	boolean verifica = false;
	
		for(int j=0;j<expandidos.size();j++) {
			if(FronteiraExpandida.toString().equals(expandidos.get(j).toString())) {
				verifica = true;
			}
	}
	
	return verifica;
	
	}

}

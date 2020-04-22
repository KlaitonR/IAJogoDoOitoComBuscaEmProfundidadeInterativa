import java.util.ArrayList;
//import javax.swing.JOptionPane;

public class BuscaEmAprofundamentoInterativo {

	Fronteira tbInicial;
	Fronteira tbFinal;
	ArrayList<Fronteira> fronteiraAtual;
	
	
	public BuscaEmAprofundamentoInterativo(ArrayList<Fronteira> fronteiraAtual , Fronteira tbFinal, Fronteira tbInicial) {
		
		this.tbInicial = tbInicial;
		this.tbFinal = tbFinal;
		this.fronteiraAtual = fronteiraAtual;
		
	}
	
	public static boolean verificaFronteira(Fronteira fronteiraAtual, Fronteira fronteiraFinal ) {

		if(fronteiraAtual.toString().equals(fronteiraFinal.toString())) {
			return true;
		}else {
			return false;
		}	
	}
	
//	public static boolean verificaVisitado(Fronteira fronteiraAtual, ArrayList<Fronteira> fronteiras, ArrayList<Fronteira> visitados) {
//		
//		boolean verifica = false;
//		
//		for(int i=0;i<visitados.size();i++) {
//			if(fronteiraAtual.toString().equals(visitados.get(i).toString()))
//				verifica = true;
//		}
//		
//		return verifica;
//	}
	
	public boolean expandeFronteira(Fronteira f, Fronteira frontFinal, ArrayList<Fronteira> fronteiras) {
			
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
				if(!novaFronteiraParaCima.toString().equals(f.antecessor.toString()) && !novaFronteiraParaCima.toString().equals(tbInicial.toString())) { //CASO SEJA IGUAL, ESSA FRONTEIRA J� EXISTE
						
					novaFronteiraParaCima.antecessor = f; //Guarda seu antecessor para recuperar o caminho
					novaFronteiraParaCima.profundidade = f.profundidade + 1; //Guarda a profundidade
					fronteiras.add(0,novaFronteiraParaCima);  //Adiciona a nova fronteira na lista de fronteiras
					mostraMovimento(novaFronteiraParaCima);
					verifica = verificaFronteira(novaFronteiraParaCima, frontFinal); // verifica se � o resultado
							
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
			
				if(!novaFronteiraEsquerda.toString().equals(f.antecessor.toString()) && !novaFronteiraEsquerda.toString().equals(tbInicial.toString())) { 
				
					novaFronteiraEsquerda.antecessor = f;
					novaFronteiraEsquerda.profundidade = f.profundidade + 1;
					fronteiras.add(0,novaFronteiraEsquerda);
					mostraMovimento(novaFronteiraEsquerda);
					verifica = verificaFronteira(novaFronteiraEsquerda, frontFinal);
		
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
						
				if(!novaFronteiraParaBaixo.toString().equals(f.antecessor.toString()) && !novaFronteiraParaBaixo.toString().equals(tbInicial.toString())) { 
			
					novaFronteiraParaBaixo.antecessor = f;
					novaFronteiraParaBaixo.profundidade = f.profundidade + 1;
					fronteiras.add(0,novaFronteiraParaBaixo);  
					mostraMovimento(novaFronteiraParaBaixo);
					verifica = verificaFronteira(novaFronteiraParaBaixo, frontFinal);
							
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
					
				if(!novaFronteiraDireita.toString().equals(f.antecessor.toString()) && !novaFronteiraDireita.toString().equals(tbInicial.toString())) { 
					
					novaFronteiraDireita.antecessor = f;
					novaFronteiraDireita.profundidade = f.profundidade + 1;
					fronteiras.add(0,novaFronteiraDireita);
					mostraMovimento(novaFronteiraDireita);
					verifica = verificaFronteira(novaFronteiraDireita, frontFinal);
						
				}
						
			}
				
		fronteiras.remove(f); //remove o n� expandido
				
		return verifica;
			
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
	
}
import java.util.ArrayList;

public class BuscaA {
	Fronteira tbInicial;
	Fronteira tbFinal;
	ArrayList<Fronteira> fronteiraAtual;
	
	public BuscaA(ArrayList<Fronteira> fronteiraAtual, Fronteira tbFinal, Fronteira tbInicial) {
		
		this.tbInicial = tbInicial;
		this.tbFinal = tbFinal;
		this.fronteiraAtual = fronteiraAtual;
		
	}
	
//	public static boolean verificaFronteira(Fronteira fronteiraAtual, Fronteira fronteiraFinal ) {
//
//		if(fronteiraAtual.toString().equals(fronteiraFinal.toString())) {
//			return true;
//		}else {
//			return false;
//		}	
//	}
	
	public boolean expandeFronteira(Fronteira f, Fronteira frontFinal, ArrayList<Fronteira> fronteiras) {
		
		f.expansao = true; //Caso n�o tenha sido expandido, ele ser� expandido, setando agora a variavel expansao como verdadeira
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
				
				//JOptionPane.showMessageDialog(null, novaFronteiraParaCima.toString() +"\n"+ f.antecessor.toString());
				
				//Verifica se o tabuleiro criado n�o � igual ao antecessor de seu antecessor (se n�o est� voltando a uma joga que j� foi feita)
				if(!novaFronteiraParaCima.toString().equals(f.antecessor.toString()) && !novaFronteiraParaCima.toString().equals(tbInicial.toString())) { //CASO SEJA IGUAL, ESSA FRONTEIRA J� EXISTE
					
					novaFronteiraParaCima.antecessor = f; //Guarda seu antecessor para recuperar o caminho
					novaFronteiraParaCima.profundidade = novaFronteiraParaCima.antecessor.profundidade + 1;
					novaFronteiraParaCima.custo = calculaCusto(f,frontFinal); // Calcula custo
					
					fronteiras.add(0,novaFronteiraParaCima);  //Adiciona a nova fronteira na lista de fronteiras
					//mostraMovimento(novaFronteiraParaCima);
					//JOptionPane.showMessageDialog(null, "prof nova: " + novaFronteiraParaCima.profundidade + "prof antiga: " + f.profundidade);
					//verifica = verificaFronteira(novaFronteiraParaCima, frontFinal);
					
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
				
				//JOptionPane.showMessageDialog(null, novaFronteiraEsquerda.toString() +"\n"+ f.antecessor.toString());
				
				if(!novaFronteiraEsquerda.toString().equals(f.antecessor.toString()) && !novaFronteiraEsquerda.toString().equals(tbInicial.toString())) { 
			
					novaFronteiraEsquerda.antecessor = f;
					novaFronteiraEsquerda.profundidade = novaFronteiraEsquerda.antecessor.profundidade + 1;
					novaFronteiraEsquerda.custo = calculaCusto(f, frontFinal);
					
					//f.fronteiras.add(novaFronteiraEsquerda);
					fronteiras.add(0,novaFronteiraEsquerda);
					//mostraMovimento(novaFronteiraEsquerda);
					//	JOptionPane.showMessageDialog(null, "prof nova: " + novaFronteiraEsquerda.profundidade + "prof antiga: " + f.profundidade);
		
					//verifica = verificaFronteira(novaFronteiraEsquerda, frontFinal);
					
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
				
				
				//JOptionPane.showMessageDialog(null, novaFronteiraParaBaixo.toString() +"\n"+ f.antecessor.toString());
				
				if(!novaFronteiraParaBaixo.toString().equals(f.antecessor.toString()) && !novaFronteiraParaBaixo.toString().equals(tbInicial.toString())) { 
		
					novaFronteiraParaBaixo.antecessor = f;
					novaFronteiraParaBaixo.profundidade = novaFronteiraParaBaixo.antecessor.profundidade + 1;
					novaFronteiraParaBaixo.custo = calculaCusto(f, frontFinal);
					
					//f.fronteiras.add(novaFronteiraParaBaixo);
					fronteiras.add(0,novaFronteiraParaBaixo);  
					//mostraMovimento(novaFronteiraParaBaixo);
					//	JOptionPane.showMessageDialog(null, "prof nova: " + novaFronteiraParaBaixo.profundidade + "prof antiga: " + f.profundidade);
					//verifica = verificaFronteira(novaFronteiraParaBaixo, frontFinal);
					
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
				
				//JOptionPane.showMessageDialog(null, novaFronteiraDireita.toString() +"\n"+ f.antecessor.toString());
				
				if(!novaFronteiraDireita.toString().equals(f.antecessor.toString()) && !novaFronteiraDireita.toString().equals(tbInicial.toString())) { 
				
					novaFronteiraDireita.antecessor = f;
					novaFronteiraDireita.profundidade = novaFronteiraDireita.antecessor.profundidade + 1;
					novaFronteiraDireita.custo = calculaCusto(f, frontFinal);
					
					//f.fronteiras.add(novaFronteiraDireita);
					fronteiras.add(0,novaFronteiraDireita);
					//mostraMovimento(novaFronteiraDireita);
					//	JOptionPane.showMessageDialog(null, "prof nova: " + novaFronteiraDireita.profundidade + "prof antiga: " + f.profundidade);
					//verifica = verificaFronteira(novaFronteiraDireita, frontFinal);
					
					if(novaFronteiraDireita.custo == 0) 
						verifica = true;
					
				}
					
			}
			
	fronteiras.remove(f); //remove o n� expandido
			//JOptionPane.showMessageDialog(null, "FIM da expansao");
			
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
		
		int custo = 0, iAux, jAux;
		
		
		for(int i=0;i<3;i++) {  // percorre as posi��es do tabuleiro atual
			for(int j=0;j<3;j++) {
				
				for(int k=0;k<3;k++) {  // percorre as posi��es do tabuleiro resultado
					for(int l =0;l<3;l++) {
					
						if(atual.tabuleiro[i][j].equals(resultado.tabuleiro[k][l]) && !atual.tabuleiro[i][j].equals(" ") && !atual.tabuleiro[k][l].equals(" ")) { // verifica se achou a posi��o da pe�a (exeto para a posi��o sem n�mero)
							if(i!=k || j!=l) { // se estiver em uma posi��o diferente, significa que ter� uma custo para se mover e chegar ao resultado
								
								iAux = i-k; //calcula a diferen�a (entre as posi��es de linha) de casas entre as pe�as do tabuleiro
								if(iAux<0) //Se menor que 0, multiplica por -1 para tornar a diferen�a positiva, para se somar ao custo
									iAux = iAux*-1;
								jAux = j-l; //calcula a diferen�a (entre as posi��es de coluna) de casas entre as pe�as do tabuleiro
								if(jAux<0)//Se menor que 0, multiplica por -1 para tornar a diferen�a positiva, para se somar ao custo
									jAux = jAux*-1;
								
								custo += iAux + jAux; //soma o custo dos movimentos
								
							}
						}
				
					}
				}
			}
		}
		
		return custo;
	}

}

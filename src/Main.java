import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		
		Tabuleiro tbInicial = new Tabuleiro();
		Tabuleiro tbFinal = new Tabuleiro();
		Lista fronteiras = new Lista();
		
		
		// Dados j� inseridos para teste
		tbInicial.tabuleiro[0][0] = "1";
		tbInicial.tabuleiro[1][0] = "2";
		tbInicial.tabuleiro[2][0] = "3";
		tbInicial.tabuleiro[0][1] = "4";
		tbInicial.tabuleiro[0][2] = "5";
		tbInicial.tabuleiro[1][1] = "6";
		tbInicial.tabuleiro[2][2] = "7";
		tbInicial.tabuleiro[1][2] = "8";
		tbInicial.tabuleiro[2][1] = " ";
		
		tbFinal.tabuleiro[0][0] = "1";
		tbFinal.tabuleiro[1][0] = "2";
		tbFinal.tabuleiro[2][0] = "3";
		tbFinal.tabuleiro[0][1] = "4";
		tbFinal.tabuleiro[0][2] = "5";
		tbFinal.tabuleiro[1][1] = "6";
		tbFinal.tabuleiro[2][2] = "7";
		tbFinal.tabuleiro[1][2] = "8";
		tbFinal.tabuleiro[2][1] = " ";
		
		/*
		JOptionPane.showMessageDialog(null, "*** Preencha o Tabuleiro inicial ***");
		preencheTabuleiros(tbInicial);
		JOptionPane.showMessageDialog(null, "*** Preencha o Tabuleiro final ***");
		preencheTabuleiros(tbFinal);
		*/
		int op = 0;
		
		do {
		
			switch (op) {
				
				case 1: //Busca em profundidade interativa
					
					expandeFronteiras(tbInicial, fronteiras );
					
					while(fronteiras.estaVazio() == false) { //enquanto a lista de fronteira estiver cheia
						
						Tabuleiro tbVerificacao = new Tabuleiro();
						tbVerificacao = verificaResultado(tbFinal, fronteiras);
						
						if(tbVerificacao == null) { //significa que ainda n�o chegou no rsultado
							
							Tabuleiro t = new Tabuleiro();
							t.tabuleiro =  fronteiras.primeiroDaLista().tb; // Pega a primeira fronteira da lista
							expandeFronteiras(t, fronteiras); //expande essa fronteira em novas fronteiras
							
							fronteiras.removeInicio(); //remove a fronteira j� expandida
							
						}else { //Mostra o tabuleiro final
							
							String resultado = "-------------\n";
							
							for(int i = 0; i<=2; i++) {
								resultado += "| ";
								for(int j = 0; j<=2; j++) {
									resultado+= tbVerificacao.tabuleiro[i][j] + " | ";
								}
								
								resultado += "\n-------------\n";
							}
							
							JOptionPane.showMessageDialog(null, "O puzzle foi reslvido" + resultado+ "\n" + tbFinal);
						}
					}
					
					break;
					
				case 2: //Busca gulosa
					
					break;
				
				case 3: //Busca A*
				
					break;
					
				}
			
			op = retornaMenu();
	
		}while(op!=4);
		
		
	}

	public static int retornaMenu() { //menu
		
		String menu ="Qual o tipo de busca? \n"
					+ "1 - Busca em profundidade interativa. \n"
					+ "2 - Busca gulosa. \n"
					+ "3 - Busca A*. \n"
					+ "4 - SAIR";
				
	return Integer.parseInt(JOptionPane.showInputDialog(menu));
	
	}
	
	public static void preencheTabuleiros(Tabuleiro tb) { //preenche os tabuleiros no estado inicial e final (resultado que se quer chegar)
		
	int op=0;
	String tab = "-------------\n";
	String peca = "";
	boolean verf = false; // controle para inserir apenas um espa�o vazio no tabuleiro
	
		for(int i = 0; i<=2; i++) {
			tab += "| ";
			for(int j = 0; j<=2; j++) {
				op = Integer.parseInt(JOptionPane.showInputDialog("1 - Inserir um n�mero para a pr�xima casa do tabuleiro \n"
					+ "2 - Inserir espa�o vazio no tabuleiro"));
			
				if(op==1) { // verifica se o jogador quer colocar um n�mero ou um espa�o vazio
					peca = JOptionPane.showInputDialog("Digite o n�mero."); 
					tb.tabuleiro[i][j] = peca; //preenche as casas do tabuleiro com a pe�a
					
					tab+= peca + " | ";
				}else {
					if(verf ==false) {
						peca  = "  ";
						tb.tabuleiro[i][j] = peca; //preenche a casa do tabuleiro com espa�o vazio
						tab+= peca + " | ";
						verf = true;
					}else {
						JOptionPane.showMessageDialog(null, "Voc� s� pode inserir um espa�o vazio no tabuleiro!");
						j--;
					}
				}

				JOptionPane.showMessageDialog(null, tab); //mostar o tabuleiro a cada n�mero inserido
			}
			
			tab+= "\n-------------\n";
		}
	}
	
	public static void expandeFronteiras(Tabuleiro tb, Lista fronteiras) {
		
		int pI=0, pJ=0;
		Fronteira novaFronteira = new Fronteira(tb.tabuleiro); //Cria nova fronteira
		
		for(int i=0;i<=2;i++) {   // La�o para verificar a posi��o da pe�a vazia
			for(int j=0;j<=2;j++) {
				
				if(tb.tabuleiro[i][j].equals(" ")) { // pega a posi��o da pe�a vazia
					pI = i;
					pJ = j;
				}
			}
		}
		
				/* tb.tabuleiro[i][j] tem a posi��o da pe�a atual, ex (i,j) = (1,1)
				 * se subtrairmos o contador i (i--), ficamos com (i--,j) = (0,1)
				 * Isso nos dar� as pe�as que fazem fronteira com a pe�a atual, na vertical e horizontal
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
				 * e a pe�a na POSI��O ANALISADA (XX) faz fronteira com PA
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
				 * Se somarmos i++ e o valor for menor que dois, significa quue esta posi��o existe na matriz
				 * e a pe�a na POSI��O ANALISADA (XX) faz fronteira com PA
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
				
				
				if(pI-- > 0) { //Verifica se existe uma posi��o em cima 
					
					novaFronteira.tb[pI][pJ] = tb.tabuleiro[pI--][pJ]; //pega o valor do tabuleiro predecessor, e coloca na pe�a vazia
					novaFronteira.tb[pI--][pJ] = " "; //Troca o valor pela pe�a vazia, ou seja, move a pe�a vazia para cima e a com valor para baixo
					
					fronteiras.inserePrimeiro(novaFronteira.tb);  //Adiciona a nova fronteira na lista de fronteiras
					
					/*    0  1  2  
					 * 	 ----------
					 * 0 |1 |7 |4 |   
					 * 	 ----------     
					 * 1 |5 |  |2 |     move a pe�a vazia para cima e a com valor para baixo 
					 * 	 ----------
					 * 2 |3 |8 |6 | 
					 * 	 ----------
					 *
					/*    0  1  2  
					 * 	 ----------
					 * 0 |1 |  |4 |   
					 * 	 ----------     Pe�a movida foi a 7
					 * 1 |5 |7 |2 |     
					 * 	 ----------
					 * 2 |3 |8 |6 | 
					 * 	 ----------
					*/
				}
					
				 
				if(pJ-- > 0) { //Verifica se existe uma posi��o � Esquerda
						
					novaFronteira.tb[pI][pJ] = tb.tabuleiro[pI][pJ--]; 
					novaFronteira.tb[pI][pJ--] = " ";
					
					fronteiras.inserePrimeiro(novaFronteira.tb);  //Adiciona a nova fronteira na lista de fronteiras
					
				}
						
					
				if(pI++ < 2) {  //Verifica se existe uma posi��o para baixo
					
					novaFronteira.tb[pI][pJ] = tb.tabuleiro[pI++][pJ]; 
					novaFronteira.tb[pI++][pJ] = " ";
					
					fronteiras.inserePrimeiro(novaFronteira.tb);  //Adiciona a nova fronteira na lista de fronteiras
							
				}
							
				if(pJ++ < 2) { //Verifica se existe uma posi��o � Direita
					
					novaFronteira.tb[pI][pJ] = tb.tabuleiro[pI][pJ++]; 
					novaFronteira.tb[pI][pJ++] = " ";
					
					fronteiras.inserePrimeiro(novaFronteira.tb);  //Adiciona a nova fronteira na lista de fronteiras
								
				}
		
	}
	
	public static Tabuleiro verificaResultado(Tabuleiro tbFinal, Lista fronteiras) {
		
		Tabuleiro resultado = new Tabuleiro();
		int pos = fronteiras.buscaFronteira(tbFinal.tabuleiro);
		
		if(pos>0) {
			
			resultado.tabuleiro = fronteiras.atual.tb; 
			
			return resultado;
			
		}else {
			return null;
		}
	}
	
}

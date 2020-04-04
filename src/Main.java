import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		
		String tbi[][] = new String [3][3];
		String tbf[][] = new String [3][3];
		
		// Dados j� inseridos para teste
				tbi[0][0] = "1";
				tbi[1][0] = "2";
				tbi[2][0] = "3";
				tbi[0][1] = "4";
				tbi[0][2] = "5";
				tbi[1][1] = " ";
				tbi[2][2] = "7";
				tbi[1][2] = "8";
				tbi[2][1] = "6";
				
				tbf[0][0] = "4";
				tbf[1][0] = "5";
				tbf[2][0] = "3";
				tbf[0][1] = "1";
				tbf[0][2] = "2";
				tbf[1][1] = "6";
				tbf[2][2] = "7";
				tbf[1][2] = "8";
				tbf[2][1] = " ";
		
		Fronteira tbInicial = new Fronteira(tbi);
		Fronteira tbFinal = new Fronteira(tbf);
		
		Lista fronteiras = new Lista();

		/*
		JOptionPane.showMessageDialog(null, "*** Preencha o Tabuleiro inicial ***");
		preencheTabuleiros(fi);
		JOptionPane.showMessageDialog(null, "*** Preencha o Tabuleiro final ***");
		preencheTabuleiros(ff);
		*/
		int op = 0;
		
		do {
		
			switch (op) {
				
				case 1: //Busca em profundidade interativa
					
					expandeFronteiras(tbInicial, fronteiras);
	
					//JOptionPane.showMessageDialog(null, fronteiras.primeiroDaLista());
					
					boolean ver = verificaResultado(tbFinal, fronteiras);
					
					if(ver == false) { //significa que ainda n�o chegou no resultado
					
						while(fronteiras.estaVazio() == false) { //enquanto a lista de fronteira estiver cheia
							
							expandeFronteiras(fronteiras.primeiroDaLista(), fronteiras); //expande essa fronteira em novas fronteiras
							fronteiras.removeInicio(); //remove a fronteira j� expandida
					
						}
							
					}else { //Mostra o tabuleiro final
							
						String resultado = "-------------\n";
							
						for(int i = 0; i<=2; i++) {
							resultado += "| ";
							for(int j = 0; j<=2; j++) {
								resultado+= tbFinal.tb[i][j] + " | ";
							}
								
							resultado += "\n-------------\n";
						}
							
						JOptionPane.showMessageDialog(null, "O puzzle foi reslvido" + resultado+ "\n" + tbFinal);
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

	static public  int retornaMenu() { //menu
		
		String menu ="Qual o tipo de busca? \n"
					+ "1 - Busca em profundidade interativa. \n"
					+ "2 - Busca gulosa. \n"
					+ "3 - Busca A*. \n"
					+ "4 - SAIR";
				
	return Integer.parseInt(JOptionPane.showInputDialog(menu));
	
	}
	
	static public void preencheTabuleiros(Fronteira f) { //preenche os tabuleiros no estado inicial e final (resultado que se quer chegar)
		
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
					f.tb[i][j] = peca; //preenche as casas do tabuleiro com a pe�a
					
					tab+= peca + " | ";
				}else {
					if(verf == false) {
						peca  = "  ";
						f.tb[i][j] = peca; //preenche a casa do tabuleiro com espa�o vazio
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
	
	static public void expandeFronteiras(Fronteira f, Lista fronteiras) {
		
		int pI=0; 
		int pJ=0;
		int excep=0;
		//int c = 0;
		
		for(int i=0;i<=2;i++) {   // La�o para verificar a posi��o da pe�a vazia
			for(int j=0;j<=2;j++) {
				
				if(f.tb[i][j].equals(" ")) { // pega a posi��o da pe�a vazia
					pI = i;
					pJ = j;
					//c = i;
				}
			}
		}
		
		//JOptionPane.showMessageDialog(null, "Posi��o i: " + pI + "Posi��o j: " + pJ + " - "+ c);
		
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
				
			excep = pI - 1;
			if(excep >= 0) { //Verifica se existe uma posi��o em cima
				
				String tb [][] =  new String [3][3];
				
				for(int i=0;i<=2;i++) {   // La�o para verificar a posi��o da pe�a vazia
					for(int j=0;j<=2;j++) {
						tb[i][j] = f.tb[i][j];
						}
				}
				
				Fronteira novaFronteira = new Fronteira(tb); //Cria nova fronteira
				novaFronteira.tb[pI][pJ] = f.tb[excep][pJ]; //pega o valor do tabuleiro predecessor, e coloca na pe�a vazia
				novaFronteira.tb[excep][pJ] = " "; //Troca o valor pela pe�a vazia, ou seja, move a pe�a vazia para cima e a com valor para baixo
				
				fronteiras.inserePrimeiro(novaFronteira);  //Adiciona a nova fronteira na lista de fronteiras
					
				//mostraMovimento(novaFronteira,f);
			}
					/*    0  1  2  
					 * 	 ----------
					 * 0 |1 |7 |4 |   
					 * 	 ----------     
					 * 1 |5 |  |2 |     move a pe�a vazia para cima e a com valor para baixo 
					 * 	 ----------
					 * 2 |3 |8 |6 | 
					 * 	 ---------
					 *
					 *    0  1  2  
					 * 	 ----------
					 * 0 |1 |  |4 |   
					 * 	 ----------     Pe�a movida foi a 7
					 * 1 |5 |7 |2 |     
					 * 	 ----------
					 * 2 |3 |8 |6 | 
					 * 	 ----------
					*/
				//pI++;
				
			excep = pJ - 1;
			if(excep >= 0) { //Verifica se existe uma posi��o � Esquerda
					
				String tb [][] =  new String [3][3];
				
				for(int i=0;i<=2;i++) {   // La�o para verificar a posi��o da pe�a vazia
					for(int j=0;j<=2;j++) {
						tb[i][j] = f.tb[i][j];
						}
				}
				
				Fronteira novaFronteira = new Fronteira(tb); //Cria nova fronteira
				novaFronteira.tb[pI][pJ] = f.tb[pI][excep]; 
				novaFronteira.tb[pI][excep] = " ";
					
				fronteiras.inserePrimeiro(novaFronteira);  //Adiciona a nova fronteira na lista de fronteiras
					
				//mostraMovimento(novaFronteira,f);
			}
				
			excep = pI + 1;
			if(excep <= 2) {  //Verifica se existe uma posi��o para baixo
				
				
				String tb [][] =  new String [3][3];
				
				for(int i=0;i<=2;i++) {   // La�o para verificar a posi��o da pe�a vazia
					for(int j=0;j<=2;j++) {
						tb[i][j] = f.tb[i][j];
						}
				}
				
				Fronteira novaFronteira = new Fronteira(tb); //Cria nova fronteira
				novaFronteira.tb[pI][pJ] = f.tb[excep][pJ]; 
				novaFronteira.tb[excep][pJ] = " ";
					
				fronteiras.inserePrimeiro(novaFronteira);  //Adiciona a nova fronteira na lista de fronteiras
							
				//mostraMovimento(novaFronteira,f);
					
			}
				
			excep = pJ + 1;
			if(excep <= 2) { //Verifica se existe uma posi��o � Direita
				
				String tb [][] =  new String [3][3];
				
				for(int i=0;i<=2;i++) {   // La�o para verificar a posi��o da pe�a vazia
					for(int j=0;j<=2;j++) {
						tb[i][j] = f.tb[i][j];
						}
				}
				
				Fronteira novaFronteira = new Fronteira(tb); //Cria nova fronteira
				novaFronteira.tb[pI][pJ] = f.tb[pI][excep]; 
				novaFronteira.tb[pI][excep] = " ";
					
				fronteiras.inserePrimeiro(novaFronteira);  //Adiciona a nova fronteira na lista de fronteiras
								
				//mostraMovimento(novaFronteira,f);
					
			}
				
			//mostraLista(fronteiras);
	}
	
	static public boolean verificaResultado(Fronteira ff, Lista fronteiras) {
	
		if(fronteiras.buscaFronteira(ff) > 0) {
			return true;
		
		}else {
			return false;
		}
	}
	
	static public void mostraMovimento(Fronteira fNew, Fronteira fOld) {
		
		String tabuleiroOld = "-------------\n";
		
		for(int i = 0; i<=2; i++) {
			tabuleiroOld += "| ";
			for(int j = 0; j<=2; j++) {
				tabuleiroOld += fNew.tb[i][j] + " | ";
			}
			
			tabuleiroOld += "\n-------------\n";
		}
		
		String tabuleiroNew = "-------------\n";
		
		for(int i = 0; i<=2; i++) {
			tabuleiroNew += "| ";
			for(int j = 0; j<=2; j++) {
				tabuleiroNew += fNew.tb[i][j] + " | ";
			}
			
			tabuleiroNew += "\n-------------\n";
		}
		
		JOptionPane.showMessageDialog(null, tabuleiroOld);
		JOptionPane.showMessageDialog(null, tabuleiroNew);
	}
	
	static public void mostraLista(Lista fronteiras) {
		
		String lista = "Lista: \n";
		
		for(int i=0;i<fronteiras.comprimento();i++) {
			lista += fronteiras.fronteiraNaPosicao(i) + "\n";
		}
		
		JOptionPane.showMessageDialog(null, lista);
	}
	
}

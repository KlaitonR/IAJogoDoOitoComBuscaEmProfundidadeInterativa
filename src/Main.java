import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		
		
		Tabuleiro tbInicial = new Tabuleiro();
		Tabuleiro tbFinal = new Tabuleiro();
		
		JOptionPane.showMessageDialog(null, "*** Preencha o Tabuleiro inicial ***");
		preencheTabuleiros(tbInicial);
		JOptionPane.showMessageDialog(null, "*** Preencha o Tabuleiro final ***");
		preencheTabuleiros(tbFinal);
		
		defineFronteiras(tbInicial);
		defineFronteiras(tbFinal); 
		
		int op = 0;
		
		do {
		
			switch (op) {
				
				case 1: //Busca em profundidade interativa
			
					
					break;
					
				case 2: //Busca gulosa
					
					break;
				
				case 3: //Busca A*
				
					break;
					
				}
			
			op = retornaMenu();
	
		}while(op!=4);
		
		
	}

	static int retornaMenu() { //menu
		
		String menu ="Qual o tipo de busca? \n"
					+ "1 - Busca em profundidade interativa. \n"
					+ "2 - Busca gulosa. \n"
					+ "3 - Busca A*. \n"
					+ "4 - SAIR";
				
	return Integer.parseInt(JOptionPane.showInputDialog(menu));
	
	}
	
	static void preencheTabuleiros(Tabuleiro tb) { //preenche os tabuleiros no estado inicial e final (resultado que se quer chegar)
		
	int op=0;
	String tab = "-------------\n";
	Peca p = new Peca(); // Inicia uma nova peça
	boolean verf = false; // controle para inserir apenas um espaço vazio no tabuleiro
	
		for(int i = 0; i<=2; i++) {
			tab += "| ";
			for(int j = 0; j<=2; j++) {
				op = Integer.parseInt(JOptionPane.showInputDialog("1 - Inserir um número para a próxima casa do tabuleiro \n"
					+ "2 - Inserir espaço vazio no tabuleiro"));
			
				if(op==1) { // verifica se o jogador quer colocar um número ou um espaço vazio
					p.setNumero(JOptionPane.showInputDialog("Digite o número.")); 
					tb.getTabuleiro()[i][j] = p; //preenche as casas do tabuleiro a peça
					
					tab+= p.getNumero() + " | ";
				}else {
					if(verf ==false) {
						p.setNumero("  ");
						tb.getTabuleiro()[i][j] = p; //preenche a casa do tabuleiro com espaço vazio
						tab+= p.getNumero() + " | ";
						verf = true;
					}else {
						JOptionPane.showMessageDialog(null, "Você só pode inserir um espaço vazio no tabuleiro!");
						j--;
					}
				}

				JOptionPane.showMessageDialog(null, tab); //mostar o tabuleiro a cada número inserido
			}
			
			tab+= "\n-------------\n";
		}
	}
	
	static void defineFronteiras(Tabuleiro tb) {
		
		for(int i=0;i<=2;i++) {
			for(int j=0;j<=2;j++) {
				
				/* tb.getTabuleiro()[i][j] tem a posição da peça atual, ex (i,j) = (1,1)
				 * se subtrairmos o contador i (i--), ficamos com (i--,j) = (0,1)
				 * Isso nos dará as peças que fazem fronteira com a peça atual, na vertical e horizontal
				 * 
				 * Posição atual = PA
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
				 * Se diminuirmos i-- e o valor for maior que zero, significa quue esta posição existe na matriz
				 * e a peça na POSIÇÃO ANALISADA (XX) faz fronteira com PA
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
				 * Se somarmos i++ e o valor for menor que dois, significa quue esta posição existe na matriz
				 * e a peça na POSIÇÃO ANALISADA (XX) faz fronteira com PA
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
				 * O mesnmo vale para a soma e subtração de da posição j
				 * 
				*/
				
				
				//Adiciona as peças vizinhas da peça atual na lista de fronteiras
				if(i-- > 0) 
					tb.getTabuleiro()[i][j].fronteiras.add(tb.getTabuleiro()[i--][j]);
				else 
					if(j-- > 0)
						tb.getTabuleiro()[i][j].fronteiras.add(tb.getTabuleiro()[i][j--]);
					else
						if(i++ < 2)
							tb.getTabuleiro()[i][j].fronteiras.add(tb.getTabuleiro()[i++][j]);
						else
							if(j++ < 2)
								tb.getTabuleiro()[i][j].fronteiras.add(tb.getTabuleiro()[i][j++]);
			}
		}
	}
}

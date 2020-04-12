import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Fronteira> FronteiraAtual = new ArrayList<>();
		Fronteira tbInicial = new Fronteira();
		Fronteira tbFinal = new Fronteira();
		
		String tbi[][] = new String [3][3];
		String tbf[][] = new String [3][3];
		
		// Dados já inseridos para teste
		tbi[0][0] = "1";
		tbi[1][0] = "2";
		tbi[2][0] = "3";
		tbi[0][1] = "4";
		tbi[0][2] = "5";
		tbi[1][1] = " ";
		tbi[2][2] = "7";
		tbi[1][2] = "8";
		tbi[2][1] = "6";

		/*tbf[0][0] = "2";
		tbf[1][0] = "4";
		tbf[2][0] = "3";
		tbf[0][1] = "1";
		tbf[0][2] = "5";
		tbf[1][1] = " ";
		tbf[2][2] = "7";
		tbf[1][2] = "8";
		tbf[2][1] = "6";*/
		
		tbf[0][0] = "1";
		tbf[1][0] = "2";
		tbf[2][0] = " ";
		tbf[0][1] = "4";
		tbf[0][2] = "5";
		tbf[1][1] = "8";
		tbf[2][2] = "6";
		tbf[1][2] = "7";
		tbf[2][1] = "3";
		
		
		tbInicial.tabuleiro = tbi;
		tbFinal.tabuleiro = tbf;
		
		tbInicial.antecessor = tbInicial;
		
		FronteiraAtual.add(tbInicial);
		
		int op = 0;
		
		do {
		
			switch (op) {
				
				case 1: //Busca em profundidade interativa
					
					int limiteDeProfundidade;
					boolean verificaLimite = true;
					
					limiteDeProfundidade = Integer.parseInt(JOptionPane.showInputDialog("Insira o limite de profundidade"));
					
					while(verificaFronteira(FronteiraAtual, tbFinal) == false && verificaLimite == true) { //|| profundidade != x
						
						//JOptionPane.showMessageDialog(null, FronteiraAtual.get(0).profundidade);
						
						if(FronteiraAtual.get(0).profundidade<limiteDeProfundidade && FronteiraAtual.get(0).expansao == false)
							expandeFronteira(FronteiraAtual.get(0),FronteiraAtual);
						else
							FronteiraAtual.remove(0);
						
						verificaLimite = limiteDeProfundidade(FronteiraAtual, limiteDeProfundidade);
					}

					if( verificaLimite == false) {
						JOptionPane.showMessageDialog(null, "Resultado não encontrado até a profundidade " + limiteDeProfundidade);
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
	
	
	public static boolean verificaFronteira(ArrayList<Fronteira> fronteiraAtual, Fronteira fronteira ) {
		
		boolean verifica = false;
		Fronteira f = new Fronteira();
		
		for(int i=0; i<fronteiraAtual.size();i++) {
			if(fronteiraAtual.get(i).toString().equals(fronteira.toString())) {
				verifica = true;
				f = fronteiraAtual.get(i);
			}
		}
		
		if(verifica == true) {
			
			String tabuleiroFinal = "-------------\n";
			
			for(int i = 0; i<=2; i++) {
				tabuleiroFinal += "| ";
				for(int j = 0; j<=2; j++) {
					tabuleiroFinal += fronteira.tabuleiro[i][j] + " | ";
				}
				
				tabuleiroFinal += "\n-------------\n";
			}
			
			
			String tabuleiro = "-------------\n";
			
			for(int i = 0; i<=2; i++) {
				tabuleiro += "| ";
				for(int j = 0; j<=2; j++) {
					tabuleiro += f.tabuleiro[i][j] + " | ";
				}
				
				tabuleiro += "\n-------------\n";
			}
			
			JOptionPane.showMessageDialog(null,"Tabuleiro Final : \n" + tabuleiroFinal + "\n Resultado Final : \n" + tabuleiro);
		}
		
		return verifica;
		
	}
	
	public static void expandeFronteira(Fronteira f, ArrayList<Fronteira> fronteiras) {
		
		if(f.expansao==false) {
			
			f.expansao = true;
			
			int pI=0; 
			int pJ=0;
			
			for(int i=0;i<3;i++) {   // Laço para verificar a posição da peça vazia
				for(int j=0;j<3;j++) {
					
					if(f.tabuleiro[i][j].equals(" ")) { // pega a posição da peça vazia
						pI = i;
						pJ = j;
					}
				}
			}
			
			//JOptionPane.showMessageDialog(null, "Posição i: " + pI + "Posição j: " + pJ + " - ");
			
					/* tb.tabuleiro[i][j] tem a posição da peça atual, ex (i,j) = (1,1)
					 * se subtrairmos o contador i (i--), ficamos com (i--,j) = (0,1)
					 * Isso nos dará as peças que fazem fronteira com a peça atual, na vertical e horizontal
					 * 
					 * Posição atual = PA (peça vazia na qual vai ser movida)
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
					 * Se diminuirmos i-- e o valor for maior que zero, significa que esta posição existe na matriz
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
					 * O mesmo vale para a soma e subtração da posição j
					 * 
					 *    0  1  2  
					 * 	 ----------
					 * 0 |  |XX|  |   
					 * 	 ----------     
					 * 1 |XX|PA|XX|     Assim teremos todas as posições para onde a peça vazia pode se mover
					 * 	 ----------
					 * 2 |  |XX|  |
					 * 	 ----------
					 * 
					*/
		
				if((pI - 1)>= 0) { //Verifica se existe uma posição em cima
					
					Fronteira novaFronteiraParaCima = new Fronteira(); //Cria nova fronteira
					
					String tbParaCima [][] =  new String [3][3];
					
					for(int i=0;i<3;i++) {   // Atribui os valores antigos ao novo tabuleiro da noa fronteira
						for(int j=0;j<3;j++) {
							tbParaCima[i][j] = f.tabuleiro[i][j];
						}
					}
					
					novaFronteiraParaCima.tabuleiro = tbParaCima; 
					
					tbParaCima[pI][pJ] = tbParaCima[pI - 1][pJ]; 
					tbParaCima[pI - 1][pJ] = " ";
					
					//JOptionPane.showMessageDialog(null, novaFronteiraParaCima.toString() +"\n"+ f.antecessor.toString());
					
					if(!novaFronteiraParaCima.toString().equals(f.antecessor.toString())) { //CASO SEJA IGUAL, ESSA FRONTEIRA JÁ EXISTE
						
						novaFronteiraParaCima.antecessor = f;
						novaFronteiraParaCima.profundidade = f.profundidade + 1;
						
						fronteiras.add(0,novaFronteiraParaCima);  //Adiciona a nova fronteira na lista de fronteiras
						
						//mostraMovimento(novaFronteiraParaCima);
					} 
				}
						/*    0  1  2  
						 * 	 ----------
						 * 0 |1 |7 |4 |   
						 * 	 ----------     
						 * 1 |5 |  |2 |     move a peça vazia para cima e a com valor para baixo 
						 * 	 ----------
						 * 2 |3 |8 |6 | 
						 * 	 ---------
						 *
						 *    0  1  2  
						 * 	 ----------
						 * 0 |1 |  |4 |   
						 * 	 ----------     Peça movida foi a 7
						 * 1 |5 |7 |2 |     
						 * 	 ----------
						 * 2 |3 |8 |6 | 
						 * 	 ----------
						*/
					//pI++;
					
				if((pJ - 1) >= 0) { //Verifica se existe uma posição à Esquerda
					
					Fronteira novaFronteiraEsquerda = new Fronteira(); //Cria nova fronteira
					
					String tbEsquerda [][] =  new String [3][3];
					
					for(int i=0;i<=2;i++) {   // Laço para verificar a posição da peça vazia
						for(int j=0;j<=2;j++) {
							tbEsquerda[i][j] = f.tabuleiro[i][j];
							}
					}
					
					novaFronteiraEsquerda.tabuleiro = tbEsquerda; 
					
					tbEsquerda[pI][pJ] = tbEsquerda[pI][pJ - 1]; 
					tbEsquerda[pI][pJ - 1] = " ";
					
					//JOptionPane.showMessageDialog(null, novaFronteiraEsquerda.toString() +"\n"+ f.antecessor.toString());
					
					if(!novaFronteiraEsquerda.toString().equals(f.antecessor.toString())) { //cASO SEJA IGUAL, ESSA FRONTEIRA JÁ EXISTE
				
						novaFronteiraEsquerda.antecessor = f;
						novaFronteiraEsquerda.profundidade = f.profundidade + 1;
						
						//f.fronteiras.add(novaFronteiraEsquerda);
						
						fronteiras.add(0,novaFronteiraEsquerda);  //Adiciona a nova fronteira na lista de fronteiras
			
						//mostraMovimento(novaFronteiraEsquerda);
						
					}
						
				}
					
				if((pI + 1) <= 2) {  //Verifica se existe uma posição para baixo
					
					Fronteira novaFronteiraParaBaixo = new Fronteira(); //Cria nova fronteira
	
					String tbParaBaixo [][] =  new String [3][3];
					
					for(int i=0;i<=2;i++) {   // Laço para verificar a posição da peça vazia
						for(int j=0;j<=2;j++) {
							tbParaBaixo[i][j] = f.tabuleiro[i][j];
						}
					}
					
					novaFronteiraParaBaixo.tabuleiro = tbParaBaixo; 
					
					tbParaBaixo[pI][pJ] = tbParaBaixo[pI + 1][pJ]; 
					tbParaBaixo[pI + 1][pJ] = " ";
					
					
					//JOptionPane.showMessageDialog(null, novaFronteiraParaBaixo.toString() +"\n"+ f.antecessor.toString());
					
					if(!novaFronteiraParaBaixo.toString().equals(f.antecessor.toString())) { //cASO SEJA IGUAL, ESSA FRONTEIRA JÁ EXISTE
					

						novaFronteiraParaBaixo.antecessor = f;
						novaFronteiraParaBaixo.profundidade = f.profundidade + 1;
						
						//f.fronteiras.add(novaFronteiraParaBaixo);
						
						fronteiras.add(0,novaFronteiraParaBaixo);  //Adiciona a nova fronteira na lista de fronteiras
						
						//mostraMovimento(novaFronteiraParaBaixo);
						
					}	
						
				}
					
				if((pJ + 1) <= 2) { //Verifica se existe uma posição à Direita
					
					Fronteira novaFronteiraDireita = new Fronteira(); //Cria nova fronteira
					
					String tbDireita [][] =  new String [3][3];
					
					for(int i=0;i<=2;i++) {   // Laço para verificar a posição da peça vazia
						for(int j=0;j<=2;j++) {
							tbDireita[i][j] = f.tabuleiro[i][j];
							}
					}
					
					novaFronteiraDireita.tabuleiro = tbDireita; 
					
					tbDireita[pI][pJ] = tbDireita[pI][pJ + 1]; 
					tbDireita[pI][pJ + 1] = " ";
					
					//JOptionPane.showMessageDialog(null, novaFronteiraDireita.toString() +"\n"+ f.antecessor.toString());
					
					if(!novaFronteiraDireita.toString().equals(f.antecessor.toString())) { //cASO SEJA IGUAL, ESSA FRONTEIRA JÁ EXISTE
					
						
						novaFronteiraDireita.antecessor = f;
						novaFronteiraDireita.profundidade = f.profundidade + 1;
						
						//f.fronteiras.add(novaFronteiraDireita);
						
						fronteiras.add(0,novaFronteiraDireita);  //Adiciona a nova fronteira na lista de fronteiras
						
						//mostraMovimento(novaFronteiraDireita);
					}
						
				}
				
				fronteiras.remove(f); //remove o nó expandido
				//JOptionPane.showMessageDialog(null, "FIM da expansao");
			
		}else {
			fronteiras.remove(f);
		}
		
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
		
		JOptionPane.showMessageDialog(null, tabuleiroNew);
	}
	
	public static boolean limiteDeProfundidade(ArrayList<Fronteira> fronteiras, int limite) {
		
		boolean verifica = false;
		
		for(int i=0;i<fronteiras.size();i++) {
			
			if(fronteiras.get(i).profundidade < limite && fronteiras.get(i).expansao == false) {
				verifica = true;
			}
		}
		
		return verifica;
		
	}

}

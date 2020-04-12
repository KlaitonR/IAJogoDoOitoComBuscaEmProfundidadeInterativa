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
		
		long init, end, diff;
		
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

//		tbf[0][0] = "1";
//		tbf[1][0] = " ";
//		tbf[2][0] = "2";
//		tbf[0][1] = "4";
//		tbf[0][2] = "5";
//		tbf[1][1] = "8";
//		tbf[2][2] = "6";
//		tbf[1][2] = "7";
//		tbf[2][1] = "3";
		
		/*tbf[0][0] = "1";
		tbf[1][0] = " ";
		tbf[2][0] = "2";
		tbf[0][1] = "4";
		tbf[0][2] = "5";
		tbf[1][1] = "8";
		tbf[2][2] = "6";
		tbf[1][2] = "7";
		tbf[2][1] = "3";*/
		
		tbf[0][0] = "2";
		tbf[1][0] = "3";
		tbf[2][0] = "1";
		tbf[0][1] = "7";
		tbf[0][2] = "5";
		tbf[1][1] = "4";
		tbf[2][2] = "6";
		tbf[1][2] = "8";
		tbf[2][1] = " ";
		
		tbInicial.tabuleiro = tbi;
		tbFinal.tabuleiro = tbf;
		
		tbInicial.antecessor = tbInicial;
		
		FronteiraAtual.add(tbInicial);
		
		int op = 0;
		
		do {
		
			switch (op) {
				
				case 1: //Busca em profundidade interativa
					
					init = System.currentTimeMillis();
					
					int limiteDeProfundidade; //limite de profundidade da busca
					boolean verificaLimite = true; //verificação para o limite da busca
					
					Fronteira fronteiraFinal = new Fronteira(); //guardar o resultado final da busca
					
					fronteiraFinal = verificaFronteira(FronteiraAtual, tbFinal);
					
					limiteDeProfundidade = Integer.parseInt(JOptionPane.showInputDialog("Insira o limite de profundidade"));
					
					do { // Enquanto não retornar o resultado correto ou o limite de profundidade não ser atingido
						
						//Se o primeiro da lista não tiver em sua profundidade máxima e ainda não foi expandido
						if(FronteiraAtual.get(0).profundidade < limiteDeProfundidade && FronteiraAtual.get(0).expansao == false)
							expandeFronteira(FronteiraAtual.get(0),FronteiraAtual); //expande a fronteira
						else
							FronteiraAtual.remove(0); //Se não, retira da lista, pois já foi expandido, ou seu limite de profundidade já foi atingido
						
						//Verifica se todos os nós foram expandidos ao seu limite
						verificaLimite = limiteDeProfundidade(FronteiraAtual, fronteiraFinal, limiteDeProfundidade);
						
						//Verifica se já achou o resultado dentro das fronteiras atuais
						fronteiraFinal = verificaFronteira(FronteiraAtual, tbFinal);
						
					}while(fronteiraFinal == null && verificaLimite == true);

					if( verificaLimite == false) { //Se o limite for atingido e ainda não encontrou o resultado
						JOptionPane.showMessageDialog(null, "Resultado não encontrado até a profundidade " + limiteDeProfundidade);
					}
					
					end = System.currentTimeMillis();
					
					diff = end - init;
					
					//MOSTRA RESULTADOS FINAIS
					if(fronteiraFinal != null) {
						
//						for(int i=0; i< FronteiraAtual.size();i++) {
//							
//							if(FronteiraAtual.get(i).profundidade < fronteiraFinal.profundidade && FronteiraAtual.get(i).toString().equals(tbFinal.toString())) {
//								fronteiraFinal = FronteiraAtual.get(i);
//							}
//						}
						
						String tabuleiroFinal = "-------------\n";
						
						for(int i = 0; i<=2; i++) {
							tabuleiroFinal += "| ";
							for(int j = 0; j<=2; j++) {
								tabuleiroFinal += tbFinal.tabuleiro[i][j] + " | ";
							}
							
							tabuleiroFinal += "\n-------------\n";
						}
						
						
						String tabuleiro = "-------------\n";
						
						for(int i = 0; i<=2; i++) {
							tabuleiro += "| ";
							for(int j = 0; j<=2; j++) {
								tabuleiro += fronteiraFinal.tabuleiro[i][j] + " | ";
							}
							
							tabuleiro += "\n-------------\n";
						}
						
						JOptionPane.showMessageDialog(null,"Tabuleiro Final : \n" + tabuleiroFinal + "\n Resultado Final : \n" + tabuleiro
								+ "\n Foi encontrado na profundidade: " + fronteiraFinal.profundidade
								+ "\n "
								+ ""
								+ ""
								+ ""
								+ ""
								+ "A Solução foi encontrada em: " + (diff / 1000) + " segundos");
						
						// MOSTRA MOVIMENTOS NO CONSOLE
				
						String caminho = "";
						int n = fronteiraFinal.profundidade + 1;
						
						caminho += "-------------\n";
						
						for(int i = 0; i<=2; i++) {
							caminho += "| ";
							for(int j = 0; j<=2; j++) {
								caminho += fronteiraFinal.tabuleiro[i][j] + " | ";
							}
							
							caminho += "\n-------------\n";
						}
						
						caminho += " Nº de movimentos: "+ n-- + "\n \n";
						
						do {
							
							fronteiraFinal = fronteiraFinal.antecessor;
	
							caminho += "-------------\n";
							
							for(int i = 0; i<=2; i++) {
								caminho += "| ";
								for(int j = 0; j<=2; j++) {
									caminho += fronteiraFinal.tabuleiro[i][j] + " | ";
								}
								caminho += "\n-------------\n";
							}
							
							caminho += " Nº de movimentos: "+ n-- + "\n \n";
							
						}while(!fronteiraFinal.toString().equals(tbInicial.toString()));
						
						System.out.println(caminho);
						
						JOptionPane.showMessageDialog(null, "Os movimentos feitos para a resolução do jogo podem ser encontrados no console");
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
	
	
	public static Fronteira verificaFronteira(ArrayList<Fronteira> fronteiraAtual, Fronteira fronteira ) {
		
		boolean verifica = false;
		Fronteira f = new Fronteira();
		f.profundidade = 0;
		
		for(int i=0; i<fronteiraAtual.size();i++) {
			if(fronteiraAtual.get(i).toString().equals(fronteira.toString())) {
				//if(fronteiraAtual.get(i).profundidade < f.profundidade) {
					verifica = true;
					f = fronteiraAtual.get(i);
				//}
			}
		}
		
		if(verifica == true)
			return f;
		else
			return null;
		
	}
	
	public static void expandeFronteira(Fronteira f, ArrayList<Fronteira> fronteiras) {
		
		if(f.expansao==false) { //Verifica se o no já foi expandido
			
			f.expansao = true; //Caso não tenha sido expandido, ele será expandido, setando agora a variavel expansao como verdadeira
			
			int pI=0; //Para recuperar a posição da peça vazia
			int pJ=0; //Para recuperar a posição da peça vazia
			
			for(int i=0;i<3;i++) {   // Laço para verificar a posição da peça vazia
				for(int j=0;j<3;j++) {
					
					if(f.tabuleiro[i][j].equals(" ")) { // pega a posição da peça vazia
						pI = i;
						pJ = j;
					}
				}
			}
			
			//JOptionPane.showMessageDialog(null, "Posição i: " + pI + "Posição j: " + pJ + " - ");
			
					/* f.tabuleiro[i][j] tem a posição da peça atual, ex (i,j) = (1,1)
					 * se subtrairmos o contador i (i--), ficamos com (i--,j) = (0,1)
					 * Isso nos dará a peça vizinha com a peça atual
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
					 * e a peça na POSIÇÃO ANALISADA (XX) é vizinha de PA
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
					 * Se somarmos i++ e o valor for menor que dois, significa que esta posição existe na matriz
					 * e a peça na POSIÇÃO ANALISADA (XX) é vizinha de PA
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
					
					tbParaCima[pI][pJ] = tbParaCima[pI - 1][pJ]; //Faz os movimentos do tabuleiro
					tbParaCima[pI - 1][pJ] = " ";
					
					//JOptionPane.showMessageDialog(null, novaFronteiraParaCima.toString() +"\n"+ f.antecessor.toString());
					
					//Verifica se o tabuleiro criado não é igual ao antecessor de seu antecessor (se não está voltando a uma joga que já foi feita)
					if(!novaFronteiraParaCima.toString().equals(f.antecessor.toString())) { //CASO SEJA IGUAL, ESSA FRONTEIRA JÁ EXISTE
						
						novaFronteiraParaCima.antecessor = f; //Guarda seu antecessor para recuperar o caminho
						novaFronteiraParaCima.profundidade = f.profundidade + 1; //Guarda a profundidade
						
						fronteiras.add(0,novaFronteiraParaCima);  //Adiciona a nova fronteira na lista de fronteiras
						//mostraMovimento(novaFronteiraParaCima);
						
						//JOptionPane.showMessageDialog(null, "prof nova: " + novaFronteiraParaCima.profundidade + "prof antiga: " + f.profundidade);
					} 
				}
					
				if((pJ - 1) >= 0) { //Verifica se existe uma posição à Esquerda
					
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
					
					if(!novaFronteiraEsquerda.toString().equals(f.antecessor.toString())) { 
				
						novaFronteiraEsquerda.antecessor = f;
						novaFronteiraEsquerda.profundidade = f.profundidade + 1;
						
						//f.fronteiras.add(novaFronteiraEsquerda);
						fronteiras.add(0,novaFronteiraEsquerda);
						//mostraMovimento(novaFronteiraEsquerda);
						
					//	JOptionPane.showMessageDialog(null, "prof nova: " + novaFronteiraEsquerda.profundidade + "prof antiga: " + f.profundidade);
						
					}
						
				}
					
				if((pI + 1) <= 2) {  //Verifica se existe uma posição para baixo
					
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
					
					if(!novaFronteiraParaBaixo.toString().equals(f.antecessor.toString())) { 
			
						novaFronteiraParaBaixo.antecessor = f;
						novaFronteiraParaBaixo.profundidade = f.profundidade + 1;
						
						//f.fronteiras.add(novaFronteiraParaBaixo);
						fronteiras.add(0,novaFronteiraParaBaixo);  
						//mostraMovimento(novaFronteiraParaBaixo);
						
					//	JOptionPane.showMessageDialog(null, "prof nova: " + novaFronteiraParaBaixo.profundidade + "prof antiga: " + f.profundidade);
						
					}	
						
				}
					
				if((pJ + 1) <= 2) { //Verifica se existe uma posição à Direita
					
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
					
					if(!novaFronteiraDireita.toString().equals(f.antecessor.toString())) { 
					
						novaFronteiraDireita.antecessor = f;
						novaFronteiraDireita.profundidade = f.profundidade + 1;
						
						//f.fronteiras.add(novaFronteiraDireita);
						fronteiras.add(0,novaFronteiraDireita);
						//mostraMovimento(novaFronteiraDireita);
						
					//	JOptionPane.showMessageDialog(null, "prof nova: " + novaFronteiraDireita.profundidade + "prof antiga: " + f.profundidade);
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
	
	public static boolean limiteDeProfundidade(ArrayList<Fronteira> fronteiras,Fronteira f, int limite) {
		
		boolean verifica = false;
		
		for(int i=0;i<fronteiras.size();i++) {
			
			if(fronteiras.get(i).profundidade < limite && fronteiras.get(i).expansao == false && f == null) {
				verifica = true;
			}
		}
		
		return verifica;
		
	}

}

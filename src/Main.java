import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Fronteira> FronteiraAtual = new ArrayList<>();
		ArrayList<Fronteira> nosDescobertos = new ArrayList<>();
		
		Fronteira tbInicial = new Fronteira();
		Fronteira tbFinal = new Fronteira();
		
		String tbi[][] = new String [3][3];
		String tbf[][] = new String [3][3];
		
		long init, end, diff; //Calcular tempo de execuçãos
		
//		JOptionPane.showMessageDialog(null, "Preencha o tabuleiro inicial do jogo.");
//		preencheTabuleiros(tbInicial);
//		
//		JOptionPane.showMessageDialog(null, "Preencha o tabuleiro final do jogo.");
//		preencheTabuleiros(tbFinal);
		
// Dados já inseridos para teste
		
//		tbi[0][0] = "1";
//		tbi[1][0] = "8";
//		tbi[2][0] = "7";
//		tbi[0][1] = "2";
//		tbi[0][2] = "3";
//		tbi[1][1] = " ";
//		tbi[2][2] = "5";
//		tbi[1][2] = "4";
//		tbi[2][1] = "6";
		
//		tbf[0][0] = " ";
//		tbf[1][0] = "3";
//		tbf[2][0] = "4";
//		tbf[0][1] = "5";
//		tbf[0][2] = "2";
//		tbf[1][1] = "8";
//		tbf[2][2] = "6";
//		tbf[1][2] = "7";
//		tbf[2][1] = "1";
//		
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
		
		tbInicial.tabuleiro = tbi; // inicia a fronteira inicial
		tbInicial.antecessor = tbInicial; //Coloca como antecessor dela, ela mesma
		
		tbFinal.tabuleiro = tbf; // inicia a fronteira final
		
		tbInicial.custo = BuscaGulosa.calculaCusto(tbInicial, tbFinal); //Inicia o custo da fronteira inicial
		
		boolean verificaResultado = false; // inicia a verificação de resultado
		
		int op = 0;
		
		do {
		
			switch (op) {
				
				case 1: //Busca em profundidade interativa
					
					init = System.currentTimeMillis(); // inicia calculo de tempo;
					
					BuscaEmAprofundamentoInterativo buscaEmAprofundamentoInterativo = new BuscaEmAprofundamentoInterativo(FronteiraAtual, tbFinal, tbInicial);
					
					int limiteDeProfundidade, verificaLimite = 0; //limite de profundidade da busca
					
					limiteDeProfundidade = Integer.parseInt(JOptionPane.showInputDialog("Insira o limite de profundidade"));
					
					// Verifica nivel por nivel, até o limite de profundidade imposto pelo usuário, ou até achar um resultado antes da profundidade limite
					// ou seja, irá reiniciar toda vez que não encontrar o resultado
					while(verificaLimite <= limiteDeProfundidade && verificaResultado == false) { 
						
						FronteiraAtual.add(tbInicial); //Coloca o nó inicial (tabuleiro) novamente na lista que esta vazia
						
						while(verificaResultado == false && (!FronteiraAtual.isEmpty())){ // Enquanto não retornar o resultado correto ou a lista ficar vazia
						
							if(verificaResultado == false) { // Se o primeiro da lista não for o resultado, expande
							
								//Se o primeiro da lista não for o nó mais profundo, ou seja, ainda pode ser expandido para mais uma profundidade e ainda não foi expandido
								if(FronteiraAtual.get(0).profundidade < verificaLimite)
									verificaResultado = buscaEmAprofundamentoInterativo.expandeFronteira(FronteiraAtual.get(0), tbFinal,FronteiraAtual); //expande a fronteira
								else
									FronteiraAtual.remove(0); //Se não, retira da lista, pois já foi expandido, ou seu limite de profundidade já foi atingido
							}
						}
						
					verificaLimite++; //Passa para o próximo nível
					
					}

					if( verificaResultado == false) { //Se o limite for atingido e ainda não encontrou o resultado
						JOptionPane.showMessageDialog(null, "Resultado não encontrado até a profundidade " + limiteDeProfundidade);
					}
					
					end = System.currentTimeMillis();
					
					diff = end - init;
					
					//MOSTRA RESULTADOS FINAIS
					if(verificaResultado == true) {
					
						mostraResultado(tbFinal, tbInicial, FronteiraAtual, diff, op);
						
						verificaLimite = 0;
						FronteiraAtual.clear();
						verificaResultado = false;
					}
						
					break;
					
				case 2: //Busca gulosa
					
					init = System.currentTimeMillis(); // inicia calculo de tempo;
					
					BuscaGulosa buscaGulosa = new BuscaGulosa(FronteiraAtual,nosDescobertos, tbFinal, tbInicial);
					Fronteira menorCustoEstimado = new Fronteira();
					
					FronteiraAtual.add(tbInicial); //Coloca o nó inicial (tabuleiro) novamente na lista que esta vazia
					
					while(verificaResultado == false && !FronteiraAtual.isEmpty()) { // enquanto não chegar no resultado, ou houver fronteiras na lista
						
						menorCustoEstimado = buscaGulosa.menorCusto(FronteiraAtual);  //Procura pelo nó fronteira com menor custo estimado

						verificaResultado = buscaGulosa.expandeFronteira(menorCustoEstimado, tbFinal, FronteiraAtual, nosDescobertos); // expande e verifica o nó fronteira
						
					}
					
					end = System.currentTimeMillis();
					
					diff = end - init;
					
					if(verificaResultado == true)
						mostraResultado(tbFinal, tbInicial, FronteiraAtual, diff, op);
					else
						JOptionPane.showMessageDialog(null, "Resultado não encontrado");
					
					verificaResultado = false;
					
					break;
				
				case 3: //Busca A*
					
					init = System.currentTimeMillis(); // inicia calculo de tempo;
					
					BuscaA buscaA = new BuscaA(FronteiraAtual, nosDescobertos, tbFinal, tbInicial);
					Fronteira menorCusto = new Fronteira();
					
					FronteiraAtual.add(tbInicial); //Coloca o nó inicial (tabuleiro) novamente na lista que esta vazia
					
					while(verificaResultado == false && !FronteiraAtual.isEmpty()) { // enquanto não chegar no resultado, ou houver fronteiras na lista
						
						menorCusto = buscaA.menorCusto(FronteiraAtual); //Procura pelo nó fronteira com menor custo

						verificaResultado = buscaA.expandeFronteira(menorCusto, tbFinal, FronteiraAtual, nosDescobertos); // expande e verifica o nó fronteira
						
					}
					
					end = System.currentTimeMillis();
					
					diff = end - init;
					
					if(verificaResultado == true)
						mostraResultado(tbFinal, tbInicial, FronteiraAtual, diff, op);
					else
						JOptionPane.showMessageDialog(null, "Resultado não encontrado");
					
					verificaResultado = false;
				
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
	 
	 public static void preencheTabuleiros(Fronteira tb) { //preenche os tabuleiros no estado inicial e final (resultado que se quer chegar)

			int op=0;
			String tab = "-------------\n";
	
			boolean verf = false; // controle para inserir apenas um espaço vazio no tabuleiro

				for(int i = 0; i<=2; i++) {
					tab += "| ";
					for(int j = 0; j<=2; j++) {
						op = Integer.parseInt(JOptionPane.showInputDialog("1 - Inserir um número para a próxima casa do tabuleiro \n"
							+ "2 - Inserir espaço vazio no tabuleiro"));

						if(op==1) { // verifica se o jogador quer colocar um número ou um espaço vazio
							tb.tabuleiro[i][j] = (JOptionPane.showInputDialog("Digite o número.")); 
							tab+= tb.tabuleiro[i][j] + " | ";
							
						}else {
							if(verf == false) {
								tb.tabuleiro[i][j] = ("  "); //preenche a casa do tabuleiro com espaço vazio
								tab+= tb.tabuleiro[i][j] + " | ";
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
	 
	 public static void mostraResultado(Fronteira tbFinal, Fronteira tbInicial, ArrayList<Fronteira> FronteiraAtual, long diff, int op) {
		 String tabuleiroFinal = "-------------\n";
			
			for(int i = 0; i<=2; i++) {
				tabuleiroFinal += "| ";
				for(int j = 0; j<=2; j++) {
					tabuleiroFinal += tbFinal.tabuleiro[i][j] + " | ";
				}
				
				tabuleiroFinal += "\n-------------\n";
			}
			
			String tabuleiroResultado = "-------------\n";
			
			for(int i = 0; i<=2; i++) {
				tabuleiroResultado += "| ";
				for(int j = 0; j<=2; j++) {
					tabuleiroResultado += FronteiraAtual.get(0).tabuleiro[i][j] + " | ";
				}
				
				tabuleiroResultado += "\n-------------\n";
			}
			
			if(op == 1) {
				JOptionPane.showMessageDialog(null,"Tabuleiro Final : \n" + tabuleiroFinal + "\n Resultado Final : \n" + tabuleiroResultado
						+ "\n Foi encontrado na profundidade: " + FronteiraAtual.get(0).profundidade
						+ "\n "
						+ "A Solução foi encontrada em: " + (diff / 1000) + " segundos");
	
				// MOSTRA MOVIMENTOS NO CONSOLE
		
				String caminho = "";
				int n = FronteiraAtual.get(0).profundidade + 1;
				
				caminho += "-------------\n";
				
				for(int i = 0; i<=2; i++) {
					caminho += "| ";
					for(int j = 0; j<=2; j++) {
						caminho += FronteiraAtual.get(0).tabuleiro[i][j] + " | ";
					}
					
					caminho += "\n-------------\n";
				}
				
				caminho += "Nº de movimentos: "+ n-- + "\n \n";
				
				Fronteira caminhoAntecessor =  new Fronteira();
				caminhoAntecessor = FronteiraAtual.get(0);
				
				do {
					
					caminhoAntecessor = caminhoAntecessor.antecessor;
	
					caminho += "-------------\n";
					
					for(int i = 0; i<=2; i++) {
						caminho += "| ";
						for(int j = 0; j<=2; j++) {
							caminho += caminhoAntecessor.tabuleiro[i][j] + " | ";
						}
						caminho += "\n-------------\n";
					}
					
					caminho += "Nº de movimentos: "+ n-- + "\n \n";
					
				}while(!caminhoAntecessor.toString().equals(tbInicial.toString()));
				
				System.out.println(caminho);
				
				JOptionPane.showMessageDialog(null, "Os movimentos feitos para a resolução do jogo podem ser encontrados no console");
			}
			
			if(op == 2) {
				
				JOptionPane.showMessageDialog(null,"Tabuleiro Final : \n" + tabuleiroFinal + "\n Resultado Final : \n" + tabuleiroResultado
						+ "A Solução foi encontrada em: " + (diff / 1000) + " segundos");
				
				// MOSTRA MOVIMENTOS NO CONSOLE
				
				String caminho = "";
				int n = FronteiraAtual.get(0).profundidade + 1;
				
				caminho += "-------------\n";
				
				for(int i = 0; i<=2; i++) {
					caminho += "| ";
					for(int j = 0; j<=2; j++) {
						caminho += FronteiraAtual.get(0).tabuleiro[i][j] + " | ";
					}
					
					caminho += "\n-------------\n";
				}
				
				caminho += "Nº de movimentos: "+ n-- +"\n Custo atual: " + FronteiraAtual.get(0).custo +"\n \n";
				
				Fronteira caminhoAntecessor =  new Fronteira();
				caminhoAntecessor = FronteiraAtual.get(0);
				
				do {
					
					caminhoAntecessor = caminhoAntecessor.antecessor;
	
					caminho += "-------------\n";
					
					for(int i = 0; i<=2; i++) {
						caminho += "| ";
						for(int j = 0; j<=2; j++) {
							caminho += caminhoAntecessor.tabuleiro[i][j] + " | ";
						}
						caminho += "\n-------------\n";
					}
					
					caminho += "Nº de movimentos: "+ n-- +"\n Custo atual: " + caminhoAntecessor.custo +"\n \n";
					
				}while(!caminhoAntecessor.toString().equals(tbInicial.toString()));
				
				System.out.println(caminho);
				
				JOptionPane.showMessageDialog(null, "Os movimentos feitos para a resolução do jogo podem ser encontrados no console");
				
			}
			
			if(op == 3) {
				
				JOptionPane.showMessageDialog(null,"Tabuleiro Final : \n" + tabuleiroFinal + "\n Resultado Final : \n" + tabuleiroResultado
						+ "A Solução foi encontrada em: " + (diff / 1000) + " segundos");
				
				// MOSTRA MOVIMENTOS NO CONSOLE
				
				String caminho = "";
				int n = FronteiraAtual.get(0).profundidade + 1;
				
				caminho += "-------------\n";
				
				for(int i = 0; i<=2; i++) {
					caminho += "| ";
					for(int j = 0; j<=2; j++) {
						caminho += FronteiraAtual.get(0).tabuleiro[i][j] + " | ";
					}
					
					caminho += "\n-------------\n";
				}
				
				caminho += "Nº de movimentos: "+ n-- +"\n Custo atual: " + FronteiraAtual.get(0).custo +"\n \n";
				
				Fronteira caminhoAntecessor =  new Fronteira();
				caminhoAntecessor = FronteiraAtual.get(0);
				
				do {
					
					caminhoAntecessor = caminhoAntecessor.antecessor;
	
					caminho += "-------------\n";
					
					for(int i = 0; i<=2; i++) {
						caminho += "| ";
						for(int j = 0; j<=2; j++) {
							caminho += caminhoAntecessor.tabuleiro[i][j] + " | ";
						}
						caminho += "\n-------------\n";
					}
					
					caminho += "Nº de movimentos: "+ n-- +"\n Custo atual: " + caminhoAntecessor.custo +"\n \n";
					
				}while(!caminhoAntecessor.toString().equals(tbInicial.toString()));
				
				System.out.println(caminho);
				
				JOptionPane.showMessageDialog(null, "Os movimentos feitos para a resolução do jogo podem ser encontrados no console");
				
			}
	 }
	 
	 
}

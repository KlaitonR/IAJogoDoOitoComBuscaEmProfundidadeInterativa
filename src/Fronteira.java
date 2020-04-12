public class Fronteira {

	String tabuleiro [][];
	//ArrayList<Fronteira> fronteiras = new ArrayList<>();
	int profundidade;
	boolean expansao;
	Fronteira antecessor;
	
	public String toString() {
		
		String tab = "";
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				
				tab += tabuleiro[i][j];
				
			}
		}
		
		return tab;
	}
}

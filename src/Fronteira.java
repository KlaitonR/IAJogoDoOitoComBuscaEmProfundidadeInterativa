public class Fronteira { 
	
	String tb [][] = new String [3][3];
	Fronteira proximo;
	
	public Fronteira(String tb[][]) {
		this.tb = tb;
		this.proximo = null; 
	}
	
	public String toString() {
		
		String tab = "-------------\n";
		
		for(int i=0;i<=2;i++) {  
			tab += "| ";
			for(int j=0;j<=2;j++) {
				
				tab += tb[i][j] + " | ";
			}
			tab+= "\n-------------\n";
		}
		
		return tab;
	}
}

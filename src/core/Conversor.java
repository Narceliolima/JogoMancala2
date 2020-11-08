package core;

public class Conversor {
	
	//Função que converte uma matriz de int (tabuleiro) para um vetor de char (Strings) separando os valores contidos
	//nela com "-"
	public static String toIstring(int[][] tabuleiro) {
		
		String string = "";
		
		for(int i=0;i<tabuleiro.length;i++) {
			for(int j=0;j<tabuleiro[i].length;j++) {
				string += tabuleiro[i][j]+"-";
			}
		}
		
		return string;
	}
	
	//Função que converte uma String para uma matriz de int, o "-" separa os int inteiros que estão contigos dentro da
	//String
	public static int[][] toTabuleiro(String string) {
		
		int[][] tabuleiro = new int[2][7];
		int n = 0;
		
		for(int i=0;i<tabuleiro.length;i++) {
			for(int j=0;j<tabuleiro[i].length;j++) {
				if(string.charAt(n+1)=='-') {
					tabuleiro[i][j] = Integer.parseInt(""+string.charAt(n));
					n+=2;
				}
				else if(string.charAt(n+1)!='-') {
					tabuleiro[i][j] = Integer.parseInt(""+string.charAt(n)+string.charAt(n+1));
					n+=3;
				}
			}
		}
		
		return tabuleiro;
	}
	
	//Função que imprime matriz, usada para fins de debuggar
	public static void imprimeMatriz(int[][] matriz) {
		int i,j;
		for(i=0;i<matriz.length;i++) {
			for(j=0;j<matriz[i].length;j++) {
				System.out.print(matriz[i][j]);
			}
			System.out.println();
		}
	}
}

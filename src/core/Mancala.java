package core;

public class Mancala {
	
	private static final int jogador1 = 0;
	private static final int jogador2 = 1;
	
	//Função que faz as peças "mover-se" pelo tabuleiro, ela recebe um jogador, equivalente ao jogador que está jogando
	//no momento, uma posição equivalente a posição que o jogador desenha remover as sementes para depois redistribuir
	//e o tabuleiro com as posições atuais das sementes.
	public static int jogar(int jogador, int posicao, int[][] tabuleiro) {
		
		int j;
		int sementes;
		int ultimaPosicao = 0;
		boolean inverter;
		boolean ultimoJogador = false;
		
		sementes = tabuleiro[jogador][posicao];
		tabuleiro[jogador][posicao] = 0;
		if(jogador == 0) {
			j = posicao - 1;
			inverter = true; //////////
		}
		else {
			j = posicao + 1;
			inverter = false;//////////
		}
		
		while(sementes>0) {
			while(j>=0&&sementes>0&&inverter) {
				if(j>6) {
					j--;
				}
				if(jogador==jogador2&&j==0) {
					j--;
				}
				else {
					tabuleiro[jogador1][j]++;
					sementes--;
					j--;
				}
			}
			while(j<=6&&sementes>0) {
				inverter = false;
				if(j<0) {
					j++;
				}
				if(jogador==jogador1&&j==6) {
					j++;
				}
				else {
					tabuleiro[jogador2][j]++;
					sementes--;
					j++;
				}
			}
			////////////////////////
			if(inverter) {
				ultimaPosicao = j+1;
			}
			else {// ----**---- ////
				ultimaPosicao = j-1;
			}
			ultimoJogador = inverter;
			inverter = true;
			/////////////////////////
		}		
		
		return aplicaRegra(ultimaPosicao,ultimoJogador,tabuleiro,jogador);
		
	}
	
	//Caso a jogada satisfaça algumas das regras, aplica de acordo com sua condição.
	public static int aplicaRegra(int ultimaPosicao, boolean ultimoJogador, int[][] tabuleiro,int jogador) {
		
		if(ultimaPosicao==0&&ultimoJogador) { //ultimoJogador == true == jogador1
			//Se a ultima peça cair na kalla do jogador que está jogando
			//Jogador 1 joga novamente
			return 1;
		}
		else if(ultimaPosicao==6&&!ultimoJogador) { //ultimoJogador == false == jogador2
			//Jogador 2 joga novamente
			return 2;
		}
		else if(tabuleiro[jogador1][ultimaPosicao]-1==0&&ultimoJogador&&ultimaPosicao!=0&&tabuleiro[jogador2][ultimaPosicao-1]>0&&jogador==jogador1) {
			//Se a ultima peça cair em uma buraco vazio do lado do jogador e o buraco adjacente tiver peças
			tabuleiro[jogador1][0] += tabuleiro[jogador2][ultimaPosicao-1] + 1;
			tabuleiro[jogador1][ultimaPosicao] = 0;
			tabuleiro[jogador2][ultimaPosicao-1] = 0;
			
		}
		else if(tabuleiro[jogador2][ultimaPosicao]-1==0&&!ultimoJogador&&ultimaPosicao!=6&&tabuleiro[jogador1][ultimaPosicao+1]>0&&jogador==jogador2) {
			tabuleiro[jogador2][6] += tabuleiro[jogador1][ultimaPosicao+1] + 1;
			tabuleiro[jogador2][ultimaPosicao] = 0;
			tabuleiro[jogador1][ultimaPosicao+1] = 0;
		}
		
		return 0;
	}
	
	//Função varre a fileira de cada jogador para checar se algum dos lados está vazio
	public static int verificaVazios(int[][] tabuleiro) {
		
		int contagemSementes;
		int resultado;
		int linha, coluna;		
		
		for(linha=0;linha<tabuleiro.length;linha++) {
			contagemSementes = 0;
			for(coluna=0;coluna<tabuleiro[linha].length;coluna++) {
				if(linha==jogador1&&coluna!=0||linha==jogador2&&coluna!=6) {
					contagemSementes += tabuleiro[linha][coluna];
				}
			}
			if(contagemSementes==0) {
				if(linha==jogador1) {
					resultado = tabuleiro[jogador2][6];
					tabuleiro[jogador2][6] = 0;
					tabuleiro [jogador2][6] = esvaziaResto(tabuleiro[jogador2], resultado);
				}
				else {
					resultado = tabuleiro[jogador1][0];
					tabuleiro[jogador1][0] = 0;
					tabuleiro [jogador1][0] = esvaziaResto(tabuleiro[jogador1], resultado);
				}
				if(tabuleiro[jogador1][0]>tabuleiro[jogador2][6]) {
					return 0;
				}
				else if(tabuleiro[jogador1][0]<tabuleiro[jogador2][6]){
					return 1;
				}
				else if(tabuleiro[jogador1][0]==tabuleiro[jogador2][6]) {
					return 2;
				}
			}
		}
		return -1;
	}
	
	//Função que serve para esvaziar o lado adjacente
	public static int esvaziaResto(int[] vetor, int resultado) {
		for(int i=0;i<vetor.length;i++) {
			resultado += vetor[i];
			vetor[i] = 0;
		}
		return resultado;
	}
}

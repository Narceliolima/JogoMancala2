package comunicacao;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Remoto extends Remote{
	
	public void conecta() throws MalformedURLException, RemoteException, NotBoundException;
	public void setMensagemRec(String mensagemRec) throws RemoteException;
	public void setTabuleiro(int[][] tabuleiro) throws RemoteException;
	public void setJogador(int jogador) throws RemoteException;
	public void desistir(int jogador) throws RemoteException;
	public void finalizaJogo() throws RemoteException;
	public void voltarJogada() throws RemoteException; //Ué
	public void notificaSaida(int jogador) throws RemoteException;
	
}

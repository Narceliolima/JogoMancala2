package core;
import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import comunicacao.Remoto;
import grafica.GUI;
import grafica.Notificacao;

public class Jogador extends UnicastRemoteObject implements Remoto{
	
	private static final long serialVersionUID = 1337;
	private String host;
	private int porta;
	private String mensagemEnv = "";
	private Remoto oponente;
	private Registry registro;
	private boolean esperando = true;
	private GUI win;
	//--------------------------------------------//--------------------------------------------//
	private ArrayList<Integer> historico;
	private boolean fim = false;
	private int novamente = 0;
	private int jogador = 1;
	private int jogando = 0;
	private static final int jogador1 = 0;
	private static final int jogador2 = 1;

	public Jogador(int[][] tabuleiro) throws RemoteException {
		super();
		win = new GUI(tabuleiro);
		createRunnable();
		historico = new ArrayList<Integer>();
		
		host = Notificacao.configuraHost();
		porta = Notificacao.configuraPorta();
		
		try {
			registro = LocateRegistry.createRegistry(porta);
			registro.bind("//"+host+":"+porta+"/Servidor",this);
			win.setMensagemEnviada("Servidor Registrado!");
			win.setMensagemEnviada("Aguardando jogador");
			this.jogador = 0;
		}
		catch (ExportException|AlreadyBoundException e) {
			try {
				win.setMensagemEnviada("Servidor já registrado");
				win.setMensagemEnviada("Registrando cliente");
				registro = LocateRegistry.getRegistry(porta);
				registro.bind("//"+host+":"+porta+"/Cliente",this);
				win.setMensagemEnviada("Cliente Registrado!");
				oponente = (Remoto)registro.lookup("//"+host+":"+porta+"/Servidor");
				win.setMensagemEnviada("Jogador "+(jogador1+1)+" conectado");
				oponente.conecta();
				saiEspera();
				oponente.saiEspera();
			} catch (Exception e2) {
				e2.printStackTrace();
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		while(esperando) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			
			while(novamente == 0) {
				int posicao = 0;
				int regra;
				int ganhador = -1;
				win.setJogador(jogador);
				
				win.setMensagemEnviada("Bem vindo ao Mancala");
				win.setMensagemEnviada("Você e o Jogador "+(jogador+1));
				mensagemEnv = "";
				
				win.salvaTabuleiro();
				salvaJogador();
				
				while(mensagemEnv!="sur:>"&&!fim) {
					if(mensagemEnv.contains("msg:>")&&mensagemEnv.indexOf(">")==4) {
						oponente.setMensagemRec((mensagemEnv.substring(0, 5)+"Jogador "+(jogador+1)+": "+mensagemEnv.substring(5)).replaceFirst("msg:>", ""));
					}
					else if(mensagemEnv.contains("int:>")&&mensagemEnv.indexOf(">")==4&&this.jogador==jogando) {
						posicao = Integer.parseInt(mensagemEnv.replaceFirst("int:>", ""));
						tabuleiro = win.getTabuleiro();
						regra = Mancala.jogar(jogando, posicao, tabuleiro);
						ganhador = Mancala.verificaVazios(tabuleiro);
						if(ganhador!=-1) {
							fim = true;
							jogando = ganhador;
						}
						else if(jogando==jogador1&&regra!=1) {
							jogando = jogador2;
						}
						else if(regra!=2){
							jogando = jogador1;
						}
						win.salvaTabuleiro();
						salvaJogador();
						oponente.setTabuleiro(tabuleiro);
						oponente.setJogador(jogando);
						if(fim) {
							oponente.finalizaJogo();
						}
					}
					else if(mensagemEnv.contains("vol:>")&&mensagemEnv.indexOf(">")==4&&this.jogador==jogando) {
						win.voltaJogada();
						voltaJogada();
						oponente.voltarJogada();
					}
					else if(mensagemEnv.contains("sai:>")&&mensagemEnv.indexOf(">")==4) {
						oponente.notificaSaida(this.jogador);
					}
					win.turno(jogando);
					win.atualizaInterface();
					if(!fim) {
						mensagemEnv = win.getMensagem();
					}
					if(mensagemEnv=="sur:>"&&Notificacao.confirmaDesistencia()==1) {
						mensagemEnv = "";
					}
				}
				if(mensagemEnv=="sur:>") {
					if(jogador==jogador1) {
						oponente.setJogador(jogador2);
					}
					else {
						oponente.setJogador(jogador1);
					}
					oponente.desistir(jogador);
					Notificacao.derrota();
				}
				else if(jogando==jogador) {
					Notificacao.vitoria();
				}
				else if(jogando==2) {
					Notificacao.empate();
				}
				else {
					Notificacao.derrota();
				}
				
				novamente = Notificacao.confirmaJogarNovamente();
				
				if(novamente == 0) {
					int[][] newTabuleiro ={{0,4,4,4,4,4,4},
										   {4,4,4,4,4,4,0}}; //0 a 6
					win.setTabuleiro(newTabuleiro);
					this.jogando = 0;
					fim = false;
				}
			}
			oponente.notificaSaida(jogador);
			System.exit(0);
		} catch(RemoteException e) {
			if(novamente == 0) {
				Notificacao.pendencia();
			}
			System.exit(0);
			while(true) {
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void conecta() throws MalformedURLException, RemoteException, NotBoundException {
		
		oponente = (Remoto)registro.lookup("//"+host+":"+porta+"/Cliente");

		win.setMensagemEnviada("Jogador "+(jogador2+1)+" conectado");
	}
	
	public void setMensagemRec(String mensagemRec) throws RemoteException {
		win.setMensagemEnviada(mensagemRec);
		win.atualizaInterface();
	}
	
	public void setTabuleiro(int[][] tabuleiro) throws RemoteException {
		win.setTabuleiro(tabuleiro);
		win.salvaTabuleiro();
		win.atualizaInterface();
	}
	
	public void setJogador(int jogador) throws RemoteException {
		this.jogando = jogador;
		win.turno(jogando);
		salvaJogador();
		win.atualizaInterface();
	}
	
	public void desistir(int jogador) throws RemoteException {
		Notificacao.desistir(jogador);
		if(this.jogador!=jogador){
			fim = true;
			win.finaliza();
		}
		win.atualizaInterface();
	}
	
	public void finalizaJogo() throws RemoteException {
		fim = true;
		win.finaliza();
		win.atualizaInterface();
	}
	
	public void voltarJogada() throws RemoteException {
		win.voltaJogada();
		voltaJogada();
		win.turno(jogando);
		win.atualizaInterface();
	}
	
	public void notificaSaida(int jogador) throws RemoteException {
		Notificacao.saida(jogador);
		if(this.jogador!=jogador){
			fim = true;
			this.jogando = this.jogador;
			win.finaliza();
		}
		win.atualizaInterface();
	}
	
	public void saiEspera() throws RemoteException {
		esperando = false;
	}

	private void salvaJogador() {
		if(historico.size()>=5) {
			historico.remove(0);
		}
		historico.add(jogando);
	}
	
	private void voltaJogada() {
		if(historico.isEmpty()) {
			win.setMensagemEnviada("Não é possivel mais voltar jogadas");
		}
		else {
			jogando = historico.get(historico.size()-1);
			historico.remove(historico.size()-1);
		}
	}
	
	private void createRunnable() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					win.getJFrame().setVisible(true);
					win.varreBotao();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

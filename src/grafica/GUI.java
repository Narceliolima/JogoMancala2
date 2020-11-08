package grafica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Color;

public class GUI {

	private JFrame frame;
	private int[][] tabuleiro;
	private ArrayList<int[][]> historico;
	private JTextField textoChat;
	private JTextArea chat;
	private JScrollPane scroll;
	private boolean pressionado = false;
	private String mensagem;
	private JButton desistir;
	private JButton voltarVez;
	private int jogador;
	String[] cores = {"#EA9D86","#87DAC4"};
	//--------------------------------------------//--------------------------------------------//
	//private ArrayList<JButton> botoes; //Refinar...
	private JButton botao01;
	private JButton botao02;
	private JButton botao03;
	private JButton botao04;
	private JButton botao05;
	private JButton botao06;
	private JButton botao10;
	private JButton botao11;
	private JButton botao12;
	private JButton botao13;
	private JButton botao14;
	private JButton botao15;
	//--------------------------------------------//--------------------------------------------//
	private JLabel placar00;
	private JLabel placar16;
	private JLabel jogando;
	private JLabel jogador1;
	private JLabel jogador2;
	//--------------------------------------------//--------------------------------------------//
	
	public GUI(int[][] tabuleiro) {
		this.tabuleiro = tabuleiro;
		this.historico = new ArrayList<int[][]>();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1244, 685); //1024x682
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		iniciaBotoes();
		iniciaPlacar();
		iniciaChat();
		
		JLabel planoDeFundo = new JLabel("Tabuleiro Mancala");
		planoDeFundo.setBounds(208, 0, 1024, 648);
		planoDeFundo.setIcon(new ImageIcon(getClass().getResource("imagens/Mancala.png")));
		frame.getContentPane().add(planoDeFundo);
	}
	
	public void atualizaInterface() {
		
		botao01.setText(tabuleiro[0][1]+"");
		botao02.setText(tabuleiro[0][2]+"");
		botao03.setText(tabuleiro[0][3]+"");
		botao04.setText(tabuleiro[0][4]+"");
		botao05.setText(tabuleiro[0][5]+"");
		botao06.setText(tabuleiro[0][6]+"");
		botao10.setText(tabuleiro[1][0]+"");
		botao11.setText(tabuleiro[1][1]+"");
		botao12.setText(tabuleiro[1][2]+"");
		botao13.setText(tabuleiro[1][3]+"");
		botao14.setText(tabuleiro[1][4]+"");
		botao15.setText(tabuleiro[1][5]+"");
		placar00.setText(tabuleiro[0][0]+"");
		placar16.setText(tabuleiro[1][6]+"");
		
	}
	
	public void varreBotao() {
		
		ActionListener ato = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == botao01&&jogador==0) {
					mensagem = "int:>1";
				}
				else if(arg0.getSource() == botao02&&jogador==0) {
					mensagem = "int:>2";
				}
				else if(arg0.getSource() == botao03&&jogador==0) {
					mensagem = "int:>3";
				}
				else if(arg0.getSource() == botao04&&jogador==0) {
					mensagem = "int:>4";
				}
				else if(arg0.getSource() == botao05&&jogador==0) {
					mensagem = "int:>5";
				}
				else if(arg0.getSource() == botao06&&jogador==0) {
					mensagem = "int:>6";
				}
				else if(arg0.getSource() == botao10&&jogador==1) {
					mensagem = "int:>0";
				}
				else if(arg0.getSource() == botao11&&jogador==1) {
					mensagem = "int:>1";
				}
				else if(arg0.getSource() == botao12&&jogador==1) {
					mensagem = "int:>2";
				}
				else if(arg0.getSource() == botao13&&jogador==1) {
					mensagem = "int:>3";
				}
				else if(arg0.getSource() == botao14&&jogador==1) {
					mensagem = "int:>4";
				}
				else if(arg0.getSource() == botao15&&jogador==1) {
					mensagem = "int:>5";
				}
				else if(arg0.getSource() == desistir) {
					mensagem = "sur:>";
				}
				else if(arg0.getSource() == voltarVez) {
					mensagem = "vol:>";
				}
				else {
					mensagem = "null:>";
				}
				pressionado = true;
			}
		};
		
		textoChat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chat.append("Jogador "+(jogador+1)+": "+arg0.getActionCommand()+"\n");
				mensagem = "msg:>"+arg0.getActionCommand();
				textoChat.setText("");
				pressionado = true;
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mensagem = "sai:>";
				pressionado = true;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		botao01.addActionListener(ato);
		botao02.addActionListener(ato);
		botao03.addActionListener(ato);
		botao04.addActionListener(ato);
		botao05.addActionListener(ato);
		botao06.addActionListener(ato);
		
		botao10.addActionListener(ato);
		botao11.addActionListener(ato);
		botao12.addActionListener(ato);
		botao13.addActionListener(ato);
		botao14.addActionListener(ato);
		botao15.addActionListener(ato);
		
		desistir.addActionListener(ato);
		voltarVez.addActionListener(ato);
		
	}
	
	public String getMensagem()  {
		//Busy wait
		while(!pressionado) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		pressionado = false;
		return this.mensagem;
	}
	
	public void setMensagemEnviada(String mensagem) {
		chat.append(mensagem+"\n");
		chat.setCaretPosition(chat.getText().length());
	}
	
	public void setTabuleiro(int[][] tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	public int[][] getTabuleiro(){
		return tabuleiro;
	}
	
	public void setJogador(int jogador) {
		this.jogador = jogador;
	}
	
	public void turno(int jogando) {
		if(jogador==jogando) {
			this.jogando.setText("Sua vez");
		}
		else {
			this.jogando.setText("Vez do oponente");
		}
		this.jogando.setForeground(Color.decode(cores[jogando]));
	}
	
	public void salvaTabuleiro() {
		if(historico.size()>=5) {
			historico.remove(0);
		}
		historico.add(copiaTabuleiro(tabuleiro));
	}
	
	public void voltaJogada() {
		if(historico.isEmpty()) {
			//Num faz nada
		}
		else {
			tabuleiro = historico.get(historico.size()-1);
			historico.remove(historico.size()-1);
		}
	}
	
	public JFrame getJFrame() {
		return this.frame;
	}
	
	public void finaliza() {
		mensagem = "fim:>";
		pressionado = true;
	}
	
	private void iniciaBotoes() {
		frame.getContentPane().setLayout(null);
		
		botao01 = new JButton(this.tabuleiro[0][1]+"");
		botao01.setForeground(Color.decode("#EA9D86"));
		botao01.setFont(new Font("Dialog", Font.BOLD, 30));
		botao01.setBounds(390, 187, 76, 72);
		botao01.setOpaque(false);
		botao01.setContentAreaFilled(false);
		botao01.setBorderPainted(false);
		frame.getContentPane().add(botao01);
		
		botao02 = new JButton(tabuleiro[0][2]+"");
		botao02.setForeground(Color.decode("#EA9D86"));
		botao02.setFont(new Font("Dialog", Font.BOLD, 30));
		botao02.setBounds(507, 187, 76, 72);
		botao02.setOpaque(false);
		botao02.setContentAreaFilled(false);
		botao02.setBorderPainted(false);
		frame.getContentPane().add(botao02);
		
		botao03 = new JButton(tabuleiro[0][3]+"");
		botao03.setForeground(Color.decode("#EA9D86"));
		botao03.setFont(new Font("Dialog", Font.BOLD, 30));
		botao03.setBounds(627, 187, 76, 72);
		botao03.setOpaque(false);
		botao03.setContentAreaFilled(false);
		botao03.setBorderPainted(false);
		frame.getContentPane().add(botao03);
		
		botao04 = new JButton(tabuleiro[0][4]+"");
		botao04.setForeground(Color.decode("#EA9D86"));
		botao04.setFont(new Font("Dialog", Font.BOLD, 30));
		botao04.setBounds(749, 187, 76, 72);
		botao04.setOpaque(false);
		botao04.setContentAreaFilled(false);
		botao04.setBorderPainted(false);
		frame.getContentPane().add(botao04);
		
		botao05 = new JButton(tabuleiro[0][5]+"");
		botao05.setForeground(Color.decode("#EA9D86"));
		botao05.setFont(new Font("Dialog", Font.BOLD, 30));
		botao05.setBounds(869, 187, 76, 72);
		botao05.setOpaque(false);
		botao05.setContentAreaFilled(false);
		botao05.setBorderPainted(false);
		frame.getContentPane().add(botao05);
		
		botao06 = new JButton(tabuleiro[0][6]+"");
		botao06.setForeground(Color.decode("#EA9D86"));
		botao06.setFont(new Font("Dialog", Font.BOLD, 30));
		botao06.setBounds(988, 187, 76, 72);
		botao06.setOpaque(false);
		botao06.setContentAreaFilled(false);
		botao06.setBorderPainted(false);
		frame.getContentPane().add(botao06);
		
		botao10 = new JButton(Integer.toString(tabuleiro[1][0]));
		botao10.setForeground(Color.decode("#87DAC4"));
		botao10.setFont(new Font("Dialog", Font.BOLD, 30));
		botao10.setBounds(390, 393, 76, 72);
		botao10.setOpaque(false);
		botao10.setContentAreaFilled(false);
		botao10.setBorderPainted(false);
		frame.getContentPane().add(botao10);
		
		botao11 = new JButton(Integer.toString(tabuleiro[1][1]));
		botao11.setForeground(Color.decode("#87DAC4"));
		botao11.setFont(new Font("Dialog", Font.BOLD, 30));
		botao11.setBounds(507, 393, 76, 72);
		botao11.setOpaque(false);
		botao11.setContentAreaFilled(false);
		botao11.setBorderPainted(false);
		frame.getContentPane().add(botao11);
		
		botao12 = new JButton(Integer.toString(tabuleiro[1][2]));
		botao12.setForeground(Color.decode("#87DAC4"));
		botao12.setFont(new Font("Dialog", Font.BOLD, 30));
		botao12.setBounds(627, 393, 76, 72);
		botao12.setOpaque(false);
		botao12.setContentAreaFilled(false);
		botao12.setBorderPainted(false);
		frame.getContentPane().add(botao12);
		
		botao13 = new JButton(Integer.toString(tabuleiro[1][3]));
		botao13.setForeground(Color.decode("#87DAC4"));
		botao13.setFont(new Font("Dialog", Font.BOLD, 30));
		botao13.setBounds(749, 393, 76, 72);
		botao13.setOpaque(false);
		botao13.setContentAreaFilled(false);
		botao13.setBorderPainted(false);
		frame.getContentPane().add(botao13);
		
		botao14 = new JButton(Integer.toString(tabuleiro[1][4]));
		botao14.setForeground(Color.decode("#87DAC4"));
		botao14.setFont(new Font("Dialog", Font.BOLD, 30));
		botao14.setBounds(869, 393, 76, 72);
		botao14.setOpaque(false);
		botao14.setContentAreaFilled(false);
		botao14.setBorderPainted(false);
		frame.getContentPane().add(botao14);
		
		botao15 = new JButton(Integer.toString(tabuleiro[1][5]));
		botao15.setForeground(Color.decode("#87DAC4"));
		botao15.setFont(new Font("Dialog", Font.BOLD, 30));
		botao15.setBounds(988, 393, 76, 72);
		botao15.setOpaque(false);
		botao15.setContentAreaFilled(false);
		botao15.setBorderPainted(false);
		frame.getContentPane().add(botao15);
		
		desistir = new JButton("Desistir");
		desistir.setBounds(221, 556, 101, 25);
		frame.getContentPane().add(desistir);
		
		voltarVez = new JButton("Voltar");
		voltarVez.setBounds(221, 593, 101, 25);
		frame.getContentPane().add(voltarVez);
	}
	
	private void iniciaPlacar() {
		
		placar00 = new JLabel(this.tabuleiro[0][0]+"");
		placar00.setForeground(Color.decode("#EA9D86"));
		placar00.setFont(new Font("Dialog", Font.BOLD, 30));
		placar00.setHorizontalAlignment(SwingConstants.CENTER);
		placar00.setBounds(278, 349, 57, 65);
		frame.getContentPane().add(placar00);
		
		placar16 = new JLabel(this.tabuleiro[1][6]+"");
		placar16.setForeground(Color.decode("#87DAC4"));
		placar16.setBounds(1119, 232, 57, 65);
		placar16.setFont(new Font("Dialog", Font.BOLD, 30));
		placar16.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(placar16);
		
		jogando = new JLabel("Esperando...");
		jogando.setHorizontalAlignment(SwingConstants.CENTER);
		jogando.setFont(new Font("Dialog", Font.BOLD, 30));
		jogando.setBounds(573, 301, 332, 36);
		frame.getContentPane().add(jogando);
		
		jogador1 = new JLabel("Jogador 1");
		jogador1.setForeground(Color.decode("#EA9D86"));
		jogador1.setHorizontalAlignment(SwingConstants.CENTER);
		jogador1.setFont(new Font("Dialog", Font.BOLD, 30));
		jogador1.setBounds(573, 100, 332, 36);
		frame.getContentPane().add(jogador1);
		
		jogador2 = new JLabel("Jogador 2");
		jogador2.setForeground(Color.decode("#87DAC4"));
		jogador2.setHorizontalAlignment(SwingConstants.CENTER);
		jogador2.setFont(new Font("Dialog", Font.BOLD, 30));
		jogador2.setBounds(573, 512, 332, 36);
		frame.getContentPane().add(jogador2);
		
	}
	
	private void iniciaChat() {
		
		chat = new JTextArea();
		chat.setEditable(false);
		chat.setBounds(1, 1, 187, 580);
		frame.getContentPane().add(chat);
		
		scroll = new JScrollPane(chat);
		scroll.setBounds(12, 12, 190, 569);
		frame.getContentPane().add(scroll);
		
		textoChat = new JTextField();
		textoChat.setBounds(12, 593, 187, 25);
		frame.getContentPane().add(textoChat);
		textoChat.setColumns(10);
	}
	
	private int[][] copiaTabuleiro(int[][] tabuleiro){
		
		int[][] cTabuleiro = new int[2][7];
		int i,j;
		
		for(i=0;i<cTabuleiro.length;i++) {
			for(j=0;j<cTabuleiro[i].length;j++) {
				cTabuleiro[i][j] = tabuleiro[i][j];
			}
		}
		return cTabuleiro;
	}
}

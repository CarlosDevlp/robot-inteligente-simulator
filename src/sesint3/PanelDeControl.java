package sesint3;

import sesint3.behaviors.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javaclient3.PlayerException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.Timer;
import sesint3.mapping.GridMap;
import sesint3.mapping.Mapper;

public class PanelDeControl extends JFrame implements ActionListener {

	// wander objWander = new wander();
	
	 //Blobfinder objBlobfinder;
	// WallFollower objWallFollower = new WallFollower();
		
	//JButton btnWander;
	
	//, btnBlobfinder, btnWallfollower;
	//JTextArea txtArea;
	//agregados arriba
	JButton btnAvanzar, btnDerecha, btnIzquierda, btnRetroceder, btnApagar, btnEncender;
	JComboBox<String> cmbRadar;
	JTextField txtServer;
	JRadioButton rbTeclado;
	Controlador control;
        
        GraficoLineaView graficoLineaView;
        double distanciaAObjeto=0;
        double distanciaAObjetoIzq=0;
        double distanciaAObjetoDer=0;
        int contadorSegundos=0;
        
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPort;
        private Timer timer;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelDeControl frame = new PanelDeControl();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PanelDeControl() {

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 766, 711);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rbTeclado = new JRadioButton("Activar Movimiento Numerico ");
		rbTeclado.setFont(new Font("Dialog", Font.BOLD, 16));
		rbTeclado.setForeground(UIManager.getColor("Button.light"));
		rbTeclado.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(rbTeclado.isSelected())
				{
			
					int c = e.getKeyChar();
					String accion=" ";
					if(c == '4' )
						accion="Robot gira a la Izquierda";
					if(c == '2' )
						accion="Robot Retrocede";
					if(c == '6' )
						accion="Robot gira a la Derecha";
					if(c == '8' )
						accion="Robot Avanza";
					control.movimiento(accion);
					//Robot Avanza
					//Robot Retrocede
					//Robot gira a la Izquierda
					//Robot gira a la Derecha
					
				}
			}
		});
                
                
                
		rbTeclado.setBackground(Color.BLACK);
		rbTeclado.setBounds(230, 334, 321, 28);
		contentPane.add(rbTeclado);
		
		txtServer = new JTextField();
		txtServer.setForeground(new Color(51, 51, 51));
		txtServer.setFont(new Font("Dialog", Font.BOLD, 16));
		txtServer.setText("localhost");
		txtServer.setBounds(96, 66, 154, 31);
		contentPane.add(txtServer);
		txtServer.setColumns(10);

		btnAvanzar = new JButton("Robot Avanza");
		btnAvanzar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAvanzar.setBounds(282, 157, 209, 40);
		btnAvanzar.addActionListener(this);
		contentPane.add(btnAvanzar);

		btnIzquierda = new JButton("Robot gira a la Izquierda");
		btnIzquierda.setFont(new Font("Dialog", Font.BOLD, 14));
		btnIzquierda.setBounds(87, 209, 238, 40);
		btnIzquierda.addActionListener(this);
		contentPane.add(btnIzquierda);

		btnDerecha = new JButton("Robot gira a la Derecha");
		btnDerecha.setFont(new Font("Dialog", Font.BOLD, 14));
		btnDerecha.setBounds(457, 209, 238, 39);
		btnDerecha.addActionListener(this);
		contentPane.add(btnDerecha);

		btnRetroceder = new JButton("Robot Retrocede");
		btnRetroceder.setFont(new Font("Dialog", Font.BOLD, 14));
		btnRetroceder.setBounds(282, 261, 209, 40);
		btnRetroceder.addActionListener(this);
		contentPane.add(btnRetroceder);

		btnEncender = new JButton("ENCENDIDO");
		btnEncender.setFont(new Font("Dialog", Font.BOLD, 16));
		btnEncender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(rbTeclado.isSelected())
				{
					rbTeclado.setFocusable(true);
					rbTeclado.requestFocus();
				}
				else
					btnApagar.requestFocus();
				try
				{
					String server = txtServer.getText();
					int port = Integer.parseInt(txtPort.getText());
					System.out.println(port + "     " + server);
					control = new Controlador(port, server);
					control.setIndexRadar(cmbRadar.getSelectedIndex());
					activarGraficador();
                                        control.encender();
					activarBotones();
                                        
                                        
                                        //empezar el mapper
                                        GridMap gridmap=new GridMap(40,40,0.05,server,port);
                                        gridmap.setVisible(true);

                                        Thread thread=new Thread(new Runnable(){
                                            @Override
                                            public void run() {
                                              Mapper mapper=new Mapper(gridmap);
                                            }

                                        });

                                        thread.start();
                                        
                                        
				}
				catch(PlayerException e2)
				{
					JOptionPane.showMessageDialog(null, "Error: " + e2.getCause(), "Information", 1);
				}
			}
		});
		btnEncender.setBounds(590, 69, 138, 25);
		contentPane.add(btnEncender);
                                
		btnApagar = new JButton("APAGAR");
		btnApagar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					control.apagar();
				} catch (PlayerException e2) {
					JOptionPane.showMessageDialog(null, "Error: " + e2.getCause(), "Information", 1);
				}
				desactivarBotones();
			}
		});
		btnApagar.setBounds(590, 118, 138, 25);
                
                //guardar estado
                JButton btnGuardarEstado = new JButton("GUARDAR");
		btnGuardarEstado.setFont(new Font("Dialog", Font.BOLD, 16));
                btnGuardarEstado.setBounds(590, 260, 138, 25);
                btnGuardarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                                System.out.println("comenzando a guardar");
                                String x=String.format("%.2f", Controlador.ROBOT_X);
                                String y=String.format("%.2f", Controlador.ROBOT_Y);
                                int lineaDelArchivo;
                                String port = txtPort.getText();
                                switch(port){                                                                                                            
                                    case "6666":
                                        lineaDelArchivo=69;
                                    break;
                                    
                                    case "6667":
                                        lineaDelArchivo=88;
                                    break;
                                    
                                     //case "6665":
                                    default:
                                        lineaDelArchivo=47;
                                }
                                control.guardarPosicion(lineaDelArchivo, "pose [ "+x+" "+y+" 0 90.000 ] ");
                                control.apagar();
                                timer.stop();
			}
		});
                contentPane.add(btnGuardarEstado);
                
                //cargar estado
                /*
                JButton btnCargarEstado = new JButton("CARGAR");
		btnCargarEstado.setFont(new Font("Dialog", Font.BOLD, 16));
                btnCargarEstado.setBounds(590, 300, 138, 25);
                contentPane.add(btnCargarEstado);
                */
		contentPane.add(btnApagar);
                
                

		cmbRadar = new JComboBox<String>();
		cmbRadar.setFont(new Font("Dialog", Font.BOLD, 20));
		cmbRadar.setBounds(337, 211, 108, 38);
		cmbRadar.setModel(new DefaultComboBoxModel<String>(new String[] { "Sonar", "Laser" }));
		contentPane.add(cmbRadar);
		
		txtPort = new JTextField();
		txtPort.setForeground(new Color(51, 51, 51));
		txtPort.setFont(new Font("Dialog", Font.BOLD, 16));
		txtPort.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				int lim = 4;
				if(txtPort.getSelectedText() != null)
				{
					if(!Character.isDigit(c))
						e.consume();
				}
				else
					if(!Character.isDigit(c) || txtPort.getText().length()>=lim )
						e.consume();
			}
		});
		txtPort.setText("6665");
		txtPort.setEditable(false);
		txtPort.setColumns(10);
		txtPort.setBounds(96, 115, 154, 31);
		contentPane.add(txtPort);
		
		JLabel lblServer = new JLabel("Server");
		lblServer.setFont(new Font("Dialog", Font.BOLD, 18));
		lblServer.setForeground(Color.BLACK);
		lblServer.setBounds(12, 74, 70, 15);
		contentPane.add(lblServer);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPort.setForeground(Color.BLACK);
		lblPort.setBounds(12, 123, 46, 15);
		contentPane.add(lblPort);
		
		JLabel lblProyectoTSistemas = new JLabel("PROYECTO T3 SISTEMAS INTELIGENTES xd");
		lblProyectoTSistemas.setBackground(Color.LIGHT_GRAY);
		lblProyectoTSistemas.setFont(new Font("Dialog", Font.BOLD, 18));
		lblProyectoTSistemas.setBounds(156, 12, 528, 31);
		contentPane.add(lblProyectoTSistemas);
		
		JPanel panelGrafico = new JPanel();
		panelGrafico.setBounds(0, 371, 764, 240);
		contentPane.add(panelGrafico);
		panelGrafico.setLayout(new BorderLayout(0, 0));

		desactivarBotones();
		
		 //generando grafico
                graficoLineaView=new GraficoLineaView("Distancia del robot a un objeto", "Tiempo", "Distancia");                
                graficoLineaView.generarGrafico();
                graficoLineaView.setearGraficoEnPanel(panelGrafico);
	}

        void activarGraficador(){
        //sucede algun cambio con la distancia detectada al objeto
                control.setCallback(new Callback(){
                    @Override
                    public void execute(Object []args) {
                      distanciaAObjeto=(double) args[0];
                      distanciaAObjetoIzq=(double) args[1];
                      distanciaAObjetoDer=(double) args[2];
                    }
                });
                
                timer=new Timer(500, new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        contadorSegundos++;
                        graficoLineaView.agregarParOrdenado(contadorSegundos, distanciaAObjeto);
                        graficoLineaView.agregarParOrdenadoEnSerie2(contadorSegundos, distanciaAObjetoIzq);
                        graficoLineaView.agregarParOrdenadoEnSerie3(contadorSegundos, distanciaAObjetoDer);
                    }
                    
                });
                
                timer.start();
        }
        
	void activarBotones() {
		btnIzquierda.setEnabled(true);
		btnDerecha.setEnabled(true);
		btnAvanzar.setEnabled(true);
		btnRetroceder.setEnabled(true);
		btnEncender.setEnabled(false);
		btnApagar.setEnabled(true);
		txtServer.setEditable(false);
		txtPort.setEditable(false);
		cmbRadar.setEnabled(false);
	}

	void desactivarBotones() {
		btnIzquierda.setEnabled(false);
		btnDerecha.setEnabled(false);
		btnAvanzar.setEnabled(false);
		btnRetroceder.setEnabled(false);
		btnEncender.setEnabled(true);
		btnApagar.setEnabled(false);
		txtServer.setEditable(true);
		txtPort.setEditable(true);
		cmbRadar.setEnabled(true);
	}

	//@Override
	//public void actionPerformed(ActionEvent e) {
		//if(rbTeclado.isSelected())
			//rbTeclado.requestFocus();
		//else
			//btnApagar.requestFocus();
		//control.movimiento(e.getActionCommand());
	//}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(rbTeclado.isSelected())
			rbTeclado.requestFocus();
		else
			btnApagar.requestFocus();
		control.movimiento(e.getActionCommand());
		//String server = txtServer.getText();
		//int port = Integer.parseInt(txtPort.getText());
	
	//	objWallFollower.executeWallFollower(port, server);
		
		//switch (e.getActionCommand()) {
//		case "Wander":
		
		
		//Llamamos al wander
			//JOptionPane.showMessageDialog(null,"1" +e.getActionCommand());
			//llama de la funcion wander lo que es el puerto y el servidor
			//objWander.executeWander(port, server);
			//agregado para q no se pegue el boton
			//btnWander.addActionListener(this);
			//contentPane.add(btnWander);
			//btnWander.setEnabled(true);
			//String text=txtArea.getText();
			
			
			
		//	break;
	//	case "Blobfinder":
			//JOptionPane.showMessageDialog(null,"2" +e.getActionCommand());
			//objBlobfinder = new Blobfinder(port,server);
			//objBlobfinder.executeBlobfinder();
			//break;
		//case "WallFollower":
			//JOptionPane.showMessageDialog(null,"3" +e.getActionCommand());
			//objWallFollower.executeWallFollower(port, server);
			//break;
		//}
		
	}
	protected Color getBtnEncenderBackground() {
		return btnEncender.getBackground();
	}
	protected void setBtnEncenderBackground(Color background) {
		btnEncender.setBackground(background);
	}
}


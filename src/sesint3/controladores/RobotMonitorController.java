/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesint3.controladores;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.swing.JComponent;
import java.util.Timer;
import sesint3.util.Callback;
import sesint3.vistas.FrmRobotMonitor;

/**
 *
 * @author carlos
 */
public class RobotMonitorController implements ActionListener{
    
    private FrmRobotMonitor frmRobotMonitor;
    private RobotControlador robotControlador;
    private GraficadorSeries graficoLineaView;
    private int pasos;
    private String pathDataStoreFolder;
    private ArrayList<String> comandosRecorrido;
    private MapperController mapperController;
    
    public RobotMonitorController(FrmRobotMonitor frmRobotMonitor) {
        frmRobotMonitor.setOnActionListenerToComponents(this);
        frmRobotMonitor.setVisible(true);
        this.frmRobotMonitor=frmRobotMonitor;
        this.robotControlador=new RobotControlador();
        this.pathDataStoreFolder=System.getProperty("user.dir")+"/src/sesint3/assets/datastore/";
        this.pasos=0;
        this.comandosRecorrido=new ArrayList();
        setearGrafico();
        setearMapping();
        cuandoDatosDeDistanciasEstenDisponibles();
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent obj=(JComponent) e.getSource();        
        try{
            switch(obj.getName()){
                case "btnConectar":
                    if(this.robotControlador.estaEncendido())
                        desconectarConRobot();
                    else
                        conectarConRobot();
                break;
                case "btnMoverRobotArriba":
                    moverRobotHaciaAdeltante();
                break;
                
                case "btnMoverRobotDerecha":
                    moverRobotHaciaDerecha();
                break;
                
                case "btnMoverRobotIzquierda":
                    moverRobotHaciaIzquierda();
                break;
                
                case "btnMoverRobotAbajo":
                    moverRobotHaciaAtras();
                break;
                
                case "btnGuardarArchivo":
                    guardarRecorrido();
                break;
                
                case "btnCargarArchivo":
                    cargarRecorrido();
                break;
            }
        }catch(Exception err){
            this.frmRobotMonitor.messageBoxError("Error",err.getMessage());
        }
        
        
    }
    
    //realizar una conexion con el robot que vamos a usar
    private void conectarConRobot() throws Exception{
        String ipServidor= this.frmRobotMonitor.txtIP.getText();
        String puerto= this.frmRobotMonitor.txtPuerto.getText();
        int tipoRadar= this.frmRobotMonitor.cmbRadar.getSelectedIndex();
        
        //error
        if(ipServidor.trim().equals("") || puerto.trim().equals("")){            
            throw new Exception("Debes ingresar un puerto y/o ip de servidor");
        }else{
            this.robotControlador.setPuerto(Integer.parseInt(puerto));            
            this.robotControlador.setServidor(ipServidor);
            this.robotControlador.setTipoRadar(tipoRadar);
            this.robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.ENCENDER);            
            this.frmRobotMonitor.btnConectar.setText("Desconectar");
        }
        
    }
    
    //realizar una desconexion con el robot que estamos usando
    private void desconectarConRobot() {
        this.robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.APAGAR);
        this.frmRobotMonitor.btnConectar.setText("Conectar");
    }
    
    
    private void moverRobotHaciaAdeltante(){
        if(this.robotControlador.estaEncendido()){
            this.robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.AVANZAR);      
            this.comandosRecorrido.add("AVANZAR");
        }
            
    }
    
    private void moverRobotHaciaDerecha(){
        if(this.robotControlador.estaEncendido()){
            this.robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.GIRAR_DERECHA);
            this.comandosRecorrido.add("GIRAR_DERECHA");
        }
            
    }
        
    private void moverRobotHaciaIzquierda(){
      if(this.robotControlador.estaEncendido()){
          this.robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.GIRAR_IZQUIERDA);
          this.comandosRecorrido.add("GIRAR_IZQUIERDA");
      }            
    }
        
    private void moverRobotHaciaAtras(){
        if(this.robotControlador.estaEncendido()){
            this.robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.RETROCEDER);
            this.comandosRecorrido.add("RETROCEDER");
        }
            
    }
    
    //setear controlador de grafico en un panel del Formulario Robot monitor
    private void setearGrafico(){
        this.graficoLineaView=new GraficadorSeries("Posicion de Robot", "Pasos", "Distancia del robot a un objeto");
        
        
        //serie de margen izquierda
        this.graficoLineaView.agregarNuevaSerie("Lado izquierdo", Color.RED);                
        //serie de margen frontal
        this.graficoLineaView.agregarNuevaSerie("Lado frente", Color.BLUE);                        
        //serie de margen derecho
        this.graficoLineaView.agregarNuevaSerie("Lado derecho", Color.GREEN);        
        
        this.graficoLineaView.generarGrafico();
        this.graficoLineaView.setearGraficoEnPanel(this.frmRobotMonitor.getGraficoCanvasPanel());                    
    }
    
     //setear callback para graficar datos de distancias del robot cuando estas se encuentren disponibles
    private void cuandoDatosDeDistanciasEstenDisponibles(){           
        this.robotControlador.setCallback((Callback<Double>) (Double[] args) -> {
            this.pasos++;
            for(int i=0;i<3;i++){
                this.graficoLineaView.agregarParOrdenadoASerie(i, this.pasos, args[i]);
            }                
        });
        
        this.robotControlador.setOnDataDistanciasDisponible((Callback<Double>) (Double[] args) -> {
            this.mapperController.dibujar(
                                          this.robotControlador.getX(),                       
                                          this.robotControlador.getY(), 
                                          this.robotControlador.getYaw(), 
                                          args);
        });
    }
    
    private void recrearRecorrido(){
        Timer timer=new Timer();
        
        TimerTask task=new TimerTask(){
            @Override
            public void run() {                
                if(comandosRecorrido.size()>0 && robotControlador.estaEncendido()){                                        
                    switch(comandosRecorrido.get(0)){
                        case "AVANZAR":
                            robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.AVANZAR);
                            break;
                        case "GIRAR_DERECHA":
                            robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.GIRAR_DERECHA);
                            break;
                        case "GIRAR_IZQUIERDA":
                            robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.GIRAR_IZQUIERDA);
                            break;
                        case "RETROCEDER":
                            robotControlador.ejecutarAccionEnThread(RobotControlador.ACCION.RETROCEDER);
                            break;
                    }                
                    comandosRecorrido.remove(0);
                }else{
                    this.cancel();
                }
                
            }            
        };        
        timer.schedule(task,10, 1000);
    }
    
    private void guardarRecorrido() throws Exception{                
        String nombreArchivo=this.frmRobotMonitor.txtNombreArchivo.getText();                        
        FileWriter fichero = new FileWriter(this.pathDataStoreFolder+nombreArchivo);
        PrintWriter pw = new PrintWriter(fichero);            
        this.comandosRecorrido.forEach((comando) -> {
            pw.println(comando);
        });            
        fichero.close();
        //apagar el robot
        desconectarConRobot();               
        this.frmRobotMonitor.messageBox("Exito", "El archivo se ha guardado con exito.");
    }    
    
    private void cargarRecorrido() throws Exception{    
        String nombreArchivo=this.frmRobotMonitor.txtNombreArchivo.getText();                        
        File archivo = new File (this.pathDataStoreFolder+nombreArchivo);
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);                  
        String linea;
        while((linea=br.readLine())!=null){
           this.comandosRecorrido.add(linea);
        }
        //volver a ejecutar recorrido
        this.recrearRecorrido();
        this.frmRobotMonitor.messageBox("Exito", "El archivo se ha cargado con exito.");
    }    
    
    private void setearMapping(){
        this.mapperController=new MapperController(20,20,0.05);
        this.mapperController.setearMapperEnPanel(this.frmRobotMonitor.canvasMappingPanel);
    }
}

package sesint3.controladores;




import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.ranger.PlayerRangerData;
import sesint3.util.Callback;


public class RobotControlador {
	PlayerClient robot = null;
	Position2DInterface posi = null;
	RangerInterface ranger = null;
        
        private boolean chocando=false;

	
	
	int[]lados = new int[3];
        private Double ladosValores[]= new Double[3];
        private Double []todosLasDistanciasDetectadas;
	int tipoRadar = 0, puerto;
	String servidor;
	private final double MAX_SPEED=5;
        private final double MAX_TURN_RATE=45.0;
        private double speed=0;
        private double turnrate=0;
        private boolean encendido=false;
        private Callback<Double> callback;
        private Callback<Double> onDataDistanciasDisponible;
        public static double ROBOT_X;
        public static double ROBOT_Y;
        public static double ROBOT_ANGULO;
        //hilo de ejecucion del robot
        private InnerRuner innerRunner;
        //constantes
        public static final short SONAR_MODE=0;
        public static final short LASER_MODE=1;
        public static enum ACCION{ ENCENDER,APAGAR,AVANZAR,RETROCEDER,GIRAR_IZQUIERDA,GIRAR_DERECHA,NINGUNA }
        
        
        public RobotControlador() {          
            this.innerRunner=new InnerRuner(this);
	}
        
	public void setTipoRadar(int indexRadar) {
		this.tipoRadar = indexRadar;
	}

        public void setPuerto(int puerto) {
            this.puerto = puerto;
        }

        public void setServidor(String servidor) {
            this.servidor = servidor;
        }

        public void setOnDataDistanciasDisponible(Callback<Double> onDataDistanciasDisponible) {
            this.onDataDistanciasDisponible = onDataDistanciasDisponible;
        }

	
        /**
         * Ejecutar siempre la accion en un hilo aparte
         * @param accionARealizar el nombre de la accion que el robot debe realizar
         */
        public void ejecutarAccionEnThread(ACCION accionARealizar){
           this.innerRunner.setNuevaAccion(accionARealizar);
        }
        
	public void apagar()
	{
            //if(ranger.isDataReady())
            {
                    posi.setMotorPower(0);
                    ranger.setRangerPower(0);
                    ranger = robot.requestInterfaceRanger(tipoRadar, PlayerConstants.PLAYER_CLOSE_MODE);
                    ranger = null;
                    posi = null;
                    robot.close();
                    System.err.println("APAGADO");
            }

            encendido=false;
	}
	
	public void encender()
	{                                
            
            robot = new PlayerClient(servidor,puerto);
            posi = robot.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
            ranger = robot.requestInterfaceRanger(tipoRadar, PlayerConstants.PLAYER_OPEN_MODE);
            ranger.setRangerPower(1);
            posi.setMotorPower(1);

            //dependiendeo del tipo de ranger seleccionado, definir los lados
            switch (ranger.getDeviceAddress().getIndex())
            {
                    case SONAR_MODE:	lados[0] = 0;   lados[1] = 3;  lados[2] = 7;	break; //max 16 detectores
                    case LASER_MODE:	lados[0] = 0; lados[1] = 89; lados[2] = 179;	break;  //max 180 detectores
            }

            robot.runThreaded(-1, -1);
            encendido=true;
            System.err.println("ENCENDIENDO");      
	}

        
        /**
         * Estado de encendido
         * @return estado de boton encendido y apagado
         * 
        */
        public boolean estaEncendido() {
            return encendido;
        }

        public double getSpeed() {
            return speed;
        }

        public double getTurnrate() {
            return turnrate;
        }

        public double getX(){
            return posi.getX();
        }
        
        public double getY(){
            return posi.getY();
        }
        
        public double getYaw(){
            return posi.getYaw();
        }
        
        public void setCallback(Callback callback) {
            this.callback = callback;
        }
        
        
        
	public synchronized void obstaculo()
	{                                       
            if(this.encendido && ranger.isDataReady()){                
                PlayerRangerData rangerData= ranger.getData();
                double[] ranges= rangerData.getRanges();                
                //System.out.println("Adelante: "+ranges[lados[1]]);
                this.chocando = ranges[lados[1]] < 0.5f;                    
            }
            
	}
        
        public void almacenarDatosDeDistancias(){
            if(this.encendido && ranger.isDataReady()){                
                PlayerRangerData rangerData= ranger.getData();
                double[] ranges= rangerData.getRanges();
                int tamanoRanges=ranges.length;
                
                if(this.tipoRadar==RobotControlador.SONAR_MODE){
                    tamanoRanges=8;
                }
                    
                this.todosLasDistanciasDetectadas=new Double[tamanoRanges];
                for(int i=0;i<tamanoRanges;i++){
                    this.todosLasDistanciasDetectadas[i]=ranges[i];
                }
                
                for(int i=0;i<ladosValores.length;i++){
                    ladosValores[i]=ranges[lados[i]];
                }                    
            }
        }
        
	//movimientos        
        public void avanzar(){
            speed = MAX_SPEED;
            turnrate=0;   
            if(!this.chocando){                                    
                mover();
            }else {            
                System.out.println("NO PUEDES AVANZAR ADELANTE");                
            }
            
        }
        
        
        public void retroceder(){
            speed = -1*MAX_SPEED;
            turnrate=0;                        
            mover();
        }
        
        public void girarALaDerecha(){
            speed = 0;
            turnrate=-1*MAX_TURN_RATE;            
            mover();
        }
        
        public void girarALaIzquierda(){
            speed = 0;
            turnrate=MAX_TURN_RATE;            
            mover();
        }
        
        
        private void mover(){
            if(this.posi!=null){
                posi.setCarCMD(speed,turnrate* (double) Math.PI/180);
                speed = 0;
                turnrate=0;
                this.callback.execute(this.ladosValores);
                this.onDataDistanciasDisponible.execute(this.todosLasDistanciasDetectadas);
                try {
                    Thread.sleep(100);
		} catch (Exception e) {
		}
                posi.setSpeed(0,0);
            }            
        }
        
        /**
         * InnerRuner
         * Lleva la batuta de las acciones de la clase padre 
         * en un nuevo thread siempre
         * @author Carlos Chavez Laguna
         */
        private class InnerRuner{
            private RobotControlador robotControlador;
            private Thread robotThread;
            private ACCION accion;
            private boolean conActividad=false;
            public InnerRuner(RobotControlador robotControlador){
                this.robotControlador=robotControlador;
                this.accion=ACCION.NINGUNA;
                robotThread=new Thread(() -> {
                    while(true){
                        //System.out.println(conActividad);
                        synchronized(this){
                            if(this.conActividad){                            
                                this.ejecutarAccion();                           
                            }                            
                            this.robotControlador.obstaculo();
                            this.robotControlador.almacenarDatosDeDistancias();
                        }
                        
                    }                
                });            
                robotThread.start();
            }
            
            
            
            /**
             * Ejecutar siempre la accion en un hilo aparte
             * @param accionARealizar el nombre de la accion que el robot debe realizar
             */
            public synchronized void setNuevaAccion(ACCION accionARealizar){
                this.accion=accionARealizar;                
                this.conActividad=true;
            }
            
            private synchronized void ejecutarAccion() {                  
                System.out.println(this.accion);
                try{
                    switch(this.accion){
                    case APAGAR:
                        this.robotControlador.apagar();
                        break;
                    case ENCENDER:
                        this.robotControlador.encender();
                        break;
                    case AVANZAR:                        
                        this.robotControlador.avanzar();
                        break;
                    case RETROCEDER:                        
                        this.robotControlador.retroceder();
                        break;
                    case GIRAR_IZQUIERDA:                        
                        this.robotControlador.girarALaIzquierda();
                        break;
                    case GIRAR_DERECHA:                        
                        this.robotControlador.girarALaDerecha();
                        break;
                    }                  
                }catch(Exception err){
                    System.out.println(err.getMessage());
                }finally{
                    this.conActividad=false;
                }
                
                
            }
            
        }
        
    
}

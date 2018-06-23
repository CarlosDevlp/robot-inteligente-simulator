package sesint3.behaviors;



import java.io.IOException;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JOptionPane;
import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.PlayerPose2d;
import javaclient3.structures.position2d.PlayerPosition2dCmdPos;
import javaclient3.structures.ranger.PlayerRangerData;
import sesint3.Callback;

public class Controlador {
	PlayerClient robot = null;
	Position2DInterface posi = null;
	RangerInterface ranger = null;
	//ahorita***
	//wander objWander = new wander();
	
	int[]lados = new int[3];
	
	int indexRadar = 0, puerto = 6665;
	String servidor = "localhost";
	private final double MAX_SPEED=0.1;
        private final double MAX_TURN_RATE=5.0;
        private static double speed=0;
        private static double turnrate=0;
        private static boolean encendido=false;
        private Callback callback;
        public static double ROBOT_X;
        public static double ROBOT_Y;
        public static double ROBOT_ANGULO;
        
	public void setIndexRadar(int indexRadar) {
		this.indexRadar = indexRadar;
	}

	public Controlador(int puerto, String servidor) {
		this.puerto = puerto;
		this.servidor = servidor;
	}
	
	public void apagar()
	{
		if(ranger.isDataReady())
		{
			posi.setMotorPower(0);
			ranger.setRangerPower(0);
			ranger = robot.requestInterfaceRanger(indexRadar, PlayerConstants.PLAYER_CLOSE_MODE);
			ranger = null;
			posi = null;
			robot.close();
			System.err.println("APAGADO");
		}
                
                Controlador.encendido=false;
	}
	
	public void encender()
	{
		robot = new PlayerClient(servidor,puerto);
		posi = robot.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
		ranger = robot.requestInterfaceRanger(indexRadar, PlayerConstants.PLAYER_OPEN_MODE);
		ranger.setRangerPower(1);
		posi.setMotorPower(1);
                
                
                
                
		switch (ranger.getDeviceAddress().getIndex())
		{
			case 0:	lados[0] = 5;   lados[1] = 10;  lados[2] = 15;	break;
			case 1:	lados[0] = 120; lados[1] = 240; lados[2] = 360;	break;
		}
		robot.runThreaded(-1, -1);
                Controlador.encendido=true;
                
                Thread thread = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        
                    //PlayerPosition2dCmdPos playerPosition2dCmdPos=new PlayerPosition2dCmdPos();
                    //PlayerPose2d playerPose2d=new PlayerPose2d(5,5,0);                
                    //playerPosition2dCmdPos.setPos(playerPose2d);
                    //posi.setPosition(playerPosition2dCmdPos);
                    
                         robotEnMovimiento();
                    }                    
                });
                
                thread.start();
	}
	
        
        public void robotEnMovimiento(){
            double speedActual,turnRateActual;
            

                
                
            while(Controlador.encendido){
                //if(ranger.isDataReady()){
                        //posi.setSpeed(Controlador.speed, Controlador.turnrate);
                        
                        //para al detectar obstaculo
                        /*if(obstaculo(0, lados[0])){
                            Controlador.speed=0;
                            System.out.println("Obstaculo detectado");
                            posi.setSpeed(0, Controlador.turnrate);
                       }                      */                             
                        
                        if(ranger.isDataReady()) {
				PlayerRangerData rangerData= ranger.getData();
				double[] ranges= rangerData.getRanges();
				//el laser tiene  360 puntos y el sonar 15 puntos
				
				//Medimos la longitud
				if(ranges.length==0)
					continue;
				
				
				
				
				// evitar obstaculo do simple obstacle avoidance
				if(ranges[0]+ranges[1]<ranges[6]+ranges[7])
                                    turnRateActual=-1*turnrate * (double)Math.PI/180.0;
				else
                                    turnRateActual = turnrate*(double)Math.PI/180.0f;
	
				if(ranges[3]<0.5f)
					speedActual=0.0;
				else 
					speedActual=Controlador.speed;
			
				// que el motor establesca la velocidad y el giro
				
                                
                                
				posi.setSpeed(speedActual, turnRateActual);
                                
                                System.out.println("izq: "+ranges[0]);
                                System.out.println("der: "+ranges[7]);
                                
                                try{
                                 ROBOT_X=posi.getX();
                                 ROBOT_Y=posi.getY();
                                 //ROBOT_ANGULO=turnRateActual;s                                 
                                   //System.out.println("angulo: "+ROBOT_ANGULO);
                                }catch(Exception err){
                                }
                                
                                
                                                               //centro  izquierda  derecha
                                callback.execute(new Object[]{ranges[3], ranges[0],ranges[7]});
                                
                                
			}
                //}
                
                
                  //  Controlador.speed=0;
            }        
            
            posi.setSpeed(0,0);
        }

        

        public void setCallback(Callback callback) {
            this.callback = callback;
        }
        
        
        
	public boolean obstaculo(int inicio, int fin)
	{
		boolean obstaculo = false;
		for (int i = inicio; i < fin; i++)
		{
			if(ranger.isDataReady())
				if (ranger.getData().getRanges()[i] < 0.5)
					obstaculo = true;
		}
                
                
		return obstaculo;
	}
	
	public void movimiento(String accion)
	{
		boolean obstaculo = false;
		double speed = 0, turnrate = 0;
		System.out.println("ACCION: " + accion);
		if("Robot gira a la Izquierda".equals(accion))
		{
			Controlador.speed = 0;
			Controlador.turnrate = MAX_TURN_RATE;
			obstaculo = obstaculo(0, lados[0]);
		}
		else if("Robot Avanza".equals(accion))
		{
			//aca es donde esta la velocidad del robot Funcion controlador
			Controlador.speed = MAX_SPEED;			
                        Controlador.turnrate = MAX_TURN_RATE;
			obstaculo = obstaculo(lados[0]+1, lados[1]);								
		}
		else if("Robot Retrocede".equals(accion))
		{
			Controlador.speed = -1*MAX_SPEED;
			Controlador.turnrate = 0;
			System.out.println();
		}
		else if("Robot gira a la Derecha".equals(accion))
		{
			Controlador.speed = 0;
			Controlador.turnrate = -1*MAX_TURN_RATE;
			obstaculo = obstaculo(0, lados[0]);
		}
                
                
		
                
		try
		{
			Thread.sleep(100);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getCause(), "Information", 1);
		}
	}
        
        
        public static void guardarPosicion(int lineNumber, String data) {
            try{
                Path path = Paths.get("/home/carlos/NetBeansProjects/sesinT3/src/sesint3/mapas/simple.world");
                List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                lines.set(lineNumber - 1, data);
                Files.write(path, lines, StandardCharsets.UTF_8);        
            }catch(IOException err){
                //"No se pudo guardar",
                System.out.println(err.getMessage());
            }
            
         
        }
}

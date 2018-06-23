package sesint3.behaviors;

import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.PlayerClient;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.ranger.PlayerRangerData;
public class Wander {
	
    
	

	public void executeWander(int port,String server) {
		
		PlayerClient robot= new PlayerClient(server, port);
		RangerInterface ranger= robot.requestInterfaceRanger(0, PlayerConstants.PLAYER_OPEN_MODE);
		Position2DInterface motor= robot.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
	
		//turn stuff on this night not be necessary
		ranger.setRangerPower(1);
		motor.setMotorPower(1);
	
		while(true) {
			
			float turnRate, speed; // turnRate es el giro en grados ej:45 grados = 0.45
									//speed es la velocidad puede llegar de 0 a 1
		
			//read all the data
			
			robot.readAll();
			
			
			// si el robot ya esta con los datos listo(leidos) obtenemos los datos
			if(ranger.isDataReady()) {
				PlayerRangerData rangerData= ranger.getData();
				double[] ranges= rangerData.getRanges();
				//el laser tiene  360 puntos y el sonar 15 puntos
				
				//Medimos la longitud
				if(ranges.length==0)
					continue;
				
				
				
				
				// evitar obstaculo do simple obstacle avoidance
				if(ranges[0]+ranges[1]<ranges[6]+ranges[7])
				turnRate=-20.0f * (float)Math.PI/180.0f;
				else
					turnRate = 20.0f *(float)Math.PI/180.0f;
	
				if(ranges[3]<0.5f)
					speed=0.0f;
				else 
					speed=0.2f;
			
				// que el motor establesca la velocidad y el giro
				
				motor.setSpeed(speed, turnRate);
			
			}
		}
		
		
	}
	


	public static void main(String[] args) {
		
	
	Wander objWander= new Wander();
	
	objWander.executeWander(6665, "localhost");
	
	
	}


}

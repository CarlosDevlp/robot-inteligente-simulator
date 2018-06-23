package sesint3.mapping;

import javax.swing.JFrame;

import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.ranger.PlayerRangerData;

public class Mapper {
	public Mapper(GridMap MapRepresentation) {
        int port = 6665;
        String server = "localhost";

        PlayerClient robot = new PlayerClient(server, port);
        RangerInterface ranger =  robot.requestInterfaceRanger(0, PlayerConstants.PLAYER_OPEN_MODE);
        Position2DInterface motor = robot.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);

        ranger.setRangerPower(1);
        motor.setMotorPower(1);
        
        
        MapRepresentation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MapRepresentation.pack();
        MapRepresentation.setVisible(true);

        double[] ranges;
        float turnRate, speed;
        
        
        double yaw;
        double[] angles = {90.0, 50.0, 30.0, 10.0, -10.0, -30.0, -50.0, -90.0};
        double[] xpos = new double[8];
        double[] ypos = new double[8];
        double currX, currY, emptyX=0, emptyY=0;
        int grayval=0;
		
        while (true) {
            robot.readAll();
            
            // GET DATA FROM ROBOT; POSITION
            currX = motor.getX();
            currY= motor.getY();
            
            
            PlayerRangerData rangerData;
            if (ranger.isDataReady()) {
                rangerData = ranger.getData();
                ranges = rangerData.getRanges();
    
                if (ranges.length == 0)
                    continue;
               // OBTIENE VALOR GYRO
                yaw = motor.getYaw();
                
                for(int i = 0; i<8; i++) {
                	xpos[i] = currX + ranges[i]* Math.cos(Math.toRadians(yaw+angles[i]));
                	ypos[i] = currY + ranges[i]* Math.sin(Math.toRadians(yaw+angles[i]));
                	emptyX= 0.99*xpos[i];
                	emptyY = 0.99*ypos[i];
                	
                	if(ranges[i]<5.0f) {
                		grayval= MapRepresentation.getVal(xpos[i], ypos[i]);
                		MapRepresentation.setVal(xpos[i], ypos[i], grayval/2);
                	}
                	for(int counter = 0; counter <10; counter++) {
                		emptyX = (((counter*10 + 9)/100.0)*(xpos[i]-currX))+ currX;
                		emptyY = (((counter*10 + 9)/100.0)*(ypos[i]-currY))+ currY;
                		grayval =MapRepresentation.getVal(emptyX, emptyY);
                		MapRepresentation.setVal(emptyX, emptyY, (grayval + 255)/2);
                			
                	}
                	
                }
                MapRepresentation.repaint();

                if (ranges[0] + ranges[1] < ranges[6] + ranges[7])
                    turnRate = -20.0f * ( float )Math.PI / 180.0f;
                else
                    turnRate = 20.0f * ( float )Math.PI / 180.0f;

                if (ranges[3] < 0.5f)
                    speed = 0.0f;
                else
                    speed = 0.25f;
                motor.setSpeed( speed, turnRate );
            }
        }
	}
}

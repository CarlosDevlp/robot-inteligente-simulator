package sesint3.controladores;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import sesint3.vistas.GridMapPanel;

public class MapperController {
    
        private GridMapPanel gridMapPanelView;
        private BufferedImage theMap;
        private int imwidth,imheight;
        private double scale;
        public MapperController(int width,int height,double mpp) {    
          
          imwidth=(int) (width/mpp);
          imheight=(int) (height/mpp);
          scale=mpp;
          theMap=new BufferedImage(imwidth,imheight,BufferedImage.TYPE_INT_ARGB);
          int midgray=(0xff << 24) | (180 << 16) | (180 << 8) | (180);

          for(int x=0; x<imwidth; x++){
              for(int y=0; y<imheight; y++){
                  theMap.setRGB(x, y, midgray);
              }
          }
          gridMapPanelView=new GridMapPanel();
          gridMapPanelView.setImage(theMap);
          gridMapPanelView.setPreferredWidth(imwidth);
          gridMapPanelView.setPreferredHeight(imheight);
	}
        /**
         * Dibujar de acuerdo a los datos ingresados
         * 
         */
        public void dibujar(double x,double y,double yaw,Double[] valores){
            double[] angles = {90.0, 50.0, 30.0, 10.0, -10.0, -30.0, -50.0, -90.0};
            double[] xpos = new double[8];
            double[] ypos = new double[8];
            double emptyX=0, emptyY=0;
            int grayval=0;
            for(int i = 0; i<8; i++) {
                xpos[i] = x + valores[i]* Math.cos(Math.toRadians(yaw+angles[i]));
                ypos[i] = y + valores[i]* Math.sin(Math.toRadians(yaw+angles[i]));
                emptyX= 0.99*xpos[i];
                emptyY = 0.99*ypos[i];
                if(valores[i]<5.0f) {
                        grayval= getVal(xpos[i], ypos[i]);
                        setVal(xpos[i], ypos[i], grayval/2);
                }
                for(int counter = 0; counter <10; counter++) {
                        emptyX = (((counter*10 + 9)/100.0)*(xpos[i]-x))+ x;
                        emptyY = (((counter*10 + 9)/100.0)*(ypos[i]-y))+ y;
                        grayval =getVal(emptyX, emptyY);
                        setVal(emptyX, emptyY, (grayval + 255)/2);
                }
            }
            gridMapPanelView.repaint();
            
            
        }        
        
        private int getVal(double x, double y){
            int value=180;

            int imx = (int) (x/scale + imwidth/2);
            int imy = (int) (imheight/2 + y/scale);
            int rgbval= 0;

            if(imx >=0 && imx < imwidth && imy >=0 && imy < imheight){
                rgbval =theMap.getRGB(imx, imy);
            }

            value=(((rgbval >> 16) & 0x00ff) + ((rgbval >> 8) & 0x0000ff) + (rgbval & 0x000000ff))/3;
            return value;
        }
        
        private  void setVal(double x, double y, int value){
            if(value < 0 || value > 255) return;
            int imx = (int) (x/scale + imwidth/2);
            int imy = (int) (imheight/2 + y/scale);
            int rgbval= (0xff << 24) | (value << 16) | (value << 8) | (value);
            System.out.println("x: "+imx+" y:"+imy);
            if(imx >=0 && imx < imwidth && imy >=0 && imy < imheight){
                theMap.setRGB(imx,imy,rgbval);
            }
        }
        
        public void setearMapperEnPanel(JPanel panel){
            panel.add(gridMapPanelView,BorderLayout.CENTER);
            panel.validate();
        }
}

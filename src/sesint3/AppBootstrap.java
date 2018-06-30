/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesint3;

import sesint3.controladores.RobotMonitorController;
import sesint3.vistas.FrmRobotMonitor;

/**
 * Main - Punto de inicio de la aplicacion
 * @author carlos
 */
public class AppBootstrap {
    public static void main(String args[]) {
        new RobotMonitorController(new FrmRobotMonitor());
    }
}

package sesint3.controladores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author carlos
 */
public class GraficadorSeries {
    private JFreeChart grafico;
    private XYSeriesCollection datasetCollection;
    private ArrayList<XYSeries> dataSets;
    private ArrayList<Color> coloresParaSeries;
    private ChartPanel graficoEnPanelView;
    private String titulo;
    private String tituloEjeX;
    private String tituloEjeY;
    
    public GraficadorSeries(String titulo,String tituloEjeX,String tituloEjeY) {
        this.titulo=titulo;
        this.tituloEjeX=tituloEjeX;
        this.tituloEjeY=tituloEjeY;
        
        //Asignando los datos
        datasetCollection=new XYSeriesCollection();
        dataSets=new ArrayList();             
        coloresParaSeries =new ArrayList();
    }
    
    public void agregarNuevaSerie(String leyenda,Color color){
        dataSets.add(new XYSeries(leyenda));
        coloresParaSeries.add(color);
        
    }
    
    //Asignando los datos    
    public void agregarParOrdenadoASerie(int indiceDeSerie,double x,double y){
        dataSets.get(indiceDeSerie).add(x,y);
    }
        
    
    public void generarGrafico(){
        //generar grafico
        grafico= ChartFactory.createXYLineChart(titulo, tituloEjeX, tituloEjeY, datasetCollection, PlotOrientation.VERTICAL, true,true,false);
        
        
        //agregar cada serie al grafico
        dataSets.forEach((dataSet) -> {
            datasetCollection.addSeries(dataSet);
        });
            
        
        
        //configurar generador de plots
        XYPlot generadorPlot=(XYPlot) grafico.getPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) generadorPlot.getRenderer();                
        //configurar cada color de la serie del grafico
        for(int i=0;i<coloresParaSeries.size();i++){
            renderer.setSeriesShapesVisible(i, true);
            renderer.setSeriesPaint(i, coloresParaSeries.get(i));
        }
            
        
        
        generadorPlot.setDomainGridlinePaint(Color.BLUE);
	generadorPlot.setRangeGridlinePaint(Color.RED);
        
        
        ValueAxis rangeAxis = generadorPlot.getRangeAxis();
        rangeAxis.setRange(0,5);
        
        
        //panel que contendra al grafico a mostrar
        graficoEnPanelView=new ChartPanel(grafico);    
    }
    
    //se seteara el panel generado por el JFreeChart a un panel del formulario principal
    public void setearGraficoEnPanel(JPanel panel){
        panel.add(graficoEnPanelView,BorderLayout.CENTER);
        panel.validate();
    }
    
}

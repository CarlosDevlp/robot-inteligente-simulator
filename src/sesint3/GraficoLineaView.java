/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesint3;

import java.awt.BorderLayout;
import java.awt.Color;
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
public class GraficoLineaView {
    private JFreeChart grafico;
    private XYSeriesCollection datasetCollection;
    private XYSeries dataSet;
    private XYSeries dataSet2;
    private XYSeries dataSet3;
    private ChartPanel graficoEnPanelView;
    private String titulo;
    private String tituloEjeX;
    private String tituloEjeY;
    
    public GraficoLineaView(String titulo,String tituloEjeX,String tituloEjeY) {
        this.titulo=titulo;
        this.tituloEjeX=tituloEjeX;
        this.tituloEjeY=tituloEjeY;
        
        //Asignando los datos
        datasetCollection=new XYSeriesCollection();
        dataSet=new XYSeries("SeriesA");
        dataSet2=new XYSeries("SeriesB");
        dataSet3=new XYSeries("SeriesC");
        
    }
    
    //Asignando los datos
    public void agregarParOrdenado(double x,double y){
        dataSet.add(x,y);
    }
    
    public void agregarParOrdenadoEnSerie2(double x,double y){
        dataSet2.add(x,y);
    }
    
    public void agregarParOrdenadoEnSerie3(double x,double y){
        dataSet3.add(x,y);
    }
    
    public void generarGrafico(){
        //generar grafico
        grafico= ChartFactory.createXYLineChart(titulo, tituloEjeX, tituloEjeY, datasetCollection, PlotOrientation.VERTICAL, true,true,false);
        datasetCollection.addSeries(dataSet);  
        datasetCollection.addSeries(dataSet2);  
        datasetCollection.addSeries(dataSet3);  
        
        //configurar generador de plots
        XYPlot generadorPlot=(XYPlot) grafico.getPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) generadorPlot.getRenderer();
        renderer.setSeriesShapesVisible(0, true);
        //renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesPaint(0, Color.MAGENTA);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.GREEN);
        
        generadorPlot.setDomainGridlinePaint(Color.BLUE);
	generadorPlot.setRangeGridlinePaint(Color.RED);
        
        //ValueAxis domainAxis = generadorPlot.getDomainAxis();
        //domainAxis.setRange(0,10);
        
        ValueAxis rangeAxis = generadorPlot.getRangeAxis();
        rangeAxis.setRange(0,5);
        
        
        //panel que contendra al grafico a mostrar
        graficoEnPanelView=new ChartPanel(grafico);    
    }
    
    public void setearGraficoEnPanel(JPanel panel){
        panel.add(graficoEnPanelView,BorderLayout.CENTER);
        panel.validate();
    }
    
}

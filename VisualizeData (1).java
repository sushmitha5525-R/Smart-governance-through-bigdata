package com;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.util.Map;
import java.awt.event.WindowEvent;
public class VisualizeData extends ApplicationFrame {
	static String t;
public VisualizeData(String title) {
	super(title);
	t = title;
	setContentPane(createDemoPanel( ));
}
public void windowClosing(WindowEvent we){
	this.setVisible(false);
}
private static PieDataset createDataset( ) {
	DefaultPieDataset dataset = new DefaultPieDataset();
	for(Map.Entry<String,Integer> me : BigData.graph.entrySet()) {
		String key = me.getKey();
		String value = Integer.toString(me.getValue());
		dataset.setValue(key,Double.parseDouble(value));
	}
	return dataset;
}

private static JFreeChart createChart( PieDataset dataset ) {
	JFreeChart chart = ChartFactory.createPieChart(t,dataset,true, true,false);
	return chart;
}
public static JPanel createDemoPanel() {
	JFreeChart chart = createChart(createDataset());
	return new ChartPanel(chart); 
}
}
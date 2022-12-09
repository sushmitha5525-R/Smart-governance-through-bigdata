package com;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.awt.Dimension;
import org.jfree.ui.RefineryUtilities;
import com.jd.swing.custom.component.panel.SimpleGlossyPanel;
import com.jd.swing.util.PanelType;
import com.jd.swing.util.Theme;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JComboBox;
import java.util.HashMap;
import java.awt.Dimension;
public class BigData extends JFrame{
	JPanel p1,p2,p3;
	JLabel l1;
	JButton b1,b2,b3;
	JScrollPane jsp;
	JTable table;
	DefaultTableModel dtm;
	Font f1,f2;
	JFileChooser chooser;
	static StringBuilder mapbuffer = new StringBuilder(); 
	File file;
	JComboBox c1,c2;
	static HashMap<String,Integer> graph = new HashMap<String,Integer>();
	static int type;
	static String value;
public void clearTable(){
	for(int i=dtm.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
}
public BigData(){
	setTitle("Smart Governance through Bigdata");

	chooser = new JFileChooser(new File("dataset"));
	
	f1 = new Font("Castellar", 1, 21);
    p1 = new SimpleGlossyPanel(Theme.GLOSSY_MULTIBLUECOLOR_THEME,PanelType.PANEL_ROUNDED_RECTANGLUR);
	l1 = new JLabel("<html><body><center>Smart Governance through Bigdata: Digital Transformation of Public Agencies</center></body></html>".toUpperCase());
	l1.setFont(this.f1);
    l1.setForeground(Color.white);
    p1.add(l1);
	p1.setBackground(new Color(204, 110, 155));
    
    f2 = new Font("Courier New", 1, 13);
    p2 = new JPanel();
    p2.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
    table.setFont(f2);
	table.getTableHeader().setFont(new Font("Courier New", 1, 14));
	table.setRowHeight(30);
    jsp = new JScrollPane(table);
	p2.add(jsp);
	dtm.addColumn("Dataset Properties");
	dtm.addColumn("Dataset Values");

	p3 = new SimpleGlossyPanel(Theme.GLOSSY_MULTIBLUECOLOR_THEME,PanelType.PANEL_ROUNDED_RECTANGLUR);
	
	b1 = new JButton("Upload Public Information Dataset");
	p3.add(b1);
	b1.setFont(f2);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(BigData.this);
			if(option == chooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				clearTable();
				DatasetInfo.getInfo(file.getPath());
				Object o1[] = {"Dataset Name",file.getName()};
				Object o2[] = {"Dataset Columns",DatasetInfo.columns};
				Object o3[] = {"Total Columns",DatasetInfo.columns.split(",").length};
				Object o4[] = {"Dataset Size",DatasetInfo.size};
				dtm.addRow(o1);
				dtm.addRow(o2);
				dtm.addRow(o3);
				dtm.addRow(o4);
			}
		}
	});

	c1 = new JComboBox();
	c1.setFont(f2);
	c1.addItem("Search Type");
	c1.addItem("workclass");
	c1.addItem("education");
	c1.addItem("occupation");
	p3.add(c1);
	c1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			c2.removeAllItems();
			c2.addItem("Search Value");
			String type = c1.getSelectedItem().toString().trim();
			if(type.equals("workclass")){
				for(int i=0;i<DatasetInfo.workclass.size();i++){
					c2.addItem(DatasetInfo.workclass.get(i));
				}
			}
			if(type.equals("education")){
				for(int i=0;i<DatasetInfo.education.size();i++){
					c2.addItem(DatasetInfo.education.get(i));
				}
			}
			if(type.equals("occupation")){
				for(int i=0;i<DatasetInfo.occupation.size();i++){
					c2.addItem(DatasetInfo.occupation.get(i));
				}
			}
		}
	});

	c2 = new JComboBox();
	c2.addItem("Search Value");
	c2.setFont(f2);
	p3.add(c2);

	b2 = new JButton("Search Public Information Using BigData Hadoop MapReduce with Privacy");
	p3.add(b2);
	b2.setFont(f2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			String selected_type = c1.getSelectedItem().toString().trim();
			value = c2.getSelectedItem().toString().trim();
			if(selected_type.equals("workclass"))
				type = 1;
			if(selected_type.equals("education"))
				type = 3;
			if(selected_type.equals("occupation"))
				type = 6;
			searchBigdata();
		}
	});

	b3 = new JButton("Data Visualization Graph");
	p3.add(b3);
	b3.setFont(f2);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			VisualizeData chart1 = new VisualizeData("Data Visualization");
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});

	p3.setPreferredSize(new Dimension(800,120));

	getContentPane().add(p1, "North");
    getContentPane().add(p2, "Center");
	getContentPane().add(p3, "South");
    
}

public void searchBigdata(){
	try{
		mapbuffer.delete(0,mapbuffer.length());
		graph.clear();
		deleteFiles(new File("output"));
		Hdfs.run(file.getPath());
		String arr[] = mapbuffer.toString().split("\n");
		ViewSearchResult vfr = new ViewSearchResult("Search Result");
		for(int i=0;i<arr.length;i++){
			String temp[] = arr[i].split(",");
			vfr.dtm.addRow(temp);
		}
		vfr.setVisible(true);
		vfr.setSize(800,600);
	}catch(Exception e){
		e.printStackTrace();
	}
}

public void deleteFiles(File path){
	if(path.exists()){
		File[] dir = path.listFiles();
		for(int d=0;d<dir.length;d++){
			if(dir[d].isFile()){
				dir[d].delete();
			}else if(dir[d].isDirectory()){
				deleteFiles(dir[d]);
			}
		}
		if(path.isDirectory()){
			path.delete();
		}
	}
}

public static void main(String a[]){
	BigData bd = new BigData();
	bd.setVisible(true);
	bd.setExtendedState(JFrame.MAXIMIZED_BOTH);
}
}
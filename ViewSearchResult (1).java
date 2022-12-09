package com;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JScrollPane;
public class ViewSearchResult extends JFrame{
	JTable table;
	DefaultTableModel dtm;
	JScrollPane jsp;
public ViewSearchResult(String title){
	super(title);
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setRowHeight(30);
	table.setFont(new Font("Courier New",Font.BOLD,13));
	jsp = new JScrollPane(table);
	getContentPane().add(jsp);
	dtm.addColumn("Age");
	dtm.addColumn("Workclass");
	dtm.addColumn("fnlwgt");
	dtm.addColumn("Education");
	dtm.addColumn("Education-num");
	dtm.addColumn("Marital-status");
	dtm.addColumn("Occupation");
	dtm.addColumn("Relationship");
	dtm.addColumn("Race");
	dtm.addColumn("Gender");
	dtm.addColumn("Capital-Gain");
	dtm.addColumn("Capital-Loss");
	dtm.addColumn("Hours-Per-Week");
	dtm.addColumn("Native-Country");
	dtm.addColumn("Salary");
}
}

package hr.fer.zemris.vhdllab.applets.schema;

import hr.fer.zemris.vhdllab.applets.automat.entityTable.EntityParser;
import hr.fer.zemris.vhdllab.applets.automat.entityTable.IEntityWizard;
import hr.fer.zemris.vhdllab.applets.automat.entityTable.NumberBox;
import hr.fer.zemris.vhdllab.applets.automat.entityTable.ReturnData;
import hr.fer.zemris.vhdllab.applets.automat.entityTable.TableData;
import hr.fer.zemris.vhdllab.applets.main.interfaces.ProjectContainer;
import hr.fer.zemris.vhdllab.vhdl.model.CircuitInterface;
import hr.fer.zemris.vhdllab.vhdl.model.Extractor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class SEntityTable extends JPanel implements IEntityWizard {

	private JTable table=null;
	private MyTableModel model=null;
	private ProjectContainer pContainer=null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7533182574459245416L;
	
	private JTextField imeSklop;
	/**
	 * @param header 
	 * @param args
	 */
	public SEntityTable(String label,String[] data, String lab2){
		super();
		
		createGUI(label,data,lab2);
	}

	private void createGUI(String lab1,String[] data,String lab2) {
		imeSklop=new JTextField("Sklop");
		imeSklop.addMouseListener(new Mouse());
		
		JPanel panel=new JPanel(new GridLayout(1,2));
		panel.add(imeSklop);

		
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(lab2),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		//panel.setBorder(BorderFactory.createEmptyBorder(0,10,0,200));
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.NORTH);
		
		Object[][] obj={{"","in","Std_Logic","0","0"}};
		JComboBox inComboBox=createInComboBox();
		JComboBox tipComboBox=createTipComboBox();
		
		NumberBox brojevi=new NumberBox("0");
		
		model=new MyTableModel(obj,data);
		model.addTableModelListener(new TableModelListener(){
			public void tableChanged(TableModelEvent arg0) {
				if(!model.getValueAt(model.getRowCount()-1,0).equals(""))model.addRow();
			}
		});
		table=new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(inComboBox));
		table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(tipComboBox));
		table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(brojevi));
		table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(brojevi));
		
		JScrollPane pane=new JScrollPane(table);
		pane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(lab1),
				BorderFactory.createEmptyBorder(5,5,5,5)));
		pane.setPreferredSize(new Dimension(100,100));
		this.add(pane,BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(500,300));
	}
	
	
	
	private JComboBox createInComboBox() {
		JComboBox cb=new JComboBox();
		InputStream in=this.getClass().getResourceAsStream("EntityTable-Direction.properties");
		Properties p=new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Object key:p.keySet())cb.addItem(p.get(key));
		return cb;
	}

	private JComboBox createTipComboBox() {
		JComboBox cb=new JComboBox();
		InputStream in=this.getClass().getResourceAsStream("EntityTable-Type.properties");
		Properties p=new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Object key:p.keySet())cb.addItem(p.get(key));
		return cb;
	}
	
	public ReturnData getData(){
		return model.getData();
	}


	//***********MyTableModel************
	
    private class MyTableModel extends AbstractTableModel {
        /**
		 * 
		 */
		private static final long serialVersionUID = -4781965036332460224L;
		/**
		 * 
		 */
		private String[] columnNames = new String[5];
        private Object[][] data = null;

        protected MyTableModel(Object[][] obj,String[] names){
        	super();
        	data=obj;
        	for(int i=0;i<5&&i<names.length;i++)columnNames[i]=names[i];
        }
        
        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
/*
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
*/

        public boolean isCellEditable(int row, int col) {
        	if(col>2&&getValueAt(row,2).equals("Std_Logic"))return false;
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
        public void addRow(){
        	int brojRedova=getRowCount();
        	Object[][] obj=new Object[brojRedova+1][getColumnCount()];
        	for(int i=0;i<getRowCount();i++)
        		for(int j=0;j<5;j++)obj[i][j]=data[i][j];
        	obj[getRowCount()][0]="";
        	obj[getRowCount()][1]="in";
        	obj[getRowCount()][2]="Std_Logic";
        	obj[getRowCount()][3]="0";
        	obj[getRowCount()][4]="0";
        	data=obj;
        	fireTableDataChanged();
        };
        public ReturnData getData(){
        	table.editingStopped(new ChangeEvent(SEntityTable.this));
        	String[][] pom=new String[getRowCount()][getColumnCount()];
        	for(int i=0;i<getRowCount();i++)
        		for(int j=0;j<getColumnCount();j++)pom[i][j]=(String) data[i][j];
        	ReturnData dat=new ReturnData(imeSklop.getText(),pom);
        	return  dat;
        }
    }
    
    public void updateTable(){
    	table.editingStopped(new ChangeEvent(SEntityTable.this));
    }
    
    private class Mouse implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			table.editingStopped(new ChangeEvent(SEntityTable.this));
		}
		public void mousePressed(MouseEvent e) {
		
		}
		public void mouseReleased(MouseEvent e) {
			
		}
		public void mouseEntered(MouseEvent e) {
			
		}
		public void mouseExited(MouseEvent e) {
			
		}
    }

	public void setData(TableData data) {
		// TODO Method still unimplemented
		
	}

	public CircuitInterface getCircuitInterface() {
		//TODO Rucno napraviti extractCircuitInterface
		EntityParser parser=new EntityParser(imeSklop.getText(),model.getData().getData());
		return Extractor.extractCircuitInterface(parser.getParsedEntityVHDL());
	}

	public void setProjectContainer(ProjectContainer pContainer) {
		this.pContainer=pContainer;
	}
}
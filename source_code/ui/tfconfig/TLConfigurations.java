package ui.tfconfig;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TLConfigurations extends JFrame {

	private static final long serialVersionUID = 1L;
    DefaultTableModel model;
    private JFrame frame;
	private JTable table;
	private JButton btnOk, btnCancel;

	public TLConfigurations() {
		initialize();
	}

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TLConfigurations window = new TLConfigurations();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	private void initialize(){

		frame = new JFrame(); //create frame
        frame.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
        frame.setBounds(50, 10, 1000, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.setTitle("Traffic Lights Configuration"); //set the title of the frame

		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 50, 450, 250);


        table = new JTable(); //create the table

		table.setForeground(new Color(0, 0, 0));
		table.setBackground(new Color(248, 248, 255));
		table.setCellSelectionEnabled(true);
		Object[][] data = { //adding information to the array
                {"1",true,true,true,true,true,true,true,true,true,true},
                {"2",true,true,true,true,true,true,true,true,true,true},
                {"3",true,true,true,true,true,true,true,true,true,true},
                {"4",true,true,true,true,true,true,true,true,true,true},
                {"5",true,true,true,true,true,true,true,true,true,true}};
        String[] cols = {"TL ID","t1","t2","t3","t4","t5","t6","t7","t8","t9","t10"}; // name of the table columns

        model = new DefaultTableModel(data, cols) {
            private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Class getColumnClass(int column) { //cells can be checked or unchecked
                return getValueAt(0, column).getClass();
            }
        };

        table.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent arg0) {   //when a cell is clicked the colour of the cell is changed
                for(int i=1; i<=(table.getColumnCount()-1); i++) //cover all of the columns of the table, I was not able to call the whole table at once...
				     table.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()); //call to the TableCellRnderer function to change the colour of the cell
			}
		});
        table.setModel(model); //adds the model to the table

        scrollPane.setViewportView(table);        //add table to the scrollpane
        btnOk = new JButton("OK");
        btnOk.setBounds(50, 300, 80, 30);
        scrollPane.add(btnOk); //add ok button to the scrollpane
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(200, 300, 80, 30);
        scrollPane.add(btnCancel);//add cancel button to the scrollpane
        frame.getContentPane().add(scrollPane); //adds the scrollPane to the frame
        frame.setVisible(true); //frame visible
    }

    @SuppressWarnings("serial")
    private static class TableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //creating a component obj

            if (table.getValueAt(row, column).equals(true)) {
                comp.setBackground(Color.GREEN); //set cycles in green
            } else
                comp.setBackground(Color.RED); // Set cycles in red
            return comp;
        }
    }
}

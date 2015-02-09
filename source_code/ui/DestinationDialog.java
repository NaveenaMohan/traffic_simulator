package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class DestinationDialog extends JDialog {

	private JTextField textField;

	public DestinationDialog() {

        initUI();
    }
	
	public final void initUI() {

        ImageIcon icon = new ImageIcon("/Users/somya/Desktop/Destination.png");
        getContentPane().setLayout(null);
        JLabel label = new JLabel(icon);
        label.setBounds(49, 22, 200, 75);
        label.setAlignmentX(0.5f);
        getContentPane().add(label);

        JLabel name = new JLabel("Enter Destination Name: ");
        name.setBounds(58, 126, 191, 15);
        name.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 13));
        name.setAlignmentX(0.5f);
        getContentPane().add(name);
        
        textField = new JTextField();
        textField.setBounds(39, 153, 222, 29);
        getContentPane().add(textField);
        textField.setColumns(10);

        JButton close = new JButton("OK");
        close.setBounds(114, 194, 75, 29);
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
        
     
        close.setAlignmentX(0.5f);
        getContentPane().add(close);

        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("Add Location");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 250);
    }
}
	

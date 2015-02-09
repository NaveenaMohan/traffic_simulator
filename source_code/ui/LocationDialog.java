package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;


public class LocationDialog extends JDialog {
	private JTextField textField;

	public LocationDialog() {

        initUI();
    }
	
	public final void initUI() {

        ImageIcon icon = new ImageIcon("/Users/somya/Desktop/Location.png");
        getContentPane().setLayout(null);
        JLabel label = new JLabel(icon);
        label.setBounds(49, 22, 200, 75);
        label.setAlignmentX(0.5f);
        getContentPane().add(label);

        JLabel name = new JLabel("Enter Location Name: ");
        name.setBounds(71, 125, 162, 15);
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
	
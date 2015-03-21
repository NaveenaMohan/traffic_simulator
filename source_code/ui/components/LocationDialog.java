package ui.components;

import ui.Traffic_Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LocationDialog extends JDialog {

    private String destination;

    public String getDestination() {
        return destination;
    }

    public final void initUI() {

        ImageIcon icon = new ImageIcon(Traffic_Simulator.class.getResource("/resources/Location.png"));
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

        final JTextField textField = new JTextField();
        textField.setBounds(39, 153, 222, 29);
        getContentPane().add(textField);
        textField.setColumns(10);

        JButton close = new JButton("OK");
        close.setBounds(114, 194, 75, 29);
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                destination = textField.getText();
                setVisible(false);
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

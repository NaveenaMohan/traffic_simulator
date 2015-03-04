/**
 * Created by somya on 26/02/15.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RUnitDialogBox extends JDialog {

    public final void initUI() {

        ImageIcon icon = new ImageIcon(Traffic_Simulator.class.getResource("RUnitError.png"));
        getContentPane().setLayout(null);
        JLabel label = new JLabel(icon);
        label.setBounds(6, 23, 50, 50);
        label.setAlignmentX(0.5f);
        getContentPane().add(label);

        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        JButton close = new JButton("OK");
        close.setBounds(90, 62, 75, 29);
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                dispose();
            }
        });


        close.setAlignmentX(0.5f);
        getContentPane().add(close);

        JLabel lblNoMatching = new JLabel("Wrong Position! ");
        lblNoMatching.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
        lblNoMatching.setBounds(68, 23, 200, 42);
        getContentPane().add(lblNoMatching);

        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("Error Message");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(225, 125);
    }
}
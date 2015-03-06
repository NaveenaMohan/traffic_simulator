package managers.vehiclefactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by somya on 05/03/15.
 */
public class NoVehicleFactoryDialogBox extends JDialog {

    public final void vehicleFactoryDialog() {

        ImageIcon icon = new ImageIcon(managers.vehiclefactory.NoVehicleFactoryDialogBox.class.getResource("RUnitError.png"));
        getContentPane().setLayout(null);
        JLabel label = new JLabel(icon);
        label.setBounds(6, 23, 50, 50);
        label.setAlignmentX(0.5f);
        getContentPane().add(label);

        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        JButton close = new JButton("OK");
        close.setBounds(140, 62, 75, 29);
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                dispose();
                repaint();
            }
        });


        close.setAlignmentX(0.5f);
        getContentPane().add(close);

        JLabel lblNoMatching = new JLabel("No Vehicle Factory added! ");
        lblNoMatching.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
        lblNoMatching.setBounds(68, 23, 275, 42);
        getContentPane().add(lblNoMatching);

        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        setTitle("Error Message");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(315, 125);

    }
}

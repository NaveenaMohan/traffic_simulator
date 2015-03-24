package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by naveena on 21/03/15.
 */
public class GlobalErrorDialogBox extends JDialog {

    private static final long serialVersionUID = 1L;

    public GlobalErrorDialogBox(Throwable ex) {

        //Initializes and populates the Error dialog box with error details
        ImageIcon icon = new ImageIcon(GlobalErrorDialogBox.class.getResource("/resources/RUnitError.png"));
        getContentPane().setLayout(null);
        JLabel label = new JLabel(icon);
        label.setBounds(6, 23, 50, 50);
        label.setAlignmentX(0.5f);
        getContentPane().add(label);

        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        JLabel warning = new JLabel("CLOSING THE DIALOG BOX WILL CLOSE THE APPLICATION!!!");
        warning.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
        warning.setBounds(26, 119, 515, 41);
        getContentPane().add(warning);

        //Display the exception
        JLabel exception = new JLabel("Exception : " + ex.toString());
        exception.setFont(new Font("Copperplate Gothic", Font.BOLD, 15));
        exception.setBounds(63, 23, 488, 50);
        getContentPane().add(exception);

        JLabel referConsole = new JLabel("Refer Output Console for details");
        referConsole.setFont(new Font("Copperplate Gothic", Font.ITALIC, 15));
        referConsole.setBounds(155, 85, 241, 33);
        getContentPane().add(referConsole);

        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setTitle("ERROR");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(557, 204);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                //Exit application
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
}

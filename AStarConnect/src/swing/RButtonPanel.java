package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RButtonPanel extends JPanel
{
    private final JRadioButton h1 = new JRadioButton("general h()",true);
    private final JRadioButton h2 = new JRadioButton("discrete h()",false);
    private final JButton button = new JButton("Choose File");

    public RButtonPanel() {
        super(null);
        ButtonGroup group = new ButtonGroup();
        group.add(h1);
        group.add(h2);
        h1.setFocusPainted(false);
        h2.setFocusPainted(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel radiopanel = new JPanel();
        radiopanel.setLayout(new BoxLayout(radiopanel, BoxLayout.X_AXIS));
        radiopanel.add(h1);
        radiopanel.add(h2);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new BorderLayout());
        buttonpanel.add(button);
        add(radiopanel);
        add(buttonpanel);
        setPreferredSize(new Dimension(250, 50));
    }
    public void addH1Listener(ActionListener listener) {
        h1.addActionListener(listener);
    }
    public void addH2Listener(ActionListener listener) {
        h2.addActionListener(listener);
    }
    public void addButtonListener(ActionListener listener) { button.addActionListener(listener);
    }
}

 class RadioButtonJFrame extends JFrame{
     public RadioButtonJFrame(ActionListener buttonlistener,ActionListener h1listener,ActionListener h2listener) throws HeadlessException {
         setTitle("Choose h(x)");
         setSize(400, 200);
         RButtonPanel radio = new RButtonPanel();
         radio.addButtonListener(buttonlistener);
         radio.addH1Listener(h1listener);
         radio.addH2Listener(h2listener);
         add(radio);
         setLocationRelativeTo(null);
         pack();
     }

     public RadioButtonJFrame() throws HeadlessException {
     }
 }

package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextLinePanel extends JPanel{
    private final JLabel nameMessage = new JLabel("   Vertex name: ");
    public JTextField nameInput = new JTextField(10);
    private final JLabel heuristicMessage = new JLabel("   Heuristic value: ");
    public JTextField heuristicInput= new JTextField(5);
    private final JCheckBox generalheuristic = new JCheckBox("Discrete h(x)");

    public TextLinePanel() {
        super(new GridLayout(1, 5));
        setPreferredSize(new Dimension(500, 30));
        add(nameMessage);
        add(nameInput);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generalheuristic.isSelected()){
                  addH();
                  ButtonPanel.isGraphicGH = false;
                  ButtonPanel.isFile = false;
                  DrawPanel.setInputHeuristic(true);
                  generalheuristic.setEnabled(false);
                }
            }
        };
        generalheuristic.setSelected(false);
        generalheuristic.addActionListener(listener);
        add(generalheuristic);
        //addH();
    }
    public void deleteH(){
        remove(heuristicMessage);
        remove(heuristicInput);
        this.updateUI();
    }

    public void addH(){
        add(heuristicMessage);
        add(heuristicInput);
        this.updateUI();
    }

}

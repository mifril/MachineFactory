package ru.nsu.vakhrushev.factory.view;

import ru.nsu.vakhrushev.factory.Controller;
import ru.nsu.vakhrushev.factory.ThreadType;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 07.06.13
 * Time: 13:09
 * To change this template use File | Settings | File Templates.
 */
public class View extends JFrame{

    private JPanel panel = new JPanel();
    private InfoPanel [] panels = new InfoPanel [4];


    public View (Controller controller)
        {
            setTitle("Factory");
            setLayout(new GridLayout(1, 1));

            panel.setLayout(new GridLayout(1, 4));
            for (int i = 0; i < panels.length; i++)
            {
                panels[i] = new InfoPanel(typeByIndex(i), controller);
                panel.add(panels[i]);
            }

            add(panel);

            setPreferredSize(new Dimension(800, 400));
            setMaximumSize(new Dimension(800, 400));
            setMinimumSize(new Dimension(800, 400));
            setSize(new Dimension(800, 400));
            panel.setPreferredSize(new Dimension(800, 400));
            panel.setMaximumSize(new Dimension(800, 400));
            panel.setMinimumSize(new Dimension(800, 400));
            panel.setSize(new Dimension(800, 400));

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            pack();
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
            panel.repaint();
            repaint();
        }

        private ThreadType typeByIndex (int i)
        {
            ThreadType type;
            switch(i)
            {
                case 0:
                {
                    type = ThreadType.DEALER;
                    break;
                }
                case 1:
                {
                    type = ThreadType.BODYSUPLIER;
                    break;
                }
                case 2:
                {
                    type = ThreadType.MOTORSUPLIER;
                    break;
                }
                case 3:
                {
                    type = ThreadType.ACSUPLIER;
                    break;
                }
                default:
                {
                    type = null;
                    break;
                }
            }
            return type;
        }

        public void update()
        {
            for (InfoPanel panel1 : panels)
            {
                panel1.update();
            }
            panel.repaint();
            repaint();
        }
}

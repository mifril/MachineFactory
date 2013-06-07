package ru.nsu.vakhrushev.factory.view;

import ru.nsu.vakhrushev.factory.Controller;
import ru.nsu.vakhrushev.factory.ThreadType;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 07.06.13
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class InfoPanel extends JPanel {

    private final int minValue = 1;
    private final int maxValue = 10;
    private final int defaultValue = 5;
    private final Controller controller;
    private final ThreadType threadType;

    public InfoPanel (ThreadType type, Controller c)
    {
        controller = c;
        threadType = type;

        setPreferredSize(new Dimension(190, 400));
        setMaximumSize(new Dimension(190, 400));
        setMinimumSize(new Dimension(190, 400));
        setSize(new Dimension(190, 400));

        setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.DARK_GRAY));

        final JSlider slider = new JSlider(JSlider.HORIZONTAL, minValue, maxValue, defaultValue);

        slider.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                controller.update(slider.getValue() * 1000, threadType);
            }
        });
        slider.setMajorTickSpacing(9);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JLabel occupancy = new JLabel("Occupancy : " + controller.getNumber(threadType) + "/" + controller.getSize(threadType));
        JLabel total = new JLabel("Total produced : " + controller.getCount(threadType));

        JLabel label = new JLabel(getThreadName(threadType));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(label);
        add(slider);
        add(occupancy);
        add(total);

        repaint();
    }

    public void update()
    {
        Component [] components = getComponents();
        ((JLabel) components[2]).setText("Occupancy : " + controller.getNumber(threadType) + "/" + controller.getSize(threadType));
        ((JLabel) components[3]).setText("Total produced : " + controller.getCount(threadType));
        repaint();
    }

    private String getThreadName (ThreadType type)
    {
        String result = null;
        switch (type)
        {
            case ACSUPLIER:
            {
                result = "Accesories";
                break;
            }
            case BODYSUPLIER:
            {
                result = "Bodies";
                break;
            }
            case MOTORSUPLIER:
            {
                result = "Motors";
                break;
            }
            case DEALER:
            {
                result = "Autos";
                break;
            }
            default:
            {
                result = null;
                break;
            }
        }
        return result;
    }

}

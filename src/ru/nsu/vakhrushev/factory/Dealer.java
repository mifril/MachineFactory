package ru.nsu.vakhrushev.factory;

import ru.nsu.vakhrushev.factory.storages.Auto;
import ru.nsu.vakhrushev.factory.storages.AutoStorage;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 05.06.13
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
public class Dealer {

    private int delay;

    private AutoStorageController ASC;
    private Controller controller;
    private Timer timer;

    private Dealer(){}

    public Dealer (int delay, AutoStorage autoStorage, AutoStorageController ASC, Controller controller)
    {
        this.delay = delay;
        this.ASC = ASC;
        this.controller = controller;
    }

    public void startWork()
    {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (controller.isWorking())
                {
                    Auto a = ASC.getAuto();
                    System.out.println("I receive auto this id " + a.getId());
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            controller.updateView();
                        }
                    });
                }
                else
                {
                    endWork();
                }
            }
        };
        timer.schedule(task, delay, delay);
    }

    public void endWork()
    {
        timer.cancel();
    }
}

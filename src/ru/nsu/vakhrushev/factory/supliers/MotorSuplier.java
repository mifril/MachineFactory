package ru.nsu.vakhrushev.factory.supliers;

import ru.nsu.vakhrushev.factory.Controller;
import ru.nsu.vakhrushev.factory.storages.Motor;
import ru.nsu.vakhrushev.factory.storages.MotorStorage;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 1:53
 * To change this template use File | Settings | File Templates.
 */
public class MotorSuplier {

    private int delay;
    private MotorStorage storage;
    private Controller controller;
    private Timer timer;

    public MotorSuplier(int delay, MotorStorage storage, Controller controller)
    {
        this.delay = delay;
        this.storage = storage;
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
                    System.out.println("Create motor with ID " + storage.getCount());
                    storage.addMotor(new Motor(storage.getCount()));
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
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

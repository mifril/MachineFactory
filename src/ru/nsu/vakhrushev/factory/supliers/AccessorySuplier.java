package ru.nsu.vakhrushev.factory.supliers;

import ru.nsu.vakhrushev.factory.Controller;
import ru.nsu.vakhrushev.factory.storages.Accessory;
import ru.nsu.vakhrushev.factory.storages.AccessoryStorage;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 1:56
 * To change this template use File | Settings | File Templates.
 */
public class AccessorySuplier {

    private int delay;
    private static final Object monitor = new Object();
    private AccessoryStorage storage;

    private Controller controller;
    private Timer timer;

    public AccessorySuplier (int delay, AccessoryStorage storage, Controller controller)
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
                    synchronized (monitor)
                    {
                        System.out.println("Create accessory with ID " + storage.getCount());
                        storage.addAccessory(new Accessory(storage.getCount()));
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                controller.updateView();
                            }
                        });
                    }
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
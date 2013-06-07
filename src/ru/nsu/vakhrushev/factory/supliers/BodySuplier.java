package ru.nsu.vakhrushev.factory.supliers;

import ru.nsu.vakhrushev.factory.Controller;
import ru.nsu.vakhrushev.factory.storages.Body;
import ru.nsu.vakhrushev.factory.storages.BodyStorage;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 1:20
 * To change this template use File | Settings | File Templates.
 */
public class BodySuplier{

    private int delay;
    private BodyStorage storage;
    private Controller controller;
    private Timer timer;
    
    public BodySuplier(int delay, BodyStorage storage, Controller controller)
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
                    System.out.println("Create body with ID " + storage.getCount());
                    storage.addBody(new Body(storage.getCount()));
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

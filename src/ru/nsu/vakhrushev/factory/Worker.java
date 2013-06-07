package ru.nsu.vakhrushev.factory;

import ru.nsu.vakhrushev.factory.storages.*;
import ru.nsu.vakhrushev.factory.threadpool.Task;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 26.05.13
 * Time: 1:19
 * To change this template use File | Settings | File Templates.
 */
public class Worker implements Task {

    private final static Object monitor = new Object();
    private AutoStorage autoStorage;
    private BodyStorage bodyStorage;
    private MotorStorage motorStorage;
    private AccessoryStorage accessoryStorage;

    private Controller controller;

    public Worker (AccessoryStorage accessoryStorage, AutoStorage autoStorage, BodyStorage bodyStorage, MotorStorage motorStorage, Controller controller)
    {
        this.accessoryStorage = accessoryStorage;
        this.autoStorage = autoStorage;
        this.bodyStorage = bodyStorage;
        this.motorStorage = motorStorage;
        this.controller = controller;
    }

    @Override
    public void performWork()
    {
        System.out.println("Worker started");
        Motor m = motorStorage.getMotor();
        System.out.println("Worker got motor");
        Body b = bodyStorage.getBody();
        System.out.println("Worker got body");


        Accessory ac = accessoryStorage.getAccessory();
        System.out.println("Worker got accessories");
        Auto a;
        synchronized (monitor)
        {
            a = new Auto(autoStorage.getCount(), m, b, ac);
        }
        autoStorage.addAuto(a);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                controller.updateView();
            }
        });
        System.out.println("Worker created auto");
    }

}

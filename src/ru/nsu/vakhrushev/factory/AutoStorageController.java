package ru.nsu.vakhrushev.factory;

import ru.nsu.vakhrushev.factory.storages.*;
import ru.nsu.vakhrushev.factory.threadpool.ThreadPool;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 25.05.13
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public class AutoStorageController{

    private static final int autoChunk = 5;
    private final AutoStorage autoStorage;
    private BodyStorage bodyStorage;
    private MotorStorage motorStorage;
    private AccessoryStorage accessoryStorage;

    private Controller controller;
    private ThreadPool workers;

    AutoStorageController (ThreadPool workers, AccessoryStorage accessoryStorage, AutoStorage autoStorage, BodyStorage bodyStorage, MotorStorage motorStorage, Controller controller)
    {
        this.workers = workers;
        this.accessoryStorage = accessoryStorage;
        this.autoStorage = autoStorage;
        this.bodyStorage = bodyStorage;
        this.motorStorage = motorStorage;
        this.controller = controller;
    }

    public Auto getAuto ()
    {
        Auto a;
        synchronized (autoStorage)
        {
            if (autoStorage.isEmpty())
            {
                for (int i = 0; i < autoChunk; i++)
                {
                    workers.addTask(new Worker(accessoryStorage, autoStorage, bodyStorage, motorStorage, controller));
                }
            }
            a = autoStorage.getAuto();
            autoStorage.notifyAll();
        }
        return a;
    }
}

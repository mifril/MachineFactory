package ru.nsu.vakhrushev.factory;

import ru.nsu.vakhrushev.factory.storages.AccessoryStorage;
import ru.nsu.vakhrushev.factory.storages.AutoStorage;
import ru.nsu.vakhrushev.factory.storages.BodyStorage;
import ru.nsu.vakhrushev.factory.storages.MotorStorage;
import ru.nsu.vakhrushev.factory.supliers.AccessorySuplier;
import ru.nsu.vakhrushev.factory.supliers.BodySuplier;
import ru.nsu.vakhrushev.factory.supliers.MotorSuplier;
import ru.nsu.vakhrushev.factory.threadpool.ThreadPool;
import ru.nsu.vakhrushev.factory.view.View;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 1:36
 * To change this template use File | Settings | File Templates.
 */
public class Controller {

    private static final String bodyStorageSizeString = "BodyStorageSize";
    private static final String motorStorageSizeString = "MotorStorageSize";
    private static final String accessoryStorageSizeString = "AccessoryStorageSize";
    private static final String autoStorageSizeString = "AutoStorageSize";
    private static final String accessoryNumberString = "AccessorySuppliersNumber";
    private static final String workersNumberString = "WorkersNumber";
    private static final String dealersNumberString = "DealersNumber";
    private static final int defaultDelay = 3000;

    private AutoStorageController ASC;
    private AutoStorage autoStorage;
    private BodyStorage bodyStorage;
    private MotorStorage motorStorage;
    private AccessoryStorage accessoryStorage;

    private BodySuplier bodySuplier;
    private MotorSuplier motorSuplier;
    private AccessorySuplier[] accessorySupliers;
    private ThreadPool workers;
    private Dealer[] dealers;

    private View view;

    private boolean workingFlag;

    public Controller(String fileName) throws IOException
    {

        workingFlag = true;

        Properties prop = new Properties();
        prop.load(this.getClass().getResourceAsStream(fileName));

        bodyStorage = new BodyStorage(Integer.decode(prop.getProperty(bodyStorageSizeString)));
        motorStorage = new MotorStorage(Integer.decode(prop.getProperty(motorStorageSizeString)));
        autoStorage = new AutoStorage(Integer.decode(prop.getProperty(autoStorageSizeString)));
        accessoryStorage = new AccessoryStorage(Integer.decode(prop.getProperty(accessoryStorageSizeString)));

        dealers = new Dealer[Integer.decode(prop.getProperty(dealersNumberString))];
        accessorySupliers = new AccessorySuplier[Integer.decode(prop.getProperty(accessoryNumberString))];

        bodySuplier = new BodySuplier(defaultDelay, bodyStorage, this);
        motorSuplier = new MotorSuplier(defaultDelay, motorStorage, this);
        workers = new ThreadPool(Integer.decode(prop.getProperty(workersNumberString)), this);
        ASC = new AutoStorageController(workers, accessoryStorage, autoStorage, bodyStorage, motorStorage, this);
        for (int i = 0; i < dealers.length; i++)
        {
            dealers[i] = new Dealer(defaultDelay, autoStorage, ASC, this);
        }
        for (int i = 0; i < accessorySupliers.length; i++)
        {
            accessorySupliers[i] = new AccessorySuplier(defaultDelay, accessoryStorage, this);
        }
        view = new View(this);
    }

    public void startWork()
    {
        bodySuplier.startWork();
        motorSuplier.startWork();

        workers.startWork();
        for (Dealer dealer : dealers)
        {
            dealer.startWork();
        }
        for (AccessorySuplier s : accessorySupliers)
        {
            s.startWork();
        }
    }

    public boolean isWorking()
    {
        return workingFlag;
    }

    public void update (final int delay, ThreadType type)
    {
        switch (type)
        {
            case ACSUPLIER:
            {
                for (int i = 0; i < accessorySupliers.length; i++)
                {
                    accessorySupliers[i].endWork();
                    accessorySupliers[i] = new AccessorySuplier(delay, accessoryStorage, this);
                    accessorySupliers[i].startWork();
                }
                break;
            }
            case BODYSUPLIER:
            {
                bodySuplier.endWork();
                bodySuplier = new BodySuplier(delay, bodyStorage, this);
                bodySuplier.startWork();
                break;
            }
            case MOTORSUPLIER:
            {
                motorSuplier.endWork();
                motorSuplier = new MotorSuplier(delay, motorStorage, this);
                motorSuplier.startWork();
                break;
            }
            case DEALER:
            {
                for (int i = 0; i < dealers.length; i++)
                {
                    dealers[i].endWork();
                    dealers[i] = new Dealer(delay, autoStorage, ASC, this);
                    dealers[i].startWork();
                }
                break;
            }
            default:
            {
                System.err.println("Error in switch");
            }
        }
    }

    public void updateView()
    {
        view.update();
    }



    public int getCount (ThreadType type)
    {
        int result = 0;
        switch (type)
        {
            case ACSUPLIER:
            {
                result = accessoryStorage.getCount();
                break;
            }
            case BODYSUPLIER:
            {
                result = bodyStorage.getCount();
                break;
            }
            case MOTORSUPLIER:
            {
                result = motorStorage.getCount();
                break;
            }
            case DEALER:
            {
                result = autoStorage.getCount();
                break;
            }
            default:
            {
                result = 0;
                break;
            }

        }
        return result;
    }
    public int getNumber (ThreadType type)
    {
        int result = 0;
        switch (type)
        {
            case ACSUPLIER:
            {
                result = accessoryStorage.getNumber();
                break;
            }
            case BODYSUPLIER:
            {
                result = bodyStorage.getNumber();
                break;
            }
            case MOTORSUPLIER:
            {
                result = motorStorage.getNumber();
                break;
            }
            case DEALER:
            {
                result = autoStorage.getNumber();
                break;
            }
            default:
            {
                result = 0;
                break;
            }
        }
        return result;
    }

    public int getSize (ThreadType type)
    {
        int result = 0;
        switch (type)
        {
            case ACSUPLIER:
            {
                result = accessoryStorage.getSize();
                break;
            }
            case BODYSUPLIER:
            {
                result = bodyStorage.getSize();
                break;
            }
            case MOTORSUPLIER:
            {
                result = motorStorage.getSize();
                break;
            }
            case DEALER:
            {
                result = autoStorage.getSize();
                break;
            }
            default:
            {
                result = 0;
                break;
            }
        }
        return result;
    }
}

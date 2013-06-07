package ru.nsu.vakhrushev.factory.storages;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 1:09
 * To change this template use File | Settings | File Templates.
 */
public class MotorStorage {

    private int size;
    private int count = 0;
    private final List<Motor> motors = new LinkedList<>();

    public MotorStorage (int size)
    {
        this.size = size;
    }



    public void addMotor (Motor b)
    {
        synchronized (motors)
        {
            while (isFull())
            {
                try
                {
                    motors.wait();
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            motors.add(b);
            count++;
            motors.notifyAll();
        }
    }

    public Motor getMotor ()
    {
        synchronized (motors)
        {
            while (isEmpty())
            {
                try
                {
                    motors.wait();
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            Motor m = motors.remove(0);
            motors.notifyAll();
            return m;
        }
    }

    public int getCount()
    {
        synchronized (motors)
        {
            return count;
        }
    }
    public int getSize()
    {
        return size;
    }

    public int getNumber()
    {
        synchronized (motors)
        {
            return motors.size();
        }
    }
    public boolean isEmpty()
    {
        return motors.isEmpty();
    }
    public boolean isFull()
    {
        return motors.size() == size;
    }

}

package ru.nsu.vakhrushev.factory.storages;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 1:11
 * To change this template use File | Settings | File Templates.
 */
public class AutoStorage {

    private int size;
    private int count = 0;
    private final List<Auto> autos = new LinkedList<>();

    public AutoStorage (int size)
    {
        this.size = size;
    }

    public void addAuto (Auto b)
    {
        synchronized (autos)
        {
            while (isFull())
            {
                try
                {
                    autos.wait();
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            autos.add(b);
            count++;
            autos.notifyAll();
        }
    }

    public Auto getAuto ()
    {
        synchronized (autos)
        {
            while (isEmpty())
            {
                try
                {
                    autos.wait();
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            Auto b = autos.remove(0);
            autos.notifyAll();
            return b;
        }
    }

    public int getCount()
    {
        synchronized (autos)
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
        synchronized (autos)
        {
            return autos.size();
        }
    }

    public boolean isEmpty()
    {
        return autos.isEmpty();
    }
    public boolean isFull()
    {
        return autos.size() == size;
    }
}

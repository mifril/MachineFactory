package ru.nsu.vakhrushev.factory.storages;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 1:12
 * To change this template use File | Settings | File Templates.
 */
public class AccessoryStorage {

    private int size;
    private int count = 0;
    private final List<Accessory> accessories = new LinkedList<>();


    public AccessoryStorage (int size)
    {
        this.size = size;
    }



    public void addAccessory (Accessory b)
    {
        synchronized (accessories)
        {
            while (isFull())
            {
                try
                {
                    accessories.wait();
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            accessories.add(b);
            count++;

            accessories.notifyAll();
        }
    }

    public Accessory getAccessory ()
    {
        synchronized (accessories)
        {
            while (isEmpty())
            {
                try
                {
                    accessories.wait();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            Accessory b = accessories.remove(0);
            accessories.notifyAll();
            return b;
        }
    }

    public boolean isEmpty()
    {
        return accessories.isEmpty();
    }
    public boolean isFull()
    {
        return accessories.size() == size;
    }

    public int getCount()
    {
        synchronized (accessories)
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
        synchronized (accessories)
        {
            return accessories.size();
        }
    }

}

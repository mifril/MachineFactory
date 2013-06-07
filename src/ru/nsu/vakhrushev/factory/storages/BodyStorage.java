package ru.nsu.vakhrushev.factory.storages;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 0:49
 * To change this template use File | Settings | File Templates.
 */
public class BodyStorage {

    private int size;
    private int count = 0;
    private final List <Body> bodies = new LinkedList<>();

    public BodyStorage (int size)
    {
        this.size = size;
    }

    public void addBody (Body b)
    {
        synchronized (bodies)
        {
            while (isFull())
            {
                try
                {
                    bodies.wait();
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            bodies.add(b);
            count++;
            bodies.notifyAll();
        }
    }

    public Body getBody ()
    {
        synchronized (bodies)
        {
            while (isEmpty())
            {
                try
                {
                    bodies.wait();
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            Body b = bodies.remove(0);
            bodies.notifyAll();
            return b;
        }
    }

    public int getCount()
    {
        synchronized (bodies)
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
        synchronized (bodies)
        {
            return bodies.size();
        }
    }
    public boolean isEmpty()
    {
        return bodies.isEmpty();
    }
    public boolean isFull()
    {
        return bodies.size() == size;
    }

}

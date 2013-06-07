package ru.nsu.vakhrushev.factory.threadpool;

import ru.nsu.vakhrushev.factory.Controller;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 0:02
 * To change this template use File | Settings | File Templates.
 */

public class PooledThread implements Runnable {

    private final List taskQueue;
    private Controller c;
    private int number;

    public PooledThread(int number, List taskQueue, Controller c)
    {
        this.taskQueue = taskQueue;
        this.number = number;
        this.c = c;
    }

    private void performTask(ThreadPoolTask t) throws InterruptedException
    {
        t.go();
    }


    public void run()
    {
        try
        {
            ThreadPoolTask toExecute = null;
            while (c.isWorking())
            {
                synchronized (taskQueue)
                {
                    if (taskQueue.isEmpty())
                    {
                        try
                        {
                            taskQueue.wait();
                        }
                        catch (InterruptedException ex)
                        {
                            System.err.println("Thread was inetrrupted:" + getNumber());
                        }
                        continue;
                    }
                    else
                    {
                        toExecute = (ThreadPoolTask)taskQueue.remove(0);
                    }
                }
                performTask(toExecute);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public int getNumber()
    {
        return number;
    }
}

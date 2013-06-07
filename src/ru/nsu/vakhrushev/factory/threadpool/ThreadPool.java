package ru.nsu.vakhrushev.factory.threadpool;

import ru.nsu.vakhrushev.factory.Controller;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 20.05.13
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
public class ThreadPool {

    private int size;
    Controller controller;
    private final List <ThreadPoolTask> taskQueue = new LinkedList<>();
    private Set <PooledThread> availableThreads = new HashSet<>();

    public ThreadPool(int size, Controller controller)
    {
        this.size = size;
        this.controller = controller;
    }

    public void addTask(Task t)
    {
        synchronized (taskQueue)
        {
            taskQueue.add(new ThreadPoolTask(t));
            taskQueue.notify();
        }
    }

    public void startWork ()
    {
        for (int i = 0; i < size; i++)
        {
            availableThreads.add(new PooledThread(i, taskQueue, controller));
        }
        for (PooledThread availableThread : availableThreads)
        {
            new Thread(availableThread).start();
        }
    }
}

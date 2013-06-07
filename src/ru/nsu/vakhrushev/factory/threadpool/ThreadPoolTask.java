package ru.nsu.vakhrushev.factory.threadpool;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 20.05.13
 * Time: 19:33
 * To change this template use File | Settings | File Templates.
 */
public class ThreadPoolTask {

    private Task task;

    public ThreadPoolTask(Task t)
    {
        task = t;
    }

    public void go() throws InterruptedException
    {
        task.performWork();
    }
}

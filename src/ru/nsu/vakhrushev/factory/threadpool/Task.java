package ru.nsu.vakhrushev.factory.threadpool;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 20.05.13
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public interface Task {

    public void performWork() throws InterruptedException;

}

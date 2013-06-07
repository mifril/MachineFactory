package ru.nsu.vakhrushev.factory;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 17.05.13
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String args[])
    {
        try
        {
            Controller controller = new Controller("config.txt");
            controller.startWork();
        }
        catch (Exception e)
        {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

}

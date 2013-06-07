package ru.nsu.vakhrushev.factory.storages;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 21.05.13
 * Time: 1:11
 * To change this template use File | Settings | File Templates.
 */
public class Auto {

    private Motor motor;
    private Accessory accessory;
    private Body body;

    private int id;

    public Auto (int id, Motor motor, Body body, Accessory accessory)
    {
        this.id = id;
        this.motor = motor;
        this.body = body;
        this.accessory = accessory;
    }


    public int getId ()
    {
        return id;
    }

}

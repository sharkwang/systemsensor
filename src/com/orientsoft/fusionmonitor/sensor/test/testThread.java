package com.orientsoft.fusionmonitor.sensor.test;

import java.util.Random;

public class testThread{

private static final Object mThreadLock = new Object();

static class DoTaskThread extends Thread {

    public void run() {

            try {
                int wait = new Random().nextInt(10000);
                System.out.println("Waiting " + wait + " ms");
                Thread.sleep(wait);
            } catch (InterruptedException ex) {
            }
            synchronized (mThreadLock) {
                mThreadLock.notifyAll();
            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        synchronized (mThreadLock) {
            DoTaskThread thread = new DoTaskThread();
            thread.start();

            try {
                // Only wait 2 seconds for the thread to finish
                mThreadLock.wait(2000);
            } catch (InterruptedException ex) {
            }

            if (thread.isAlive()) {
                throw new RuntimeException("thread took too long");
            } else {
                System.out.println("Thread finished in time");
            }
        }
    }
}
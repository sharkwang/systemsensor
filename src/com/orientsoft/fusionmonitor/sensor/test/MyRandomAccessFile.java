package com.orientsoft.fusionmonitor.sensor.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;


public class MyRandomAccessFile
{

   public static long nextLong(Random rng, long n) {
     // error checking and 2^x checking removed for simplicity.
     long bits, val;
     do {
		bits = (rng.nextLong() << 1) >>> 1;
		val = bits % n;
	 } while (bits-val+(n-1) < 0L);
	return val;
   }
    
   public static void main(String[] args)
    {
        String fileName="";

        try
        {
            if(args.length>0)
            {
             fileName=args[0];            
            }

            // Create a new random access file.
            RandomAccessFile raf = new RandomAccessFile(
                fileName, "r");

            long fileLength = raf.length()-1;

             Random random = new Random();
           
            long rlong = nextLong(random, fileLength);

            // Reposition the file pointer to position 0.
            raf.seek(rlong);

            // Read in the next line.
	    while(raf.read()!='\n')
	    {
	     //skip , find other start point 
             //System.out.println("read ...\n");
	    }

	    System.out.println("line: " + raf.readLine());

            // Close the file.
            raf.close();
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }
}


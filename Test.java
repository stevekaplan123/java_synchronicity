import java.lang.Thread;
import java.util.ArrayList;

public class Test2{

public static void main(String[] args) 	{

		Aardvark[] aardvarks = new Aardvark[150];
		Anteater[] anteaters = new Anteater[50];
		Thread[] threadAnimals = new Thread[200];
		PriorityScheduler ps = new PriorityScheduler(2, 50);
		
	
		for (int i=0; i<150; i++)
		{
			String name = i+"-"+"Aardvark";
			aardvarks[i] = new Aardvark(name, ps);
			aardvarks[i].setPriority(i%4);
		}
		for (int i=0; i<50; i++)
		{
			String name = (i)+"-"+"Anteater";
			anteaters[i] = new Anteater(name, ps);
			anteaters[i].setPriority(i%4);
		}
		
		
		//actually start them
		for (int i=0; i<50; i++)
		{
			threadAnimals[i] = new Thread(aardvarks[i]);
			threadAnimals[i].start();
		}
	
		for (int i=0; i<50; i++)
		{
			threadAnimals[50+i] = new Thread(anteaters[i]);
			threadAnimals[50+i].start();
		}
	
		for (int i=0; i<100; i++)
		{
			threadAnimals[i+100] = new Thread(aardvarks[i+50]);
			threadAnimals[i+100].start();
		}
	
		for (int i=0; i<200; i++)
		{
			try{threadAnimals[i].join(); System.out.println(Thread.currentThread().getState()+" HI");}
			catch(InterruptedException e)
			{System.out.println(e);
				return;}
		}
	System.out.println("get here");
		
	}
}

import java.util.ArrayList;
 
public class PriorityScheduler implements Anthill {

	ArrayList<Anthill> myAnthills;
	int antsPerHill;
	int numHills;
	ArrayList<String> animalsEating;
	ArrayList<String> animalsWaiting;
	String myName;
	
	
	public PriorityScheduler(int num_hills, int num_ants)
	{
		animalsEating = new ArrayList<String>(0);
		animalsWaiting = new ArrayList<String>(0);
		myAnthills = new ArrayList<Anthill>(num_hills);
		antsPerHill = num_ants;
		numHills = num_hills;
		for (int i=0; i<numHills; i++)
		{
			String name=i+"";
			myAnthills.add(new BasicAnthill(antsPerHill, name));
		}
	}
/**
     * animal tries to eat at an anthill.
     *
     * @param  animal The animal that is attempting to ent at the anthill
     * @return true if the animal was able to start eating at the anthill,
     * false otherwise
     */
	
	
    public synchronized boolean tryToEatAtWithAnimal(Animal animal)
	{
		int howManyTimes=0;
	  boolean alreadyWaiting=false;
	  while(true)  //keep going from anthill to anthill seeing if it's possible to eat
	  {
		
	    for (Anthill anthill : myAnthills)
		{
			boolean priority=true;
			for (int i=0; i<animalsWaiting.size(); i++)
			{	
				//parse information about each animal currently waiting
				//to attain: animal name, anthill name, and priority level
				
				String info = animalsWaiting.get(i);
				int first_pos = info.indexOf("#");
				String animalName = info.substring(0, first_pos);
				
				String etc = info.substring(first_pos+1);
				int second_pos = etc.indexOf("@");
				String anthillName = etc.substring(0, second_pos);
				int num_priority = Integer.parseInt(etc.substring(second_pos+1));
				
				if (animal.getPriority() < num_priority && anthill.getName().equals(anthillName))
				{
					
					priority= false;
				}
				
			}
			
		
			if (anthill.tryToEatAtWithAnimal(animal)==false || priority==false)
			{
				
				if (!alreadyWaiting)
				{
					alreadyWaiting=true;
					animalsWaiting.add(animal.getName()+"#"+anthill.getName()+"@"+animal.getPriority());
				}
				
				try{					
					wait();
					continue;
					}
				catch (InterruptedException e) {}
			}
			else 
			{
				for (int i=0; i<animalsWaiting.size(); i++)
				{
					String temp=animal.getName()+"#"+anthill.getName()+"@"+animal.getPriority();
					if (temp.equals(animalsWaiting.get(i)))
					{
						System.out.println("removed waiter " +animalsWaiting.remove(i));
						break;
						//found myself...remove myself
					}
				}
				animalsEating.add(animal.getName()+"$"+anthill.getName());
				return true;
			}

		}
	   }
					
	}
    
    /**
     * animal exits the anthill.
     * 
     * @param animal The animal that is leaving the anthill
     */
    public synchronized void exitAnthillWithAnimal(Animal animal)
	{
		int found=0;
		for (int i=0; i<animalsEating.size(); i++)
		{
			String info = animalsEating.get(i);
			int pos = info.indexOf("$");
			String animalName = info.substring(0, pos);
			String anthillName = info.substring(pos+1);
			if (animalName.equals(animal.getName()))
			{
				for (int j=0; j<numHills; j++)
				{
					if (myAnthills.get(j).getName().equals(anthillName))
					{
						myAnthills.get(j).exitAnthillWithAnimal(animal);
						animalsEating.remove(i);
						break;
					}
				}
			}
		}
			
		
		notifyAll();
	}

    /**
    * returns the number of ants left in the anthill
    * must be checked before an animal tries to eat at to make sure there are ants left
    */
    public int antsLeft()
	{
		return antsPerHill*numHills;
	}

    /**
     * Sets the name of this anthill
     *
     * @param name The name of this anthill
     */
    public void setName(String name)
	{
		this.myName = name;
	}

    /**
     * Returns the name of this anthill
     *
     * @return The name of this anthill
     */
    public String getName()
	{
		return this.myName;
	}
}

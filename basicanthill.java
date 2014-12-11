/**
 * An Anthill is an object which can be eaten at by animals. Animals
 * themselves are responsible for indicating when they want to eat at
 * and when they are ready to leave. Anthills are responsible for
 * indicating if it is safe for an Animal to enter.
 *
 * When an animal wants to enter an anthill, it calls tryToJoin on the
 * anthill instance. If the animal has entered the anthill
 * successfully, tryToJoin returns true. Otherwise, tryToJoin
 * returns false. The animal simulates the time spent at the anthill,
 * and then must call exitAnthill on the same anthill instance it
 * entered.
 */
public class BasicAnthill implements Anthill {

	int ants;
	String myName;
	boolean anteaterEating;
	boolean aardvarkEating;
	int aardvarkCount;
	
	
	public BasicAnthill(int num, String anthillName)
	{
		this.setName(anthillName);
		this.ants = num;
		
		this.aardvarkCount=0;
		this.anteaterEating=false;
		this.aardvarkEating=false;
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
		if (antsLeft()==0)
			return false;
		
		String animalName = animal.getName();
		int pos = animalName.indexOf("-")+1;
		if (animalName.substring(pos).equals("Aardvark"))
		{
			if (this.anteaterEating || this.aardvarkCount==3)
			{
				return false;
			}
			
			this.aardvarkEating = true;
			this.aardvarkCount++;
			this.ants--;
			System.out.println(animalName+" has started eating at anthill-"+this.myName+" with priority " + animal.getPriority()+"\n");
		}
		else if (animalName.substring(pos).equals("Anteater"))
		{
			
			if (this.anteaterEating || this.aardvarkEating)
			{
				return false;
			}
			
			this.anteaterEating = true;
			this.ants--;
			System.out.println(animalName+" has started eating at anthill-"+this.myName+" with priority " + animal.getPriority()+"\n");
		}
		return true; 
	}
    
    /**
     * animal exits the anthill.
     * 
     * @param animal The animal that is leaving the anthill
     */
    public synchronized void exitAnthillWithAnimal(Animal animal)
	{
		String animalName = animal.getName();
		int pos = animalName.indexOf("-")+1;
		if (animalName.substring(pos).equals("Aardvark"))
		{
			this.aardvarkCount--;
			if (this.aardvarkCount==0)
				this.aardvarkEating=false;
		}
		else if (animalName.substring(pos).equals("Anteater"))
		{
			this.anteaterEating=false;
		}
		
	
		System.out.println(animalName+" has stopped eating at anthill-"+this.myName+" with priority "+animal.getPriority()+"\n");
		//System.out.println(antsLeft()+"ants");
	}

    /**
    * returns the number of ants left in the anthill
    * must be checked before an animal tries to eat at to make sure there are ants left
    */
    public int antsLeft()
	{
		return ants;
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

import java.util.Collection;

/**
 * An Aardvark is a fast Animal.
 */
public class Aardvark extends Animal {
    public Aardvark(String name, Collection<Anthill> anthills) {
        super(name, anthills);
    }

    public Aardvark(String name, Anthill anthill) {
        super(name, anthill);
    }

    protected int getDefaultSpeed() {
        return 6;
    }
}

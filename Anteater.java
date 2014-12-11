import java.util.Collection;

/**
 * An Anteater is a slow Animal.
 */
public class Anteater extends Animal {
    public Anteater(String name, Collection<Anthill> anthills) {
        super(name, anthills);
    }

    public Anteater(String name, Anthill anthill) {
        super(name, anthill);
    }

    protected int getDefaultSpeed() {
        return 4;
    }
}

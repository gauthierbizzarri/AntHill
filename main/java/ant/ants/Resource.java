package ant.ants;

public class Resource {

    protected ResourceType type;


    public Resource(  ResourceType type ) {
        this.type = type;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String toString() {
        return type.toString();
    }
}

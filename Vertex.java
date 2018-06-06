import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Vertex
{
    String name;
    Set<Vertex> neighbours;
    Map<Vertex,Edge> neighEdges;

    public Vertex(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Vertex> getNeighbours() {
        return neighbours;
    }

    public Edge getEdge(Vertex v)
    {
        return neighEdges.get(v);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void addNeighbour(Vertex v,Edge e)
    {
        if(v ==  null)
            return;

        if(neighbours == null)
            neighbours = new HashSet<Vertex>();

        this.neighbours.add(v);

        if(neighEdges == null)
            neighEdges = new HashMap<Vertex,Edge>();

        neighEdges.put(v, e);
    }

    @Override
    public String toString()
    {
        return name;
    }


}
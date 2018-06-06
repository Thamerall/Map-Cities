
public class Edge
{
    Vertex from;
    Vertex to;
    int weight;

    public Edge(Vertex from,Vertex to,int weight)
    {
        this.from = from;
        this.to = to;
        this.weight = weight;

        from.addNeighbour(to,this);

    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString()
    {
        return from.toString()+"-->"+to.toString()+" Weight:" + weight+"\n";
    }

}
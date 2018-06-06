import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Driver
{
    private static Map<String,Vertex> vertexMap = new HashMap<String,Vertex>();
    private static Set<Edge> edges = new HashSet<Edge>();
    private static String filename = "input.xls";

    private static List<Edge> crossBFSEdges = new ArrayList<Edge>();
    private static List<Edge> backEdges = new ArrayList<Edge>();
    private static List<Edge> dfsEdges = new ArrayList<Edge>();
    private static List<Edge> bfsEdges = new ArrayList<Edge>();
    private static int bfsWeight = 0;
    private static int dfsWeight = 0;

    public static void readXLSFile() throws IOException
    {
        InputStream ExcelFileToRead = new FileInputStream(filename);
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

        HSSFSheet sheet=wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;

        Iterator rows = sheet.rowIterator();
        String from = null;
        String to = null;
        int weight = -1;

        while (rows.hasNext())
        {
            row=(HSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            while (cells.hasNext())
            {
                cell=(HSSFCell) cells.next();

                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
                {
                    System.out.print(cell.getStringCellValue()+" ");
                    if(from == null)
                        from = cell.getStringCellValue();
                    else
                        to = cell.getStringCellValue();

                }
                else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                {
                    System.out.print(cell.getNumericCellValue()+" ");
                    weight = (int)cell.getNumericCellValue();
                    {
                        Vertex u = vertexMap.get(from);

                        if(u == null)
                        {
                            u = new Vertex(from);
                            vertexMap.put(from, u);
                        }

                        Vertex v = vertexMap.get(to);

                        if(v == null)
                        {
                            v = new Vertex(to);
                            vertexMap.put(to, v);
                        }

                        Edge e = new Edge(u,v,weight);

						/*if(u.getName().equals("Fargo") && v.getName().equals("Sioux Falls"))
						{
							e = u.getEdge(v);
						}
*/
                        edges.add(e);
                    }
                    from = null;

                }
                else
                {
                    //U Can Handel Boolean, Formula, Errors
                }
            }
            System.out.println();
        }

    }

    private static List<Vertex> doDFS(Vertex startVertex)
    {
        List<Vertex> results = new ArrayList<Vertex>();

        results.add(startVertex); // starting point

        Stack<Vertex> stack = new Stack<Vertex>();
        stack.push(startVertex);

        Map<Vertex,Boolean> isVisited = new HashMap<Vertex,Boolean>();
        isVisited.put(startVertex,true);

        boolean hasSuccessor;

        while(!stack.isEmpty())
        {
            hasSuccessor = false;
            startVertex = stack.peek();
            Set<Vertex> successors = startVertex.getNeighbours();
            if(successors != null && !successors.isEmpty())
            {
                for(Vertex nextVertex : successors)
                {
                    Edge e = startVertex.getEdge(nextVertex);

                    if(isVisited.get(nextVertex) == null || isVisited.get(nextVertex) == false)
                    {
                        dfsEdges.add(e);
                        dfsWeight = dfsWeight + e.getWeight();
                        hasSuccessor = true;
                        isVisited.put(nextVertex, true);
                        results.add(nextVertex);
                        stack.push(nextVertex);
                        break;
                    }
                    else
                    {
                        if(stack.contains(nextVertex))
                            backEdges.add(e);
                    }
                }
            }
            if(!hasSuccessor)
                stack.pop();

        }

        return results;

    }

    private static List<Vertex> doBFS(Vertex startVertex)
    {
        LinkedList<Vertex> results = new LinkedList<Vertex>();

        results.add(startVertex);
        Map<Vertex,Boolean> isVisited = new HashMap<Vertex,Boolean>();
        isVisited.put(startVertex,true);

        Queue<Vertex> queue = new LinkedList<Vertex>();
        queue.add(startVertex);

        while(queue != null && !queue.isEmpty())
        {
            Vertex head = queue.remove();
            Set<Vertex> successors = head.getNeighbours();

            if(successors != null && !successors.isEmpty())
            {
                for(Vertex temp : successors)
                {
                    Edge e = head.getEdge(temp);

                    if(isVisited.get(temp) == null || isVisited.get(temp) == Boolean.FALSE)
                    {
                        bfsEdges.add(e);
                        bfsWeight = bfsWeight + e.getWeight();
                        results.add(temp);
                        isVisited.put(temp, Boolean.TRUE);
                        queue.add(temp);
                    }
                    else
                        crossBFSEdges.add(e);
                }
            }

        }

        return results;
    }


    public static void main(String[] args) throws IOException
    {
        readXLSFile();

        Vertex startVertex = vertexMap.get("Grand Forks");

        System.out.println("DFS Traversal...\n\n");
        System.out.print("Traversal Order:\n");
        System.out.print(doDFS(startVertex));
        System.out.print("\nTotal Weight: "+dfsWeight);
        System.out.print("\nDFS Edges:\n"+dfsEdges);
        System.out.print("\nBack Edges:\n"+backEdges);

        System.out.println("\n\nBFS Traversal...\n\n");
        System.out.print("Traversal Order:\n");
        System.out.print(doBFS(startVertex));
        System.out.print("\nTotal Weight: "+bfsWeight);
        System.out.print("\nBFS Edges:\n"+bfsEdges);
        System.out.print("\nCross Edges:\n"+crossBFSEdges);


    }
}
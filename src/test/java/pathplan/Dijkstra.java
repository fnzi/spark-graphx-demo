package pathplan;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class CoordPos
{
    float x;
    float y;
}

class Size
{
    float width; //x
    float height; // y
}



class Node implements Comparable<Node>
{
    public final int nodeId;
    public Link[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Node previous;

    public CoordPos pos = new CoordPos();
    public Size objectSize = new Size();
    public int NodeType;

    public Node(int argNodeId) { nodeId = argNodeId; }
    public String toString() { return String.valueOf(nodeId); }

    public int compareTo(Node other)           // override function, used for priority queue
    {
        return Double.compare(minDistance, other.minDistance);
    }

}


class Link
{
    public final int nextNodeId;
    public Node nextNode;
    public double length;

    public Link(int argNextNodeId)
    { nextNodeId = argNextNodeId;}
}

public class Dijkstra
{
    public static void computePaths(Node source)
    {
        source.minDistance = 0.;
        PriorityQueue<Node> nodeQueue = new PriorityQueue<Node>();
        nodeQueue.add(source);

        while (!nodeQueue.isEmpty()) {
            Node u = nodeQueue.poll();

            // Visit each Link exiting u
            for (Link e : u.adjacencies)
            {
                Node v = e.nextNode;
                double length = e.length;
                double distanceThroughU = u.minDistance + length;
                if (distanceThroughU < v.minDistance) {            // evaluation rule
                    nodeQueue.remove(v);   // update v

                    v.minDistance = distanceThroughU ;
                    v.previous = u;    // link, multi-segment graph
                    nodeQueue.add(v);
                }
            }
        }
    }

    public static List<Node> getShortestPathTo(Node target)
    {
        List<Node> path = new ArrayList<Node>();
        for (Node node = target; node != null; node = node.previous)
            path.add(node);

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args)
    {
        Node v0 = new Node(1);
        v0.pos.x = (float) 0.9;
        v0.pos.y = (float) 0.6;
        Node v1 = new Node(2);
        v1.pos.x = (float) 2.15;
        v1.pos.y = (float) 0.6;
        Node v2 = new Node(3);
        v2.pos.x = (float) 3.4;
        v2.pos.y = (float) 0.6;
        Node v3 = new Node(4);
        v3.pos.x = (float) 0.9;
        v3.pos.y = (float) 1.8;
        Node v4 = new Node(5);
        v4.pos.x = (float) 2.15;
        v4.pos.y = (float) 1.8;
        Node v5 = new Node(6);
        v5.pos.x = (float) 3.4;
        v5.pos.y = (float) 1.8;
        Node v6 = new Node(7);
        v6.pos.x = (float) 0.9;
        v6.pos.y = (float) 3.0;
        Node v7 = new Node(8);
        v7.pos.x = (float) 2.15;
        v7.pos.y = (float) 3.0;
        Node v8 = new Node(9);
        v8.pos.x = (float) 3.4;
        v8.pos.y = (float) 3.0;

        v0.adjacencies = new Link[]{ new Link(2) }; // reference
        v1.adjacencies = new Link[]{ new Link(1),
                new Link(3),
                new Link(5) };

        v2.adjacencies = new Link[]{ new Link(2) };
        v3.adjacencies = new Link[]{ new Link(5),
                new Link(7) };

        v4.adjacencies = new Link[]{ new Link(2),
                new Link(4),
                new Link(6),
                new Link(8) };

        v5.adjacencies = new Link[]{ new Link(5),
                new Link(9) };

        v6.adjacencies = new Link[]{ new Link(4),
                new Link(8) };

        v7.adjacencies = new Link[]{ new Link(5),
                new Link(7),
                new Link(9)};

        v8.adjacencies = new Link[]{ new Link(6),
                new Link(8) };

        Node[] vertices = { v0, v1, v2, v3, v4, v5, v6, v7, v8 };
        for (Node v : vertices)
        {
            for (Link c : v.adjacencies)
            {
                for (Node nextv : vertices)
                {
                    if (c.nextNodeId == nextv.nodeId)
                    {
                        c.nextNode = nextv;
                    }
                }
                c.length = Math.sqrt((c.nextNode.pos.x - v.pos.x)*(c.nextNode.pos.x - v.pos.x) + (c.nextNode.pos.y - v.pos.y)*(c.nextNode.pos.y - v.pos.y));

                System.out.printf("length: %.2f \n", c.length);
            }
        }

        computePaths(v0);
        for (Node v : vertices)
        {
            System.out.println("Distance to " + v + ": " + v.minDistance);
            List<Node> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
    }
}

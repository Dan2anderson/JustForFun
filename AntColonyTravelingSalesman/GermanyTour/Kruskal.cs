//this is an unfinished class.
// TODO get Kruskals working 
//TODO implement a Dijkstras class

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GermanyTour
{
    class Kruskal
    {
        public List<WeightedEdge> MinSpanTree { get; set; }
        public List<WeightedEdge> EdgeList { get; set; }
       // public Node[] disjointSet { get; set; }
        public List<Node>[] disjointSet { get; set; }

        public Kruskal(List<Node> graph)
        {
            MinSpanTree = new List<WeightedEdge>();
            disjointSet = MakeDisjointSet(graph);
            EdgeList = MakeEdgeList(graph);
        }

        public void MakeMinSpanTree()
        {
            SortByTime();
            for(int i =0; i<EdgeList.Count;i++)
            {
                if (NotCycle(EdgeList[i]))
                {
                    Console.WriteLine("is not a cycle");
                    AddToTree(EdgeList[i]);
                }
            }
            int counter = 0;
            foreach (List<Node> item in disjointSet)
            {
               // Console.WriteLine("index, " + counter);
                foreach (Node node in item)
                {
                    Console.WriteLine(node.Name);
                    foreach (WeightedEdge e in node.Edges)
                    {
                        Console.WriteLine("Time, " + e.Time + "  node1 name " + e.Node1.Name + ",  node2 name" + e.Node2.Name);
                    }
                    Console.WriteLine();
                }
                Console.WriteLine();
                counter++;
            }
           
        }

        private void AddToTree(WeightedEdge edge)
        {
            int moveToIndex=99999;
            int moveFromIndex=99999;

            for (int i = 0; i < disjointSet.Length; i++)
            {
                foreach (Node node in disjointSet[i])
                {
                    if (node.Name.Equals(edge.Node1.Name))
                    {
                        moveToIndex=i;
                        node.Edges.Add(edge);
                    }
                    else if(node.Name.Equals(edge.Node2.Name))
                    {
                        moveFromIndex=i;

                    }
                }
            }
            int count = disjointSet[moveToIndex].Count;
            for(int i=0; i<disjointSet[moveFromIndex].Count;i++)
            {
                Node node =new Node(disjointSet[moveFromIndex][i].Name);
                node.Edges=disjointSet[moveFromIndex][i].Edges;
                disjointSet[moveToIndex].Add(node);
            }
            disjointSet[moveFromIndex] = new List<Node>();
            disjointSet[moveFromIndex].Add(new Node("AAA"));
        }

        private bool NotCycle(WeightedEdge edge)
        {
            //List<Node>[] disjointSet 

            for (int i = 0; i < disjointSet.Length; i++)
            {
                bool name1Found = false;
                bool name2Found = false;
                foreach (Node node in disjointSet[i])
                {
                    if (node.Name.Equals(edge.Node1.Name)) name1Found = true;
                    if (node.Name.Equals(edge.Node2.Name)) name2Found = true;
                }
                if (name1Found && name2Found)
                {
                    return false;
                }
            }
            return true;

        }

        public List<WeightedEdge> MakeEdgeList(List<Node> graph)
        {
            List<WeightedEdge> returnList = new List<WeightedEdge>();
            foreach (Node node in graph)
            {
                foreach (WeightedEdge edge in node.Edges)
                {
                    returnList.Add(new WeightedEdge(edge.Node1, edge.Node2, edge.Time, edge.Distance, edge.Cost));
                }
            }
            return returnList;
        }
        private List<Node>[] MakeDisjointSet(List<Node> graph)
        {
            List<Node>[] set = new List<Node>[graph.Count];
            for (int i = 0; i < set.Length; i++)
            {
                set[i] = new List<Node>();
                set[i].Add(new Node(graph[i].Name));
            }
            return set;
        }

        public void SortByTime()
        {
            EdgeList = EdgeList.OrderBy(x => x.Time).ToList();
        }
        public void SortByCost()
        {
            EdgeList = EdgeList.OrderBy(x => x.Cost).ToList();
        }
        public void SortByDistance()
        {
            EdgeList = EdgeList.OrderBy(x => x.Distance).ToList();
        }

        //makes a deep copy of a graph
        public List<Node> CopyGraph(List<Node> graph)
        {
            List<Node> graphReturn = new List<Node>();
            foreach (Node node in graph)
            {
                Node n1 = new Node(node.Name);
                foreach (WeightedEdge edge in node.Edges)
                {
                    Node n2= new Node(edge.Node2.Name);
                    n1.Edges.Add(new WeightedEdge(n1, n2, edge.Time, edge.Distance, edge.Cost));
                }
                graphReturn.Add(n1);
            }//end foreach
            return graphReturn;
        }
    }
}

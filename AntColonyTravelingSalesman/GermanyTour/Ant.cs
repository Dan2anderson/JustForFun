//Author Daniel Anderson

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GermanyTour
{
    class Ant
    {
        public List<Node> trail { get; set; }                        // trail nodes are deep copy of graph nodes
        public List<Node> FullGraph { get; set; }                     // is original graph
        public Node startCity { get; set; }
        public int startIndex { get; set; }

        //needs to remember the past nodes visited
        List<WeightedEdge> edgesVisited { get; set; }
        public decimal TimeTraveled { get; set; }

        public Ant(List<Node> graph,int index)
        {
            trail = new List<Node>();
            FullGraph = graph;
            startIndex = index;
            startCity = FullGraph[index];

            edgesVisited = new List<WeightedEdge>();
        }

        public void FindRout()
        {
            bool running = true;
            Node currCity = startCity;
            while (running)
            {
                
                WeightedEdge chosenEdge =ChooseCity(currCity.Edges);
                edgesVisited.Add(chosenEdge);
                //node wihth only the chosen edge
                Node tempNode = new Node(currCity.Name);
                tempNode.Edges.Add(chosenEdge);
                //add to trail
                trail.Add(tempNode);

                //  move from Node1 to node2
               // Console.WriteLine("from " + chosenEdge.Node1.Name + " To " + chosenEdge.Node2.Name);
                currCity = chosenEdge.Node2;

                //check if it is the start node
                if (currCity.Name.Equals(startCity.Name) && trail.Count == FullGraph.Count)
                {
                    running = false;    //tour is complete
                }
            }//end while

            TimeTraveled = FindTimeTraveled();

        }

        /// <summary>
        /// Finds the total cost for the rout the ant has chosen
        /// </summary>
        /// <returns></returns>
        private decimal FindTimeTraveled()
        {
            decimal temp = 0;
            foreach (WeightedEdge edge in edgesVisited)
            {
                temp += edge.Cost;              //other options for calculating cost  'Time' 'Distance' 'Cost'
            }
            return temp;
        }

        /// <summary>
        /// adds pheromon to the Weighted edges that have been traveled.
        /// </summary>
        public void DepositPheramone()
        {
            foreach (Node node in FullGraph)
            {
                foreach (WeightedEdge edge in node.Edges)
                {
                    foreach(WeightedEdge edgeVisited in edgesVisited)
                    {
                        if (edge.Node1.Name.Equals(edgeVisited.Node1.Name) && edge.Node2.Name.Equals(edgeVisited.Node2.Name))
                        {
                            edge.Pheramone += 10;

                        }
                    }
                }
            }
        }


        /// <summary>
        /// Evaluates the to city in the from citie's(nod's) edges and chooses the best city based on the cost and the highest amount of pheramone.
        /// </summary>
        /// <param name="edges"></param>
        /// <returns></returns>
        private WeightedEdge ChooseCity(List<WeightedEdge> edges) 
        {
            bool first = true;
            WeightedEdge tempEdge = null;
            decimal newProb = 0;
            Random random = new Random();
            int[] index = MakeRandomIndex(random,edges);
            bool running = true;
           while(running)
           {
                foreach (int ind in index)
                {
                    decimal threashold = (decimal)random.NextDouble();
                    if (!HasVisited(edges[ind]))     //if ant has not already visited Node2.
                    {

                        newProb = Probability(edges[ind], edges);
                        if (newProb > threashold)
                        {
                            tempEdge = edges[ind];
                            running = false;
                        }                  
                    }
                    else if (trail.Count == FullGraph.Count - 1 && edges[ind].Node2.Name.Equals(startCity.Name))
                    {
                        tempEdge = edges[ind];
                        running = false;

                    }
                }
            }

            return tempEdge;
        }//END CHooseCity


        /// <summary>
        /// generates int[]that stores randomly generated index positions. This is used to add some randomness and therefore 
        /// extra variability in what routs the ants choose.
        /// </summary>
        /// <param name="random"></param>
        /// <param name="edges"></param>
        /// <returns></returns>
        private int[] MakeRandomIndex(Random random, List<WeightedEdge> edges)
        {
            int count = edges.Count;
            int[] indexs = new int[count];
            for (int i = 0; i < count; i++)
            {
                indexs[i] = i;
            }
            for (int i = 0; i < count; i++)
            {
                int next = random.Next(count);
                int tempNum = indexs[i];
                indexs[i] = indexs[next];
                indexs[next] = tempNum;
            }
            return indexs;
        }


        /// <summary>
        /// checks if an ant has already visited that edge.
        /// </summary>
        /// <param name="edge"></param>
        /// <returns></returns>
        private bool HasVisited(WeightedEdge edge)
        {

            foreach (Node node in trail)
            {
                if (node.Name.Equals(edge.Node2.Name))
                {
                    return true;
                }
            }
            return false;
        }


        /// <summary>
        /// Uses the pheramone and cost to calculate the probability that the Ant choses that edge.  Is called in Choose city.
        /// </summary>
        /// <param name="currEdge"></param>
        /// <param name="edges"></param>
        /// <returns></returns>
        private decimal Probability(WeightedEdge currEdge,List<WeightedEdge> edges)
        {
            decimal sumPheramone=0;
            decimal sumTime=0;
            foreach (WeightedEdge edge in edges)
            {
                sumPheramone += (decimal)edge.Pheramone;
                sumTime += edge.Cost;                   //can be changed to 'Time' 'Distance' or 'Cost'
            }
            decimal evalWeight = sumTime - currEdge.Cost;  //get the invers of the weight so that those with 
                                                            //smaller costs return a higher value and are more likely to be chosen


            return (currEdge.Pheramone + (evalWeight)) / (sumPheramone * (sumTime));
        }


    }
}

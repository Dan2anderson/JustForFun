//Author Daniel Anderson

//is a collection of Ants
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GermanyTour
{
    class Colony
    {
        public double RateOfDecay { get; set; }
       public List<Ant> AllAnts { get; set; }
       public decimal BestTime { get; set; }
       public List<Node> BestTrail { get; set; }  
       public bool Consensus { get; set; }
       Stopwatch timer = new Stopwatch();
       int counter = 30000;

       public Colony(List<Node> graph)
       {
           RateOfDecay = 0.2;
           Consensus = false;
           AllAnts = new List<Ant>();
           ReInitAnts(graph);
           BestTime = 99999;
       }

        /// <summary>
        /// the method loops untill a consensus is found by all Ants in the colony.  
        /// </summary>
        /// <param name="graph"></param>
       public void Search(List<Node> graph)
       {
           timer.Start();
           while (!Consensus)
           {
               TimeStep();
               Console.SetCursorPosition(0, 0);
               Console.WriteLine("Current best Cost: " + BestTime);
               Decay(graph);
               //reInitAnts()?
               if (!Consensus)
               {
                   ReInitAnts(graph);
               }
               
               if (timer.ElapsedMilliseconds >counter)
               {
                   Console.SetCursorPosition(0, 2);
                   foreach (Node node in BestTrail)
                   {             
                       Console.WriteLine(node.Name + "   pharamone * " + node.Edges[0].Pheramone + " *    To, " + node.Edges[0].Node2.Name);
                       counter = counter + 30000;
                   }
               }
           }
           //print best rout
           foreach (Node node in AllAnts[0].trail)
           {
               Console.WriteLine(node.Name + "  edge +" + node.Edges[0].Node1.Name + "," + node.Edges[0].Node2.Name + "   pharamone " + node.Edges[0].Pheramone);
           }
       }

        /// <summary>
        /// resets all of the Ants so they don't remember past routs.
        /// </summary>
        /// <param name="graph"></param>
       public void ReInitAnts(List<Node> graph)
       {
           AllAnts.Clear();
           int num = 0;
           for (int i = 0; i < 22; i++)
           {
               Ant ant = new Ant(graph, num);
               AllAnts.Add(ant);
               num++;
               if (num == graph.Count) num = 0;
           }
       }

        /// <summary>
        /// calls all the needed Ant methods for each ant for each iteration of the while loop. 
        /// </summary>
       public void TimeStep()
       {
           foreach (Ant ant in AllAnts)  //all ants find a rout.
           {
               ant.FindRout();
           }
           foreach (Ant ant in AllAnts)// after all routs found deposit pheromone
           {
               ant.DepositPheramone();
           }
           foreach (Ant ant in AllAnts) //record the shortest rout so far. 
           {
               if (ant.TimeTraveled < BestTime)
               {
                   BestTime = ant.TimeTraveled;
                   BestTrail = ant.trail;
               }
           }
           //check if routs are same
           int count=0;
           for (int i = 1; i < AllAnts.Count; i++)
           {
               if (AreTrailsSame(AllAnts[0].trail, AllAnts[i].trail)) count++;
           }
           if (count - 1 == AllAnts.Count) Consensus = true;
       }


        /// <summary>
        /// Decays the pheramone on each WeightedEdge 
        /// </summary>
        /// <param name="graph"></param>
       public void Decay(List<Node> graph)
       {
           foreach (Node node in graph)
           {
               foreach (WeightedEdge edge in node.Edges)
               {
                   edge.Pheramone = (int)(edge.Pheramone * RateOfDecay);
                   if (edge.Pheramone < 1) edge.Pheramone = 1; ///so we don't have division problems later
               }
           }
       }


        /// <summary>
        /// checks if the two trail paramaters are the same. 
        /// </summary>
        /// <param name="trail1"></param>
        /// <param name="trail2"></param>
        /// <returns></returns>
       private bool AreTrailsSame(List<Node> trail1,List<Node> trail2)
       {
           for (int i = 0; i<trail1.Count; i++)
           {
               if (trail1[i].Name.Equals(trail2[i].Name)) { continue; }
               else { return false; }
           }
               return true;
       }
    }
}

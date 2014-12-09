//Author Daniel Anderson
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GermanyTour
{
    class WeightedEdge
    {
        public Node Node1 { get; set; }  //this is the from city/node
        public Node Node2 { get; set; }  //this is the to city/node

        public bool Visited { get; set; }
        public int Pheramone { get; set; } //initialized to 1 (should never be zero).  this increases as it is traveled more aften by the ants

        //different costs that can be used in the calculation.
        public decimal Time { get; set; }
        public decimal Distance { get; set; }
        public decimal Cost { get; set; }
        
        public WeightedEdge(Node n1,Node n2,decimal time,decimal dist,decimal cost){
            Node1=n1;
            Node2=n2;
            Time=time;
            Distance=dist;
            Cost = cost;
            Pheramone = 1;
        }
    }
}

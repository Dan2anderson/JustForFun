//Author Daniel Anderson
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GermanyTour
{
    class Node
    {
        public List<WeightedEdge> Edges { get; set; }
        public string Name { get; set; }
        public bool Visited { get; set; }


        public Node(string from)
        {
            Name = from;
            Edges = new List<WeightedEdge>();
        }


    }
}

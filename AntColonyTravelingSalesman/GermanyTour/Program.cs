//Author Daniel Anderson

//This program uses an Ant colony algorithm to find an optimized (not neccessarily best) rout
//through a list of cities.  In this case a tour through Germany.
//Runs continually and prints the best rout and cost it has found.  
//Optimal running time is 1-5 minutes  with single thread.
//TODO implement multi threading and compare speed. 

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Data.Entity.Validation;

namespace GermanyTour
{
    class Program
    {
        static void Main(string[] args)
        {
          //  ImportEdges("dataToUpload.csv");    //import from csv file to database.  only needs to run once to create the DB on a given computer 

            List<Node> graph = new List<Node>();
       
            #region make Graph.
            using (EdgeEntities db = new EdgeEntities())
            {
                MakeNodes(db,graph);
                AddEdge(db, graph);
                
            }
            #endregion


            Colony colony = new Colony(graph);
            colony.Search(graph);

        }//END Main


        /// <summary>
        /// Is static.
        /// Takes data form  EdgeEntities(the database) creates a new edge and adds the edge to the correct node in the graph (List of Nodes)
        /// </summary>
        /// <param name="db"></param>
        /// <param name="graph"></param>
        public static void AddEdge(EdgeEntities db, List<Node> graph)
        {
            foreach (Vertex v in db.Vertices)
            {
                foreach (Node n in graph)
                {
                    if (v.From.Equals(n.Name))
                    {
                        Node n2=null;
                        foreach(Node nb in graph){
                            if(v.To.Equals(nb.Name))n2=nb;
                        }
                        n.Edges.Add(new WeightedEdge(n,n2,v.Time,v.Rout,v.Cost));

                    }
                }
            }
        }

        /// <summary>
        /// Takes the data from the database (EdgeEntities) makes a new node and adds it to the graph (List of Nods)
        /// </summary>
        /// <param name="db"></param>
        /// <param name="graph"></param>
        public static void MakeNodes(EdgeEntities db,List<Node> graph)
        {
            foreach (Vertex v in db.Vertices)
            {
                bool matchFrom=false;
                bool matchTo= false;
                foreach (Node n in graph)
                {
                    if (v.From.Equals(n.Name)) matchFrom = true;
                    else if (v.To.Equals(n.Name)) matchTo = true;
                    else { }
                }
                if (!matchFrom) graph.Add(new Node(v.From));
                if (!matchTo) graph.Add(new Node(v.To));
            }
        }






        /// <summary>
        /// Imports the data from a csv file and puts it into a data base (EdgeEntities).
        /// </summary>
        /// <param name="path"> string that represents the source of the csv file.</param>

        public static void ImportEdges(string path)
        {
            using (EdgeEntities db = new EdgeEntities())
            {
                using(StreamReader reader = new StreamReader(path))
                {
                    reader.ReadLine();
                    while (!reader.EndOfStream)
                    {
                        Vertex vert = GetCsvData(reader.ReadLine());
                        if (vert.From.Equals(vert.To))
                        {                           
                              //do nothing
                        }
                        else
                        {
                            db.Vertices.Add(vert);
                        }
                                     
                    }
                    try
                    {
                        db.SaveChanges();
                    }
                    catch (DbEntityValidationException e)
                    {
                        foreach (var ev in e.EntityValidationErrors)
                        {
                            Console.WriteLine("entity: "+ev.Entry.Entity.GetType().Name+"   state: "+ ev.Entry.State);
                            foreach (var v in ev.ValidationErrors)
                            {
                                Console.WriteLine("Name " + v.PropertyName + "    error: " + v.ErrorMessage);
                            }
                        }
                    }
                    
                }
            }
        }
        /// <summary>
        /// Parses the csv data and maks a Vertex to add to the data base. 
        /// </summary>
        /// <param name="line"></param>
        /// <returns></returns>
        private static Vertex GetCsvData(String line)
        {
            Vertex vertex = new Vertex();
            try
            {
                line = line.Replace("\"", "");

            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
                
                StringBuilder sb = new StringBuilder();
                int currCase = 1;

                foreach (char ch in line)
                {
                    if (ch == ',')
                    {
                        switch (currCase)
                        {
                            case 1:
                                sb.Append(ch);
                                currCase = 2;
                                break;
                            case 2:
                                vertex.From = sb.ToString();
                                sb.Clear();
                                currCase = 3;
                                break;
                            case 3:
                                sb.Append(ch);
                                currCase = 4;
                                break;
                            case 4:
                                vertex.To = sb.ToString();
                                sb.Clear();
                                currCase = 5;
                                break;
                            case 5:
                                vertex.Rout = decimal.Parse(sb.ToString());
                                sb.Clear();
                                currCase = 6;
                                break;
                            case 6:
                                vertex.Time = decimal.Parse(sb.ToString());
                                sb.Clear();
                                currCase = 7;
                                break;
                            case 7:
                                vertex.Cost = decimal.Parse(sb.ToString());
                                sb.Clear();
                                currCase = 8;
                                break;
                            case 8:
                                vertex.RTC = decimal.Parse(sb.ToString());
                                sb.Clear();
                                currCase = 9;
                                break;

                        }
                    }
                    else
                    {
                        sb.Append(ch);
                    }
                }
         
            return vertex;

        }
    }
}

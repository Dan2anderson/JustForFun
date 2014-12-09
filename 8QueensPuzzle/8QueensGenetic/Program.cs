/**Author Daniel Anderson
 * 
 * this program uses a genetic algorithm to find a solution to the 8 queens problem. 
 * genetic algorithms http://en.wikipedia.org/wiki/Genetic_algorithm
 * 8 queens problem http://en.wikipedia.org/wiki/Eight_queens_puzzle
 * 
 * 
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace _8QueensGenetic
{
    class Program
    {
        static void Main(string[] args)
        {
            Population pop = new Population(8, 10); //create population of 10 different board setups.
            //find each individuals fitness
            foreach (Individual ind in pop.individuals)
            {
                ind.FindFitnes();
            }
            //evolution   find a board where the queens do not attach each other at all.
            Console.WriteLine("evolving");
            while (!pop.solution)
            {
                pop.FindMate();
                pop.UpdatePopulation();
            }

            //solution found
            if (pop.solution)
            {
                Console.WriteLine("\nsolution Found in "+pop.generation+" generations.");
                Individual solution = pop.GetSolution();
                for (int i = 0; i < solution.genome.Length; i++)
                {
                    Console.Write(solution.genome[i]);
                }
                Console.WriteLine("\n\n\n\n");
            }


        }//END main
    }

}

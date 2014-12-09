using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _8QueensGenetic
{
    class Population
    {
        public List<Individual> individuals = new List<Individual>();
        public List<Individual> children = new List<Individual>();
       public  bool solution = false;
        public int generation=0;
        private int boardSize;

        Random random = new Random();
        /**
         @paramaters  int of the size of the boards
         @paramater  int how many individuals you want in the population*/
        public Population(int boardSize,int populationSize)
        {
            this.boardSize = boardSize;
            int[] rows = {0,1,2,3,4,5,6,7};
            for (int i = 0; i < populationSize; i++ )
            {
                for (int j = 0; j < boardSize; j++)
                {
                    int temp = rows[j];
                    int randNumber = random.Next(8);
                    int tempHolder = rows[j];
                    rows[j] = rows[randNumber];
                    rows[randNumber] = tempHolder;
                }
                individuals.Add(new Individual(boardSize, rows));
            }
        }//END constructor

        public void FindMate()
        {
            IEnumerable<Individual> readyTo = individuals.OrderBy(i => i.fitnes);
            
            Random rand = new Random();
            double choosyness = rand.NextDouble();
            foreach (Individual ind in readyTo)
            {
                double targetMate = ind.fitnes;
                foreach (Individual mate in readyTo)
                {
                    if (ind.fitnes <= mate.fitnes || (ind.fitnes-(ind.fitnes*rand.NextDouble()))<=mate.fitnes)
                    {
                       children.Add(new Individual(boardSize, Copulate(ind,mate,rand.Next(boardSize))));

                    }
                    else
                    {
                       
                    }
                }
            }
        }//END findMate

        private int[] Copulate(Individual mom,Individual dad,int geneSplit)
        {
           // Console.Write("\n**method starts**\n\n  ");
            int[] child = new int[boardSize];
            int[] tempDadGenes = new int[dad.genome.Length];
            for (int i = 0; i < dad.genome.Length; i++)
            {
                tempDadGenes[i] = dad.genome[i];
            }
            
           /** Console.Write("dad before  ");
            for(int i=0; i<tempDadGenes.Length; i++)
            {
                            Console.Write(tempDadGenes[i]);
            }
            Console.WriteLine();*/
            for(int i =0 ; i<=geneSplit; i++)
            {
                child[i]=mom.genome[i];
                
                for(int j=0; j<tempDadGenes.Length; j++)
                {
                    if(child[i]==tempDadGenes[j])tempDadGenes[j]=-1;
                }
            }///END for
           /** Console.WriteLine("\n*****");
            Console.Write("child befor  ");
            for(int i=0; i<child.Length; i++)
            {
                Console.Write(child[i]);
            }
            Console.WriteLine();
            Console.Write("dad before  ");
             for(int i=0; i<tempDadGenes.Length; i++)
            {
                            Console.Write(tempDadGenes[i]);
            }
            Console.WriteLine();*/

            for (int i = geneSplit + 1; i < child.Length; i++)
            {
                for (int j = 0; j < tempDadGenes.Length; j++)
                {
                    if (tempDadGenes[j] != -1)
                    {
                        child[i] = tempDadGenes[j];
                        tempDadGenes[j] = -1;
                        break;
                    }
                }
            }//END for

            //Random chance for mutation@@@@
            Random rand = new Random();
            double mutChance = 0.1;
            for(int i = 0; i < child.Length; i++)
            {
                if (mutChance >= rand.NextDouble())
                {
                    int n1 = child[i];
                    int place2 = rand.Next(boardSize);
                    child[i] = child[place2];
                    child[place2] = n1;
                }
            }

                return child;

        }//END Copulate


        //update the list of individuals
        public void UpdatePopulation()
        {
            List<Individual> tempPop = new List<Individual>();
            //find fitnes of children
            foreach (Individual ind in children)
            {
                ind.FindFitnes();
            }

            //comparefiutnes of all adults and children
                foreach (Individual ind in children)
                {
                    tempPop.Add(ind);

                }
                foreach (Individual ind in individuals)
                {
                    tempPop.Add(ind);
                }
            //keep only top fitnes 
              IEnumerable<Individual> tempEnum = tempPop.OrderByDescending(x => x.fitnes);
            //set new list of individuals
              int count = individuals.Count;

              for (int i = 0; i < count; i++)
              {
                  if (i == 0)
                  {
                      individuals.RemoveRange(0, count);
                  }
                  individuals.Add(tempEnum.ElementAt(i));
              }
            //clear children
              generation++;
              children.RemoveRange(0, children.Count);
              SolutionExists();
        }//END populationUpdate

        private void SolutionExists()
        {
            foreach (Individual ind in individuals)
            {
                if (ind.fitnes == 28) solution = true;
            }
        }

        public Individual GetSolution()
        {
            foreach (Individual ind in individuals)
            {
                if (ind.fitnes == 28) return ind;
            }
            return null;
        }

    }//END class
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _8QueensGenetic
{
    class Individual
    {
        public int[] genome;
        public int fitnes { get; set; }

        public Individual(int boardSize,int[] rows)  //the row number that each queen is on in each column is an aliel in the genom of the board layout
        {
            genome = new int[boardSize];

            for (int i = 0; i < rows.Length; i++)
            {
                genome[i] = rows[i];
            }
        }

        public void FindFitnes()
        {
            //checks diagonal collisions only.
            int counter=0;
            for (int column = 0; column < genome.Length; column++)
            {
                //check right and down
                int rowPlace = 0;
                for (int j = column+1; j < genome.Length; j++)
                {
                    rowPlace++;
                    if (genome[column] + rowPlace == genome[j])
                    {
                        counter++;
                        j=genome.Length;//stop the loop
                    }
                }
                //left and up
                rowPlace = 0;
                for (int j = column - 1; j >=0 ; j--)
                {
                    rowPlace++;
                    if(genome[column]-rowPlace == genome[j])
                    {
                        counter++;
                        j = -1;
                    }
                }
                //right and up
                rowPlace = 0;
                for (int j = column + 1; j < genome.Length; j++)
                {
                    rowPlace++;
                    if (genome[column] - rowPlace == genome[j])
                    {
                        counter++;
                        j = genome.Length;
                    }
                }
                //left and down
                rowPlace = 0;
                for (int j = column - 1; j >= 0; j--)
                {
                    rowPlace++;
                    if (genome[column] + rowPlace == genome[j])
                    {
                        counter++;
                        j = -1;
                    }
                }
            }//END column loop
            fitnes = 28-counter;
        }//End findFitnes


    }
}

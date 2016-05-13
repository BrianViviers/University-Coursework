// 10253311 - Brian Viviers

using System;

namespace AntSimulation
{
#if WINDOWS || XBOX
    static class Program
    {
        static public AntSimulator antSimulator;
        static public AntSimulationForm antSimulationForm;

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        static void Main(string[] args)
        {
            antSimulationForm = new AntSimulationForm();

            // Set the forms initial size to the game window size plus enough for the 
            // settings panel to the right and for the form head and bottom
            antSimulationForm.Size = new System.Drawing.Size(1024 + 190, 768 + 40);
            
            // Show the form
            antSimulationForm.Show();

            // Create a game with the panel and the size as parameters
            antSimulator = new AntSimulator(antSimulationForm.getDrawSurface());

            // Run the game
            antSimulator.Run();
        }
    }
#endif
}
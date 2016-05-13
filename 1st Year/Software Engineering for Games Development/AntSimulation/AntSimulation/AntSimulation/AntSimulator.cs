// 10253311 - Brian Viviers

using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using System.Windows.Forms;

namespace AntSimulation
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class AntSimulator : Microsoft.Xna.Framework.Game
    {
        private GraphicsDeviceManager graphics;
        private SpriteBatch spriteBatch;

        private IntPtr drawSurface;                 // Used in drawing to a form. URL to code source below.

        private enum GameState { paused, playing }  // Game can be paused
        private GameState currentGameState;         // The state of the game

        private List<BlackAnt> blackAntList;        // Contains all the black ants
        private List<RedAnt> redAntList;            // Contains all the red ants
        private List<Nest> blackNestList;           // Contains the nests of the black ants
        private List<Nest> redNestList;             // Contains the nests of the red ants
        private List<Food> foodList;                // Contains all the food placed

        private Ant antFollowed;                    // The ant currently marked to follow

        private int foodQuantity;                   // The initial food amount to place
        private bool restartGame;                   // 
        private int numberBlackAnts, numberRedAnts; // The ants count
        private int doThird;                        // Used to only let a third of the ants ask for info during one time step

        private Texture2D antFoodImage;             //
        private Texture2D backgroundTexture;        // All the images used
        private Texture2D[] blackAntImages;         // in the ant simulation
        private Texture2D[] redAntImages;           // 

        private Rectangle viewPortBounds;           // The bounds of the ant simulation window.

        /// <summary>
        /// AntSimulator constructor.
        /// </summary>
        /// <param name="drawSurface">The location of where to draw the game in the form</param>
        public AntSimulator(IntPtr drawSurface)
        {
            graphics = new GraphicsDeviceManager(this);

            restartGame = false;

            // Initial window size of 1024 x 768
            viewPortBounds = new Rectangle(0, 0, 1024, 768);
            graphics.PreferredBackBufferWidth = viewPortBounds.Width;
            graphics.PreferredBackBufferHeight = viewPortBounds.Height;          

            Content.RootDirectory = "Content";

            # region Code to enable drawing to a form (URL: http://royalexander.wordpress.com/2008/10/09/xna-30-and-winforms-the-easy-way/)

            // Code to enable drawing to a form
            this.drawSurface = drawSurface;

            graphics.PreparingDeviceSettings +=
            new EventHandler<PreparingDeviceSettingsEventArgs>(graphics_PreparingDeviceSettings);

            System.Windows.Forms.Control.FromHandle((this.Window.Handle)).VisibleChanged +=
            new EventHandler(AntSimulator_VisibleChanged);
        }

        /// <summary>
        /// Event capturing the construction of a draw surface and makes sure this gets redirected to
        /// a predesignated drawsurface marked by pointer drawSurface
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        void graphics_PreparingDeviceSettings(object sender, PreparingDeviceSettingsEventArgs e)
        {
            e.GraphicsDeviceInformation.PresentationParameters.DeviceWindowHandle = drawSurface;
        }

        /// <summary>
        /// Occurs when the original gamewindows' visibility changes and makes sure it stays invisible
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AntSimulator_VisibleChanged(object sender, EventArgs e)
        {
            if (System.Windows.Forms.Control.FromHandle((this.Window.Handle)).Visible == true)
                System.Windows.Forms.Control.FromHandle((this.Window.Handle)).Visible = false;
        }

            # endregion Code to enable drawing to a form

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            LoadGame();
            base.Initialize();
        }

        /// <summary>
        /// All the necessary variables used when creating a new simulation.
        /// Moved out of Initialize() to enable restarting of the simulation
        /// without closing the application
        /// </summary>
        /// <param name="blackAnts">The black ants count</param>
        /// <param name="redAnts">The red ants count</param>
        public void LoadGame(int blackAnts = 500, int redAnts = 200)
        {
            // To increase visual performance
            graphics.SynchronizeWithVerticalRetrace = false;
            IsFixedTimeStep = false;
            // End

            IsMouseVisible = true;

            currentGameState = GameState.playing;

            blackAntList = new List<BlackAnt>();
            redAntList = new List<RedAnt>();
            blackNestList = new List<Nest>();
            redNestList = new List<Nest>();
            foodList = new List<Food>();

            Vector2 antPosition;
            Random myRandom = new Random();
            BlackAnt blackAnt;
            RedAnt redAnt;

            numberBlackAnts = blackAnts;
            numberRedAnts = redAnts;
            foodQuantity = 200;

            // The size of the ants
            int antWidth = 10;
            int antHeight = 10;

            // Add all the black ants to a list at random locations
            for (int i = 0; i < numberBlackAnts; i++)
            {
                antPosition.X = myRandom.Next(0, (int)(viewPortBounds.Width - antWidth));
                antPosition.Y = myRandom.Next(0, (int)(viewPortBounds.Height - antHeight));
                blackAnt = new BlackAnt(antPosition, viewPortBounds);
                blackAntList.Add(blackAnt);
            }

            // Add all the red ants to a list at random locations
            for (int i = 0; i < numberRedAnts; i++)
            {
                antPosition.X = myRandom.Next(0, (int)(viewPortBounds.Width - antWidth));
                antPosition.Y = myRandom.Next(0, (int)(viewPortBounds.Height - antHeight));
                redAnt = new RedAnt(antPosition, viewPortBounds);
                redAntList.Add(redAnt);
            }

            // This should only be called when the user restarts the game
            // as it is called firstly in 
            if (restartGame == true)
                AddImages();

            restartGame = true;
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place
        /// to load all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            blackAntImages = new Texture2D[3];
            redAntImages = new Texture2D[3];

            backgroundTexture = Content.Load<Texture2D>("images\\Ground");
            antFoodImage = Content.Load<Texture2D>("images\\AntFood");

            blackAntImages[0] = Content.Load<Texture2D>("images\\BlackAnt");
            blackAntImages[1] = Content.Load<Texture2D>("images\\BlackAntWithFood");
            blackAntImages[2] = Content.Load<Texture2D>("images\\BlackAntNest");

            redAntImages[0] = Content.Load<Texture2D>("images\\RedAnt");
            redAntImages[1] = Content.Load<Texture2D>("images\\RedAntWithFood");
            redAntImages[2] = Content.Load<Texture2D>("images\\RedAntNest");

            AddImages();
        }

        /// <summary>
        /// Method to be called whenever the simulation is restarted
        /// with different amount of ants so that the new ants will have
        /// images displayed.
        /// </summary>
        private void AddImages()
        {
            foreach (Ant ant in redAntList)
            {
                ant.AntImage = redAntImages[0];
                ant.AntImageWithFood = redAntImages[1];
            }

            foreach (Ant ant in blackAntList)
            {
                ant.AntImage = blackAntImages[0];
                ant.AntImageWithFood = blackAntImages[1];
            }
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            Content.Unload();
        }

        /// <summary>
        /// Called from the AntSimulationForm to play or pause the game on button click
        /// </summary>
        public void PausePlay()
        {
            if (currentGameState == GameState.paused)
                currentGameState = GameState.playing;
            else
                currentGameState = GameState.paused;
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            // This limits wander() being called more than once for each ant.
            bool wanderOnce;

            int[] fromTO = new int[2];

            // exit when escape key is pressed
            if (Keyboard.GetState().IsKeyDown(Microsoft.Xna.Framework.Input.Keys.Escape))
                this.Exit();

            // Check if the game is not paused
            if (!(currentGameState == GameState.paused))
            {
                // Loop to simulate the red ants
                foreach (RedAnt ant in redAntList)
                {
                    wanderOnce = false;

                    if (ant.FoodKnownPosition == Vector2.Zero && ant.HaveFood == false)
                        ant.SearchForFood(blackAntList, ref wanderOnce);
                    else if (ant.FoodKnownPosition != Vector2.Zero && ant.HaveFood == false)
                    {
                        ant.CollectKnownFood();
                        ant.SearchForMoreFood(blackAntList);
                    }
                    else if (ant.HaveFood == true && ant.NestKnown != null)
                        ant.ReturnFood(redNestList);
                    else
                        ant.WanderLimiter(ref wanderOnce);

                    ant.SearchForNest(redNestList, ref wanderOnce);

                    // Ask for food and nest location
                    fromTO = CalculateAskingLoop(numberRedAnts);

                    for (int i = fromTO[0]; i < fromTO[1]; i++)
                    {
                        ant.AskFoodLocation(redAntList[i]);
                        ant.AskNestLocation(redAntList[i], redNestList);
                    }
                }

                // Loop to simulate the black ants
                foreach (BlackAnt ant in blackAntList)
                {
                    wanderOnce = false;

                    if (ant.FoodKnown == null && ant.HaveFood == false)
                        ant.SearchForFood(foodList, ref wanderOnce);

                    else if (ant.FoodKnown != null && ant.HaveFood == false)
                    {
                        ant.CollectKnownFood(foodList);
                        ant.SearchForMoreFood(foodList);
                    }
                    else if (ant.HaveFood == true && ant.NestKnown != null)
                        ant.ReturnFood(blackNestList);
                    else
                        ant.WanderLimiter(ref wanderOnce);

                    ant.SearchForNest(blackNestList, ref wanderOnce);

                    // Ask for food and nest location
                    fromTO = CalculateAskingLoop(numberBlackAnts);

                    for (int i = fromTO[0]; i < fromTO[1]; i++)
                    {
                        ant.AskFoodLocation(blackAntList[i], foodList);
                        ant.AskNestLocation(blackAntList[i], blackNestList);
                    }
                }

                if (doThird == 2)
                    doThird = 0;
                else
                    doThird++;

                RemoveUsedFood();

                base.Update(gameTime);
            }
        }

        private int[] CalculateAskingLoop(int totalAnts)
        {
            int[] fromTO = new int[2];
            int thirdAnts = totalAnts / 3;

            if (doThird == 0)
            {
                fromTO[0] = 0;
                fromTO[1] = totalAnts - thirdAnts * 2;
            }
            else if (doThird == 1)
            {
                fromTO[0] = totalAnts - thirdAnts * 2;
                fromTO[1] = totalAnts - thirdAnts;
            }
            else
            {
                fromTO[0] = totalAnts - thirdAnts;
                fromTO[1] = totalAnts;
            }

            return fromTO;
        }

        /// <summary>
        /// Remove food if its quantity is finished.
        /// </summary>
        private void RemoveUsedFood()
        {
            for (int i = 0; i < foodList.Count; i++)
            {
                if (foodList[i].FoodQuantity <= 0)
                {
                    foodList[i] = null;
                    foodList.RemoveAt(i);
                }
            }
        }

        /// <summary>
        /// Add newly placed food to the foodList
        /// </summary>
        /// <param name="e"></param>
        public void AddFood(MouseEventArgs e)
        {
            foreach (Food foods in foodList)
            {
                if (foods.FoodRectanglePosition.Contains(new Point(e.X, e.Y)))
                {
                    foods.FoodQuantity += foodQuantity;
                    return;
                }
            }
            Food food;
            Vector2 foodPosition = new Vector2(e.X, e.Y);
            food = new Food(foodPosition, antFoodImage, foodQuantity);
            foodList.Add(food);
        }

        /// <summary>
        ///  Add newly placed black ant nest to the blackNestList
        /// </summary>
        /// <param name="e"></param>
        public void AddNest(MouseEventArgs e, string antType)
        {
            Nest nest;
            Vector2 nestPosition;

            if (antType == "black")
            {
                nestPosition = new Vector2(e.X, e.Y);
                nest = new Nest(nestPosition, blackAntImages[2]);
                blackNestList.Add(nest);
            }
            else
            {
                nestPosition = new Vector2(e.X, e.Y);
                nest = new Nest(nestPosition, redAntImages[2]);
                redNestList.Add(nest);
            }
        }

        /// <summary>
        /// Remove the object which is at the position of the mouse click
        /// </summary>
        /// <param name="e">The coordinates of the mouse click</param>
        public void RemoveObject(MouseEventArgs e)
        {
            Point p = new Point(e.X, e.Y);

            foreach (Nest nest in blackNestList.ToList())
            {
                if (nest.NestRectanglePosition.Contains(p))
                    blackNestList.Remove(nest);
            }

            foreach (Nest nest in redNestList.ToList())
            {
                if (nest.NestRectanglePosition.Contains(p))
                    redNestList.Remove(nest);
            }

            foreach (Food food in foodList.ToList())
            {
                if (food.FoodRectanglePosition.Contains(p))
                    foodList.Remove(food);
            }
        }

        /// <summary>
        /// Change the speed of the simulation
        /// </summary>
        /// <param name="speed"></param>
        public void ChangeSpeed(float speed)
        {
            foreach (Ant ant in blackAntList)
            {
                ant.MaxAntSpeed += speed;
            }

            foreach (Ant ant in redAntList)
            {
                ant.MaxAntSpeed += speed;
            }
        }

        /// <summary>
        /// When the resolution size has been changed on the form
        /// then the window size needs to be changed in the game too.
        /// The game is restarted on change.
        /// </summary>
        /// <param name="width">The width of the new resolution</param>
        /// <param name="height">The height of the new resolution</param>
        public void ChangeResolutionSize(int width, int height)
        {
            viewPortBounds = new Rectangle(0, 0, width, height);
            graphics.PreferredBackBufferWidth = viewPortBounds.Width;
            graphics.PreferredBackBufferHeight = viewPortBounds.Height;
            graphics.ApplyChanges();
            LoadGame();
        }

        /// <summary>
        /// To mark an ant to be followed
        /// </summary>
        /// <param name="e">The coordinates of the mouse click</param>
        public void FollowAnt(MouseEventArgs e)
        {
            Point p = new Point(e.X, e.Y);

            if (antFollowed != null)
                antFollowed.Follow = false;

            foreach (Ant ant in blackAntList)
            {
                if (ant.AntRectanglePosition.Contains(p))
                {
                    ant.Follow = true;
                    antFollowed = ant;
                    return;
                }
            }

            foreach (Ant ant in redAntList)
            {
                if (ant.AntRectanglePosition.Contains(p))
                {
                    ant.Follow = true;
                    antFollowed = ant;
                    return;
                }
            }
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            spriteBatch.Begin();

            spriteBatch.Draw(backgroundTexture, viewPortBounds, Color.White);

            // Draw the black ant nests
            foreach (Nest nest in blackNestList)
            {
                nest.Draw(spriteBatch);
            }

            // Draw the red ant nests
            foreach (Nest nest in redNestList)
            {
                nest.Draw(spriteBatch);
            }

            // Draw the food
            foreach (Food food in foodList)
            {
                food.Draw(spriteBatch);
            }

            // Draw the black ants
            foreach (BlackAnt ant in blackAntList)
            {
                ant.Draw(spriteBatch);
            }

            // Draw the red ants
            foreach (RedAnt ant in redAntList)
            {
                ant.Draw(spriteBatch);
            }

            spriteBatch.End();

            base.Draw(gameTime);
        }

        public int FoodQuantity
        {
            set { foodQuantity = value; }
        }
    }
}
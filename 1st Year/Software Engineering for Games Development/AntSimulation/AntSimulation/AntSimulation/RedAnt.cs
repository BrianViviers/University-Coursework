// 10253311 - Brian Viviers

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;
using System.Threading;
using System.Diagnostics;
using System.Timers;

namespace AntSimulation
{
    class RedAnt : Ant
    {
        public Vector2 FoodKnownPosition { set; get; }      // Known food location
        private Vector2 tempFoodPos;                        // A closer food location not currently known
        private int distanceToAnt;                          // Distance to steal from ant

        public RedAnt(Vector2 pos, Rectangle b)
            : base(pos, b)
        {
            tempFoodPos = Vector2.Zero;
            FoodKnownPosition = Vector2.Zero;
            distanceToAnt = 5;
        }

        /// <summary>
        /// If the ant is in the asking radius of another ant then they
        /// will either just accept a food location if they don't know
        /// one themselves or ask for a location that is closer than 
        /// the food location that they know of.
        /// </summary>
        /// <param name="otherAnt">The ant to ask for the food location from</param>
        public void AskFoodLocation(RedAnt otherAnt)
        {
            float distance = Vector2.Distance(antPosition, otherAnt.AntPosition);

            if (distance <= antRadius && otherAnt.HaveFood == true)
            {
                if (FoodKnownPosition == Vector2.Zero)
                {
                    ArriveAndAsk(otherAnt);

                    if (distance <= askRadius)
                        FoodKnownPosition = otherAnt.FoodKnownPosition;
                }
                else if (NestKnown != null)
                    CheckIfFoodCloser(otherAnt.FoodKnownPosition);
            }
        }

        /// <summary>
        /// Check if the newly found found is closer to the nest
        /// than the current known food location.
        /// </summary>
        /// <param name="newFoodPos">The position of the new food source</param>
        private void CheckIfFoodCloser(Vector2 newFoodPos)
        {
            float distance1 = Vector2.Distance(NestKnown.NestPosition, newFoodPos);
            float distance2 = Vector2.Distance(NestKnown.NestPosition, FoodKnownPosition);

            if (distance1 < distance2)
                tempFoodPos = newFoodPos;
        }

        /// <summary>
        /// Check if the newly found nest is closer to the food
        /// than the current known nest location
        /// </summary>
        /// <param name="nest">The nest to see if it is closer</param>
        /// <param name="timer">The timer to start</param>
        protected override void CheckIfNestCloser(Nest nest, ref Stopwatch timer)
        {
            if (FoodKnownPosition != Vector2.Zero)
            {
                float distance1 = Vector2.Distance(FoodKnownPosition, NestKnown.NestPosition);
                float distance2 = Vector2.Distance(FoodKnownPosition, nest.NestPosition);

                if (distance1 > distance2)
                {
                    InitForgetTimer(ref timer);
                    NestKnown = nest;
                }
            }
        }

        /// <summary>
        /// This method determines the distance to the known food location
        /// and then either moves the ant closer to the food or if
        /// they are close enough then makes them forget the known food
        /// location so they can spot a new ant with food.
        /// </summary>
        public void CollectKnownFood()
        {
            float distance = Vector2.Distance(antPosition, FoodKnownPosition);

            if (distance > distanceToAnt)
            {
                if (tempFoodPos != Vector2.Zero)
                {
                    FoodKnownPosition = tempFoodPos;
                    tempFoodPos = Vector2.Zero;
                }
                ReturnToFood();
            }
            else if (distance <= distanceToAnt)
            {
                FoodKnownPosition = Vector2.Zero;
            }
        }

        /// <summary>
        /// Calculate the steering towards the food from the
        /// ants current position.
        /// </summary>
        /// <param name="foodPos"></param>
        private void MoveToFood(Vector2 foodPos)
        {
            Vector2 steering = foodPos - antPosition;
            CalculateNewPosition(steering);
        }

        /// <summary>
        /// This method is responsible for steering the red ant
        /// back to the known food location.
        /// </summary>
        private void ReturnToFood()
        {
            float turnFactor = 2f;

            // get direction in which to steer
            Vector2 steering = FoodKnownPosition - antPosition;

            // normalise to unit length
            if (steering != Vector2.Zero)
                steering.Normalize();

            // turn to face direction or travel
            orientation = TurnToFace(steering, orientation, turnFactor * maxRotation);

            // determine the heading based on orientation
            heading = OrientationAsVector(orientation);

            // update position by heading and speed
            antPosition += heading * MaxAntSpeed;
        }

        /// <summary>
        /// This method is called when no known food location is known and 
        /// the ant currently does not have food with them. Red ants use
        /// the black ants as a food location.
        /// </summary>
        /// <param name="blackAntList">The entire black ant list</param>
        /// <param name="wanderOnce">Bool to limit wander() being called repeatedly</param>
        public void SearchForFood(List<BlackAnt> blackAntList, ref bool wanderOnce)
        {
            foreach (BlackAnt ant in blackAntList)
            {
                if ( ant.HaveFood == true)
                {
                    float distance = Vector2.Distance(antPosition, ant.AntPosition);

                    if (distance <= antRadius && distance > distanceToAnt && HaveFood == false)
                        MoveToFood(ant.AntPosition);
                    else if (distance <= distanceToAnt && HaveFood == false)
                        StealFood(ant);
                }
                else
                    WanderLimiter(ref wanderOnce);
            }
        }

        /// <summary>
        /// This method is used when a food location IS known.
        /// The ant still needs to search for food even if a food location is known
        /// If they find food closer then it is checked to see if it is closer
        /// by using the CheckIfFoodCloser(ant.AntPosition) method
        /// </summary>
        /// <param name="blackAntList">The entire black ant list</param>
        public void SearchForMoreFood(List<BlackAnt> blackAntList)
        {
            foreach (BlackAnt ant in blackAntList)
            {
                if (ant.HaveFood == true)
                {
                    float distance = Vector2.Distance(antPosition, ant.AntPosition);

                    if (distance <= antRadius && NestKnown != null)
                        CheckIfFoodCloser(ant.AntPosition);
                }
            }
        }

        /// <summary>
        /// When the ant is close enough to a black
        /// ant to steal food.
        /// </summary>
        /// <param name="ant"></param>
        private void StealFood(BlackAnt ant)
        {
            HaveFood = true;
            ant.HaveFood = false;
            FoodKnownPosition = ant.AntPosition;
            if (tempFoodPos != Vector2.Zero)
            {
                FoodKnownPosition = tempFoodPos;
                tempFoodPos = Vector2.Zero;
            }
        }
    }
}
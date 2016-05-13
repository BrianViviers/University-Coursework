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
    class BlackAnt : Ant
    {
        public Food FoodKnown { set; get; }         // The currently known food object
        private Food closerFood;                    // Food that is closer but is not the known food yet
        private int foodCollectTimer;               // A timer to keep ants sitting at food
        private int distanceToStop;                 // The distance to stop from food centre
        private float angle;                        // An angle around the food to sit.

        // This vector adds a short distance to a food 
        // location so that the ant will sit on the circumference
        // of a food pile.
        private Vector2 nearFoodLocation;

        public BlackAnt(Vector2 pos, Rectangle b)
            : base(pos, b)
        {
            foodCollectTimer = 0;
            angle = (float)(random.NextDouble() * Math.PI * 2);
        }

        /// <summary>
        /// If the ant is in the radius of another ant then they
        /// will either just accept a food location if they don't know
        /// one themselves or ask for a location that is closer than 
        /// the food location that they know of. Ants need to get close
        /// enough to another ant to accept information
        /// </summary>
        /// <param name="otherAnt">The ant to ask for food location off</param>
        /// <param name="foodList">The entire black ant food list</param>
        public void AskFoodLocation(BlackAnt otherAnt, List<Food> foodList)
        {
            float distance = Vector2.Distance(antPosition, otherAnt.AntPosition);

            if (distance <= antRadius && otherAnt.FoodKnown != null)
            {
                if (FoodKnown == null)
                {
                    bool arriveOnce = false;

                    foreach (Food food in foodList)
                    {
                        float newDistance = Vector2.Distance(antPosition, food.FoodPosition);

                        // Check the food is not already in the radius
                        if (newDistance > antRadius)
                        {
                            if (otherAnt.FoodKnown == food)
                            {
                                if (arriveOnce == false)
                                {
                                    ArriveAndAsk(otherAnt);
                                    arriveOnce = true;
                                }
                                if (distance <= askRadius)
                                    FoodKnown = otherAnt.FoodKnown;
                            }
                        }
                    }
                }
                else if (NestKnown != null)
                {
                    foreach (Food food in foodList)
                    {
                        float newDistance = Vector2.Distance(antPosition, food.FoodPosition);

                        if (newDistance > antRadius)
                        {
                            if (otherAnt.FoodKnown == food)
                                CheckIfFoodCloser(otherAnt.FoodKnown);
                        }
                    }
                }
            }
        }

        /// <summary>
        ///  Calculates a spot on the circumference of the
        ///  food to sit and collect food
        /// </summary>
        private void CalculatePlaceToEat(Food food)
        {
            float foodScale = food.Scale;
            float dist = (25 * foodScale);

            // Clamp the distance to a minimum of 7.5 from food centre
            if (dist < 7.5)
                dist = 7.5f;

            nearFoodLocation.X = (float)(Math.Cos(angle) * dist);
            nearFoodLocation.Y = (float)(Math.Sin(angle) * dist);
        }

        /// <summary>
        /// Used to see if the food source still has food available.
        /// It will only operate when the ant is close enough to
        /// the food to see it.
        /// </summary>
        private void CheckFoodQuantity()
        {
            if (FoodKnown != null && Vector2.Distance(antPosition, FoodKnown.FoodPosition)
                < antRadius && FoodKnown.FoodQuantity <= 0)
                FoodKnown = null;
        }

        /// <summary>
        /// This method checks to see if a user has not deleted a food
        /// source from the floor.
        /// </summary>
        /// <param name="foodList">The entire food list</param>
        private void CheckFoodStillExists(List<Food> foodList)
        {
            for (int i = 0; i < foodList.Count; i++)
            {
                if (FoodKnown == foodList[i])
                    return;
            }
            FoodKnown = null;
        }

        /// <summary>
        /// Check if the newly found found is closer to the nest
        /// than the current known food location.
        /// </summary>
        /// <param name="food">The food source to see if it is closer</param>
        private void CheckIfFoodCloser(Food food)
        {
            if (FoodKnown != null)
            {
                float distance1 = Vector2.Distance(NestKnown.NestPosition, food.FoodPosition);
                float distance2 = Vector2.Distance(NestKnown.NestPosition, FoodKnown.FoodPosition);

                if (distance1 < distance2)
                    closerFood = food;
            }
        }

        /// <summary>
        /// Check if the newly found nest is closer to the food
        /// than the current known nest location
        /// </summary>
        /// <param name="nest">The nest to see if it is closer</param>
        /// <param name="timer">The timer to start</param>
        protected override void CheckIfNestCloser(Nest nest, ref Stopwatch timer)
        {
            if (FoodKnown != null)
            {
                float distance1 = Vector2.Distance(FoodKnown.FoodPosition, NestKnown.NestPosition);
                float distance2 = Vector2.Distance(FoodKnown.FoodPosition, nest.NestPosition);

                if (distance1 > distance2)
                {
                    InitForgetTimer(ref timer);
                    NestKnown = nest;
                }
            }
        }

        /// <summary>
        /// This method is called when an ant knows the food location and
        /// does not have food with them. 
        /// </summary>
        /// <param name="foodList">The entire list of all foods available</param>
        public void CollectKnownFood(List<Food> foodList)
        {
            CalculatePlaceToEat(FoodKnown);

            float distance = Vector2.Distance(antPosition, FoodKnown.FoodPosition + nearFoodLocation);

            if (distance > DistanceToStop)
            {
                // Set the closer food to the known food
                if (closerFood != null)
                {
                    FoodKnown = closerFood;
                    closerFood = null;
                }
                MoveToFood(FoodKnown);
            }
            else if (distance <= DistanceToStop && FoodKnown.FoodQuantity > 0)
            {
                CheckFoodStillExists(foodList);
                PickUpFood(FoodKnown);
            }
            CheckFoodQuantity();
        }

        /// <summary>
        /// When the ant reaches its spot to collect food
        /// will need to turn and face the food.
        /// </summary>
        /// <param name="food">The food to face</param>
        private void FaceFood(Food food)
        {
            float turnFactor = 0.75f;

            // get direction in which to steer
            Vector2 steering = food.FoodPosition - antPosition;

            // normalise to unit length
            if (steering != Vector2.Zero)
                steering.Normalize();

            // turn to face food
            orientation = TurnToFace(steering, orientation, turnFactor * maxRotation);
        }

        /// <summary>
        /// Calculate the steering towards the food from the
        /// ants current position.
        /// </summary>
        /// <param name="food">The food source to collect food from</param>
        private void MoveToFood(Food food)
        {
            CalculatePlaceToEat(food);
            Vector2 steering = food.FoodPosition + nearFoodLocation - antPosition;
            CalculateNewPosition(steering);
        }

        /// <summary>
        /// When the ant is close enough to the food
        /// to eat then a timer is begun to simulate the 
        /// actually collecting food.
        /// </summary>
        /// <param name="food">The food source to collect from</param>
        private void PickUpFood(Food food)
        {
            int waitTime = 60;

            if (food != null)
            {
                FaceFood(food);
                foodCollectTimer++;
                if (foodCollectTimer >= waitTime)
                {
                    HaveFood = true;
                    FoodKnown = food;
                    FoodKnown.DecreaseFoodQuantity();
                    foodCollectTimer = 0;

                    // Change the position of where the ant will sit next time.
                    angle = (float)(random.NextDouble() * Math.PI * 2);

                    // Set the closer food to the known food
                    if (closerFood != null)
                    {
                        FoodKnown = closerFood;
                        closerFood = null;
                    }
                }
            }
        }

        /// <summary>
        /// This method is called when no known food location is known and 
        /// the ant currently does not have food with them.
        /// </summary>
        /// <param name="foodList">The entire black ant food list</param>
        /// <param name="wanderOnce">Bool to limit wander() being called repeatedly</param>
        public void SearchForFood(List<Food> foodList, ref bool wanderOnce)
        {
            if (foodList.Count == 0)
                WanderLimiter(ref wanderOnce);
            else
            {
                foreach (Food food in foodList)
                {
                    CalculatePlaceToEat(food);

                    float distance = Vector2.Distance(antPosition, food.FoodPosition + nearFoodLocation);

                    if (distance <= antRadius)
                    {
                        if (distance > DistanceToStop && HaveFood == false)
                            MoveToFood(food);
                        else if (distance <= DistanceToStop && HaveFood == false)
                            PickUpFood(food);

                        return;
                    }
                }
                WanderLimiter(ref wanderOnce);
            }
        }

        /// <summary>
        /// This method is used when a food location IS known.
        /// The ant still needs to search for food even if a food location is known
        /// If they find food closer then it is checked to see if it is closer
        /// by using the CheckIfFoodCloser(food) method
        /// </summary>
        /// <param name="foodList">The entire list of all foods available</param>
        public void SearchForMoreFood(List<Food> foodList)
        {
            if (foodList.Count != 0)
            {
                foreach (Food food in foodList)
                {
                    CalculatePlaceToEat(food);

                    float distance = Vector2.Distance(antPosition, food.FoodPosition + nearFoodLocation);

                    if (distance <= antRadius && NestKnown != null)
                        CheckIfFoodCloser(food);
                }
            }
        }

        /// <summary>
        /// Distance to stop is increased as the ants speed increases
        /// because they will not come as close to the food when the 
        /// speed is higher.
        /// </summary>
        private float DistanceToStop
        {
            get
            {
                if (MaxAntSpeed < 2)
                    distanceToStop = 2;
                else
                    distanceToStop = (int)MaxAntSpeed;

                return distanceToStop;
            }
        }
    }
}
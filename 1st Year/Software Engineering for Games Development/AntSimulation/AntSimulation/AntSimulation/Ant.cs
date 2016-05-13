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
    abstract class Ant
    {
        protected Vector2 antPosition;                            // The position of the ant
        protected Vector2 wanderPosition;                         // Random position for the ant to head towards
        protected Vector2 heading;                                // vector representation of orientation

        protected float orientation;                              // the angle which the ant is facing in radians
        public float MaxAntSpeed { set; get; }                    // the maximum speed the ant can travel
        protected float maxRotation;                              // maximum number of radians ants can turn
        protected int antRadius;                                  // A radius distance
        protected float slowRadius;                               // radius for slowing down when arriving
        protected int askRadius;                                  // The distance at which ants can ask for information
        protected int timeToForget;                               // How long ants will remember a nest location

        public bool Follow { set; get; }                          // True if the ant is marked to be followed
        public bool HaveFood { set; get; }                        // If the ant has food or not
        public Nest NestKnown { set; get; }                       // The known nest location

        protected Rectangle viewportbounds;                       // To keep track of the objects bounds i.e. ViewPort dimensions
        protected Random random;                                  // random number used for wandering

        public Texture2D AntImage { set; protected get; }         // image of ant without food
        public Texture2D AntImageWithFood { set; protected get; } // image of ant with food
        protected Rectangle antRectanglePosition;                 // Position and size of object

        protected static Stopwatch nestLastSeenTimer;             // A timer of when the nest location was last seen
        protected static Stopwatch nestLastToldTimer;             // A timer of when the nest location was last told to the ant

        public Ant(Vector2 pos, Rectangle b)
        {
            antPosition = new Vector2(pos.X, pos.Y);
            viewportbounds = new Rectangle(b.X, b.Y, b.Width, b.Height);
            orientation = 0.0f;

            heading = new Vector2(0, 0);
            HaveFood = false;

            // set some intial speeds, distances and angular rotations
            MaxAntSpeed = 4f;
            maxRotation = 1.0f;
            askRadius = 10;
            slowRadius = 20;
            timeToForget = 20;
            antRadius = 20;

            // Ensure all ants moving in different directions initially.
            Thread.Sleep(8);

            random = new Random();
        }

        /// <summary>
        /// An ant can only ask information once it is close enough
        /// so the first have to arrive at the other ant and then ask
        /// the required information. 
        /// Black ant and Red ant use this so it has Ant as the parameter.
        /// </summary>
        /// <param name="arrivee">The ant to ask information from.</param>
        protected void ArriveAndAsk(Ant arrivee)
        {
            float turnFactor = 0.75f;

            float distanceToArrivee;
            float arriveSpeed;

            Vector2 steering = arrivee.AntPosition - AntPosition;

            orientation = TurnToFace(steering, orientation, turnFactor * maxRotation);

            heading = OrientationAsVector(orientation);

            // find the distance to the arrivee
            distanceToArrivee = steering.Length();

            // determine the speed to move
            arriveSpeed = (MaxAntSpeed * distanceToArrivee / slowRadius) / askRadius;

            // clamp arriveSpeed to max speed
            if (arriveSpeed > MaxAntSpeed)
                arriveSpeed = MaxAntSpeed;

            // finally update the antposition
            antPosition += heading * arriveSpeed;
        }

        /// <summary>
        /// If the ant is in the radius of another ant then they
        /// will, depending if they know where a nest is or not, either 
        /// just ask for a nest location or ask for a nest location
        /// that is closer than their currently known nest location.
        /// </summary>
        /// <param name="otherAnt">The ant to ask for nest location off</param>
        /// <param name="nestList">The entire black ant nest list</param>
        public void AskNestLocation(Ant otherAnt, List<Nest> nestList)
        {
            float distance = Vector2.Distance(antPosition, otherAnt.AntPosition);

            // if the ant is within the radius and the other ant knows of a nest and has seen it less than 20 seconds ago
            if (distance <= antRadius && otherAnt.NestKnown != null && otherAnt.NestLastSeenTimer < timeToForget)
            {
                // Check the nest has not been removed by the user.
                if (CheckNestStillExists(nestList, otherAnt.NestKnown))
                {
                    if (NestKnown == null)
                    {
                        // Move closer to other ant
                        ArriveAndAsk(otherAnt);

                        if (distance <= askRadius)
                        {
                            // Take nest information from other ant
                            NestKnown = otherAnt.NestKnown;
                            
                            // Start the timer for when it was last told about a nest
                            InitForgetTimer(ref nestLastToldTimer);
                        }
                    }
                    else
                        CheckIfNestCloser(otherAnt.NestKnown, ref nestLastToldTimer);
                }
            }
        }

        /// <summary>
        /// Update the position and orientation of the ant when it hits the boundary
        /// </summary>
        protected void Boundary()
        {
            if (antPosition.X < 0)
            {
                antPosition.X = 0;
                orientation = 0f;
            }
            else if (antPosition.X + AntRectanglePosition.Width > viewportbounds.Width)
            {
                antPosition.X = viewportbounds.Width - AntRectanglePosition.Width;
                orientation = (float)Math.PI;
            }

            if (antPosition.Y < 0)
            {
                antPosition.Y = 0;
                orientation = (float)Math.PI / 2;
            }
            else if (antPosition.Y + AntRectanglePosition.Height > viewportbounds.Height)
            {
                antPosition.Y = viewportbounds.Height - AntRectanglePosition.Height;
                orientation = -(float)Math.PI / 2;
            }
        }

        /// <summary>
        /// This method is called from the collect and deposit food
        /// method but with a different steering value.
        /// </summary>
        /// <param name="steering"></param>
        protected void CalculateNewPosition(Vector2 steering)
        {
            float turnFactor = 2.0f;

            // normalise to unit length
            if (steering != Vector2.Zero)
                steering.Normalize();

            // turn to face direction or travel
            orientation = TurnToFace(steering, orientation, turnFactor * maxRotation);

            // determine the heading based on orientation
            heading = OrientationAsVector(orientation);

            // update position by heading and speed
            antPosition += heading * MaxAntSpeed;

            // keep ant in viewport by wrapping as a Torus
            Boundary();
        }

        // Subclasses contain information for this method.
        protected abstract void CheckIfNestCloser(Nest nest, ref Stopwatch timer);

        /// <summary>
        /// Loops through the nest list and checks if the nest passed
        /// in has not been removed by the user.
        /// </summary>
        /// <param name="nestList">The entire nest list(either black or red ant)</param>
        /// <param name="nestToCheck">The nest to see if it exists</param>
        /// <returns>True if nest exists else false</returns>
        protected bool CheckNestStillExists(List<Nest> nestList, Nest nestToCheck)
        {
            foreach (Nest nest in nestList)
            {
                if (nest == nestToCheck)
                    return true;
            }
            return false;
        }

        /// <summary>
        /// Calculate the steering from the ants 
        /// position to the nest position and then passes
        /// it to a helper function.
        /// </summary>
        /// <param name="nest"></param>
        protected void DepositFood(Nest nest)
        {
            Vector2 steering = nest.NestPosition - antPosition;
            CalculateNewPosition(steering);
        }

        /// <summary>
        /// Draws this object (and its related images) to the given graphics 
        /// context (SpriteBatch).
        /// </summary>
        /// <param name="spriteBatch"></param>
        public void Draw(SpriteBatch spriteBatch)
        {
            Vector2 drawingOrigin = new Vector2(AntImage.Width / 2.0f, AntImage.Height / 2.0f);

            if (HaveFood == false)
                spriteBatch.Draw(AntImage, antPosition, null, Color.White, orientation, drawingOrigin, 0.1f, SpriteEffects.None, 0.0f);
            else
                spriteBatch.Draw(AntImageWithFood, antPosition, null, Color.White, orientation, drawingOrigin, 0.1f, SpriteEffects.None, 0.0f);

            // Draw a circle around the ant so it can be followed
            if (Follow == true)
                Primitives2D.DrawCircle(spriteBatch, antPosition, 8f, 100, Color.LawnGreen);
        }

        /// <summary>
        /// Called on every draw this method checks to see if the timers
        /// have been going for longer than 20 seconds which then causes
        /// ants to forget their known nest location
        /// </summary>
        protected void ForgetNestLocation()
        {
            if (nestLastSeenTimer != null && nestLastToldTimer != null)
            {
                // If both timers have been started and are greater than 20 seconds
                if (NestLastSeenTimer > timeToForget && nestLastToldTimer.Elapsed.Seconds > timeToForget)
                    NestKnown = null;
            } 
            else if (nestLastSeenTimer != null)
            {
                // if only this timer has been started and is greater than 20 seconds
                if (NestLastSeenTimer > timeToForget)
                    NestKnown = null;
            } 
            else if (nestLastToldTimer != null)
            {
                // if only this timer has been started and is greater than 20 seconds
                if (nestLastToldTimer.Elapsed.Seconds > timeToForget)
                    NestKnown = null;
            }
        }

        /// <summary>
        /// Start the timer which times when last an ant has seen or 
        /// been told the location of a nest.
        /// </summary>
        /// <param name="timer"></param>
        protected void InitForgetTimer(ref Stopwatch timer)
        {
            timer = new Stopwatch();
            timer.Start();
        }

        /// <summary>
        /// Returns the Vector representation of the orientation
        /// passed as parameter. Orientation is measured in radians
        /// </summary>
        /// <param name="orien"></param>
        /// <returns></returns>
        protected Vector2 OrientationAsVector(float orien)
        {
            Vector2 orienAsVect;

            orienAsVect.X = (float)Math.Cos(orien);
            orienAsVect.Y = (float)Math.Sin(orien);

            return orienAsVect;
        }

        /// <summary>
        /// Return food to the nest
        /// </summary>
        public void ReturnFood(List<Nest> nestList)
        {
            DepositFood(NestKnown);

            float distance = Vector2.Distance(antPosition, NestKnown.NestPosition);

            if (distance <= antRadius)
            {
                // Check the user has not removed the nest
                if (!(CheckNestStillExists(nestList, NestKnown)))
                    NestKnown = null;

                if (NestKnown != null && Vector2.Distance(antPosition, NestKnown.NestPosition) <= 3)
                    HaveFood = false;
            }
        }

        /// <summary>
        /// The ant will continuously search for a nest even if a nest location
        /// is known as a closer nest might exist. A call to the ForgetNestLocation
        /// method is also done to check if the ant should forget the nest location.
        /// </summary>
        /// <param name="nestList">The entire red ant nest list</param>
        /// <param name="wanderOnce">Bool to limit wander() being called repeatedly</param>
        public void SearchForNest(List<Nest> nestList, ref bool wanderOnce)
        {
            ForgetNestLocation();

            foreach (Nest nest in nestList)
            {
                float distance = Vector2.Distance(antPosition, nest.NestPosition);

                if (distance <= antRadius)
                {
                    if (NestKnown != null)
                        CheckIfNestCloser(nest, ref nestLastSeenTimer);
                    else
                    {
                        InitForgetTimer(ref nestLastSeenTimer);
                        NestKnown = nest;
                    }
                }
            }
        }

        /// <summary>
        /// The ant image will be turned towards their direction of heading
        /// </summary>
        /// <param name="steering">The vector representation of direction</param>
        /// <param name="currentOrientation">The current direction the ant faces</param>
        /// <param name="turnSpeed">The max turn speed the ant can perform</param>
        /// <returns></returns>
        protected float TurnToFace(Vector2 steering, float currentOrientation, float turnSpeed)
        {
            float newOrientation;
            float desiredOrientation;
            float orientationDifference;

            float x = steering.X;
            float y = steering.Y;

            // the desiredOrientation is given by the steering vector
            desiredOrientation = (float)Math.Atan2(y, x);

            // find the difference between the orientation we need to be
            // and our current Orientation
            orientationDifference = desiredOrientation - currentOrientation;

            // now using WrapAngle to get result from -Pi to Pi 
            // ( -180 degrees to 180 degrees )
            orientationDifference = WrapAngle(orientationDifference);

            // clamp that between -turnSpeed and turnSpeed.
            orientationDifference = MathHelper.Clamp(orientationDifference, -turnSpeed, turnSpeed);

            // the closest we can get to our target is currentAngle + orientationDifference.
            // return that, using WrapAngle again.
            newOrientation = WrapAngle(currentOrientation + orientationDifference);

            return newOrientation;
        }

        /// <summary>
        /// The wander effect is accomplished by having the ant aim in a random
        /// direction. Every frame, this random direction is slightly modified.
        /// </summary>
        protected void Wander()
        {
            // the max +/- the agent will wander from its current position
            float wanderLimits = 0.5f;

            // this defines what proportion of its maxRotation speed the agent will turn
            float turnFactor = 0.15f;

            // randomly define a new position
            wanderPosition.X += MathHelper.Lerp(-wanderLimits, wanderLimits, (float)random.NextDouble());
            wanderPosition.Y += MathHelper.Lerp(-wanderLimits, wanderLimits, (float)random.NextDouble());

            // normalize the wander position, ...
            if (wanderPosition != Vector2.Zero)
                wanderPosition.Normalize();

            // now find the new orientation based on the wanderPosition
            orientation = TurnToFace(wanderPosition, orientation, turnFactor * maxRotation);

            // determine the heading vector based on orientation
            heading = OrientationAsVector(orientation);

            // finally update the agents position based upon the new heading and its speed
            // assume a wandering agent only moves at 0.5 of maxSpeed
            antPosition += heading * MaxAntSpeed;

            Boundary();
        }

        /// <summary>
        /// This ensures wander() is only called once for 
        /// each update of the position. If this is not done
        /// then the ants speed is increased.
        /// </summary>
        /// <param name="wanderOnce">An int that is set to 1 once</param>
        public void WanderLimiter(ref bool wanderOnce)
        {
            if (wanderOnce == false)
            {
                Wander();
                wanderOnce = true;
            }
        }

        /// <summary>
        /// Returns the angle expressed in radians between -Pi and Pi.
        /// <param name="theta">the angle to wrap, in radians.</param>
        /// <returns>the input value expressed in radians from -Pi to Pi.</returns>
        /// </summary>
        protected float WrapAngle(float theta)
        {
            while (theta < -MathHelper.Pi)
            {
                theta += MathHelper.TwoPi;
            }
            while (theta > MathHelper.Pi)
            {
                theta -= MathHelper.TwoPi;
            }
            return theta;
        }

        public Rectangle AntRectanglePosition
        {
            get
            {
                antRectanglePosition.Width = AntImage.Width / 10;
                antRectanglePosition.Height = AntImage.Height / 10;

                antRectanglePosition.X = (int)(antPosition.X - antRectanglePosition.Width / 2);
                antRectanglePosition.Y = (int)(antPosition.Y - antRectanglePosition.Height / 2);

                return antRectanglePosition;
            }
        }

        public Vector2 AntPosition
        {
            get { return antPosition; }
        }

        public int NestLastSeenTimer
        {
            get { return nestLastSeenTimer.Elapsed.Seconds; }
        }
    }
}
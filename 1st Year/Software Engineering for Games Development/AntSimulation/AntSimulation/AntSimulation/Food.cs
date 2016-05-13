// 10253311 - Brian Viviers

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System.Threading;

namespace AntSimulation
{
    class Food
    {
        private Vector2 foodPosition;              // position used in updating
        private Texture2D foodImage;               // image used to display food
        private Rectangle foodRectanglePosition;   // Position and size of food
        private int foodQuantity;                  // The amount of food in the object
        private int startFoodQuantity;             // The starting amount of food
        

        public Food(Vector2 pos, Texture2D image, int quantity)
        {
            foodPosition = new Vector2(pos.X, pos.Y);
            foodRectanglePosition = new Rectangle((int)pos.X - image.Width / 2, 
                (int)pos.Y - image.Height / 2, image.Width, image.Height);

            foodImage = image;
            foodQuantity = quantity;
            startFoodQuantity = quantity;
        }

        /// Draws this food (and its related image) to the given graphics 
        /// context (SpriteBatch).
        public void Draw(SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(foodImage, new Vector2(foodRectanglePosition.X + 20, foodRectanglePosition.Y + 20),
                null, Color.White, 0, new Vector2(foodImage.Width / 2.0f, foodImage.Height / 2.0f), 
                Scale, SpriteEffects.None, 0.0f);
        }

        
        public void DecreaseFoodQuantity()
        {
            foodQuantity--;
        }

        public float Scale
        {
            get 
            {
                float scale = (float)foodQuantity / startFoodQuantity;
                if (scale > 1f)
                    scale = 1f;
                return scale; 
            }
        }

        public int FoodQuantity
        {
            set { foodQuantity = value; }
            get { return foodQuantity; }
        }

        public Rectangle FoodRectanglePosition
        {
            get { return foodRectanglePosition; }
        }

        public Vector2 FoodPosition
        {
            get { return foodPosition; }
        }
    }
}
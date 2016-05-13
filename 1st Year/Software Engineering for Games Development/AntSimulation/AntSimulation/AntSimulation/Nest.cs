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
    class Nest
    {
        private Vector2 nestPosition;                  // position used in updating
        private Texture2D nestImage;                   // image used to display nest
        private Rectangle nestRectanglePosition;       // Position and size of nest

        public Nest(Vector2 pos, Texture2D image)
        {
            nestPosition = new Vector2(pos.X, pos.Y);
            nestImage = image;
            nestRectanglePosition = new Rectangle((int)pos.X - (int)image.Width / 2, 
                (int)pos.Y - (int)image.Height / 2, (int)image.Width, (int)image.Height);
        }

        /// Draws this nest (and its related image) to the given graphics 
        /// context (SpriteBatch).
        public void Draw(SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(nestImage, nestRectanglePosition, Color.Tan);
        }

        public Vector2 NestPosition
        {
            get { return nestPosition; }
        }

        public Rectangle NestRectanglePosition
        {
            get { return nestRectanglePosition; }
        }
    }
}
// 10253311 - Brian Viviers

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace AntSimulation
{
    public partial class AntSimulationForm : Form
    {
        // Timers to perform an action while a button is clicked and held down.
        private Timer plusButtonTimer, minusButtonTimer;

        public AntSimulationForm()
        {
            InitializeComponent();

            plusButtonTimer = new Timer();
            plusButtonTimer.Tick += new EventHandler(PlusButtonTimer);
            plusButtonTimer.Interval = 200;

            minusButtonTimer = new Timer();
            minusButtonTimer.Tick += new EventHandler(MinusButtonTimer);
            minusButtonTimer.Interval = 200;

            Size size = new Size(1024, 768);
            PanelSize = size;
        }

        /// <summary>
        /// Used to provide the AntSimulation with a place to draw
        /// the simulation onto on the form.
        /// </summary>
        /// <returns>The handle to the panel on where to draw the game</returns>
        public IntPtr getDrawSurface()
        {
            return simulationPanel.Handle;
        }

        /// <summary>
        /// If the close button is clicked then the game should close too.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Settings_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        /// <summary>
        /// Depending on which radio button is selected when the user
        /// clicks somewhere then the corresponding action will be 
        /// performed on the spot where the user clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void simulationPanel_MouseDown(object sender, MouseEventArgs e)
        {
            if (foodRadioButton.Checked)
                Program.antSimulator.AddFood(e);
            else if (blackNestRadioButton.Checked)
                Program.antSimulator.AddNest(e, "black");
            else if (redAntNestRadioButton.Checked)
                Program.antSimulator.AddNest(e, "red");
            else if (removeObjectRadioButton.Checked)
                Program.antSimulator.RemoveObject(e);
            else if (checkRadioButton.Checked)
                Program.antSimulator.FollowAnt(e);
        }

        /// <summary>
        /// Clicking this button will set the next placed food
        /// amount to the quantity the user has entered.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void applyFoodButton_Click(object sender, EventArgs e)
        {
            try
            {
                int quantity = Int32.Parse(foodTextBox.Text);
                if (quantity < 1)
                {
                    quantity = 1;
                    foodTextBox.Text = "1";
                }
                Program.antSimulator.FoodQuantity = quantity;
                foodTextBox.BackColor = Color.LimeGreen;
            }
            catch (FormatException)
            {
                MessageBox.Show("Enter an integer for the food quantity.", "Number Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /// <summary>
        /// Sets the backcolor property of the text box which has
        /// had its text changed to a tomato colour.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void TextBox_Changed(object sender, EventArgs e)
        {
            if (sender.Equals(foodTextBox))
                foodTextBox.BackColor = Color.Tomato;
            else if (sender.Equals(blackAntTextBox))
                blackAntTextBox.BackColor = Color.Tomato;
            else
                redAntTextBox.BackColor = Color.Tomato;
        }

        /// <summary>
        /// This will restart the simulation with the amount of
        /// black and red ants the user has entered.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void setAntButton_Click(object sender, EventArgs e)
        {
            try
            {
                int black = Int32.Parse(blackAntTextBox.Text);
                int red = Int32.Parse(redAntTextBox.Text);

                if (black < 0)
                {
                    black = 0;
                    blackAntTextBox.Text = "0";
                }

                if (red < 0)
                {
                    red = 0;
                    redAntTextBox.Text = "0";
                }

                Program.antSimulator.LoadGame(black, red);
                foodTextBox.Text = "200";
                speedLabel.Text = "4.0";
                blackAntTextBox.BackColor = Color.LimeGreen;
                redAntTextBox.BackColor = Color.LimeGreen;
                foodTextBox.BackColor = Color.Silver;
            }
            catch (FormatException)
            {
                MessageBox.Show("Enter an integer for the ants count.", "Number Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /// <summary>
        /// This will either pause or play the game depending on 
        /// the current state of the simulation.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PlayPause(object sender, EventArgs e)
        {
            Program.antSimulator.PausePlay();
        }

        /// <summary>
        /// This button will display a message box with some information relating
        /// to the ant simulation
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void infoButton_Click(object sender, EventArgs e)
        {
            MessageBox.Show("- If performance is slow try reducing the amount of ants. \r\n\n" + 
                            "- Place food for black ants to collect. \r\n\n" + 
                            "- Place nests for black and red ants to return food into.\r\n\n" + 
                            "- Red ants steal food from black ants.\r\n\n" +
                            "- All ants should find the shortest path between food and a nest.\r\n\n" +
                            "- Ants communicate food and nest locations with each other.\r\n\n" +
                            "- Ants forget nests after a short time.\r\n\n" +
                            "- Placing food on top of an existing food will increase its quantity.\r\n\n" +
                            "- Select 'Follow Ant' and click on an ant to place a circle around it.\r\n" +
                            "  Pausing the simulation makes this easier to accomplish.\r\n\n" +
                            "- The speed is limited to between 0 - 8.",
                            "Simulation Information", MessageBoxButtons.OK);
        }

        /// <summary>
        /// One click will increase the speed by 0.1.
        /// </summary>
        private void speedPlusButton_Click(object sender, EventArgs e)
        {
            UpdateSpeed(0.1f);
        }

        /// <summary>
        /// This timer is started when the user click and holds on the 
        /// plus button. While the timer is running the PlusButtonTimer() 
        /// method is called every 200ms.
        /// </summary>
        private void speedPlusButton_MouseDown(object sender, MouseEventArgs e)
        {
            plusButtonTimer.Start();
        }

        /// <summary>
        /// The timer is stopped when the user releases the button.
        /// </summary>
        private void speedPlusButton_MouseUp(object sender, MouseEventArgs e)
        {
            plusButtonTimer.Stop();
        }

        /// <summary>
        /// One click will decrease the speed by 0.1.
        /// </summary>
        private void speedMinusButton_Click(object sender, EventArgs e)
        {
            UpdateSpeed(-0.1f);
        }

        /// <summary>
        /// This timer is started when the user click and holds on the 
        /// minus button. While the timer is running the 
        /// MinusButtonTimer() method is called every 200ms.
        /// </summary>
        private void speedMinusButton_MouseDown(object sender, MouseEventArgs e)
        {
            minusButtonTimer.Start();
        }

        /// <summary>
        /// The timer is stopped when the user releases the button.
        /// </summary>
        private void speedMinusButton_MouseUp(object sender, MouseEventArgs e)
        {
            minusButtonTimer.Stop();
        }

        /// <summary>
        /// This is called every 200ms while the plus button is held down.
        /// </summary>
        private void PlusButtonTimer(Object myObject, EventArgs myEventArgs)
        {
            UpdateSpeed(0.1f);
        }

        /// <summary>
        /// This is called every 200ms while the minus button is held down.
        /// </summary>
        private void MinusButtonTimer(Object myObject, EventArgs myEventArgs)
        {
            UpdateSpeed(-0.1f);
        }

        /// <summary>
        /// On every call the speed will either be increased or
        /// decreased with the value passed in.
        /// </summary>
        /// <param name="speed">The amount to increase or decrease the speed.</param>
        private void UpdateSpeed(float speed)
        {
            float newSpeed = float.Parse(speedLabel.Text) + speed;
            if (newSpeed >= 0.0 && newSpeed <= 8.0)
            {
                Program.antSimulator.ChangeSpeed(speed);
                speedLabel.Text = newSpeed.ToString("0.0");
            }
        }

        /// <summary>
        /// A method linked to a button click which when click will change 
        /// the resolution to the one selected by the radio button that 
        /// is checked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void resolutionApplyButton_Click(object sender, EventArgs e)
        {
            smallResolutionRadio.BackColor = Color.Tan;
            mediumResolutionRadio.BackColor = Color.Tan;
            maxResolutionRadio.BackColor = Color.Tan;
            if (smallResolutionRadio.Checked)
            {
                Size size = new Size(800, 600);
                PanelSize = size;
                Program.antSimulator.ChangeResolutionSize(size.Width, size.Height);
                Program.antSimulationForm.Size = new System.Drawing.Size(size.Width + 190, size.Height + 40);
                Program.antSimulationForm.WindowState = FormWindowState.Normal;
                smallResolutionRadio.BackColor = Color.LimeGreen;
            }
            else if (mediumResolutionRadio.Checked)
            {
                Size size = new Size(1024, 768);
                PanelSize = size;
                Program.antSimulator.ChangeResolutionSize(size.Width, size.Height);
                Program.antSimulationForm.Size = new System.Drawing.Size(size.Width + 190, size.Height + 40);
                Program.antSimulationForm.WindowState = FormWindowState.Normal;
                mediumResolutionRadio.BackColor = Color.LimeGreen;
            }
            else
            {
                Rectangle max = GetResolution();
                Size size = new Size(max.Width - 190, max.Height - 60);
                PanelSize = size;
                Program.antSimulator.ChangeResolutionSize(size.Width, size.Height);
                Program.antSimulationForm.Size = new Size(max.Width, max.Height);
                Program.antSimulationForm.WindowState = FormWindowState.Maximized;
                maxResolutionRadio.BackColor = Color.LimeGreen;
            }
            ResetSettings();
        }

        /// <summary>
        /// A helper method that resets the text box
        /// values and colours to the default.
        /// </summary>
        private void ResetSettings()
        {
            foodTextBox.Text = "200";
            speedLabel.Text = "4.0";
            blackAntTextBox.Text = "500";
            redAntTextBox.Text = "200";
            blackAntTextBox.BackColor = Color.Silver;
            redAntTextBox.BackColor = Color.Silver;
            foodTextBox.BackColor = Color.Silver;
        }

        /// <summary>
        /// This method obtains the users currently set
        /// resolution size
        /// </summary>
        /// <returns>A rectangle with the resolution size.</returns>
        private Rectangle GetResolution()
        {
            return Screen.FromControl(this).Bounds;
        }

        private Size PanelSize
        {
            set
            {
                simulationPanel.Width = value.Width;
                simulationPanel.Height = value.Height;
                settingsPanel.Location = new Point(value.Width + 10, 10);
                settingsPanel.Height = value.Height;
            }
        }
    }
}

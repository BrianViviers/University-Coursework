namespace AntSimulation
{
    partial class AntSimulationForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AntSimulationForm));
            this.simulationPanel = new System.Windows.Forms.Panel();
            this.actionsGroupBox = new System.Windows.Forms.GroupBox();
            this.checkRadioButton = new System.Windows.Forms.RadioButton();
            this.redAntNestRadioButton = new System.Windows.Forms.RadioButton();
            this.removeObjectRadioButton = new System.Windows.Forms.RadioButton();
            this.blackNestRadioButton = new System.Windows.Forms.RadioButton();
            this.foodRadioButton = new System.Windows.Forms.RadioButton();
            this.blackAntsLabel = new System.Windows.Forms.Label();
            this.blackAntTextBox = new System.Windows.Forms.TextBox();
            this.foodTextBox = new System.Windows.Forms.TextBox();
            this.foodCountLabel = new System.Windows.Forms.Label();
            this.setAntButton = new System.Windows.Forms.Button();
            this.playButton = new System.Windows.Forms.Button();
            this.setupGroupBox = new System.Windows.Forms.GroupBox();
            this.clickHoldLabel = new System.Windows.Forms.Label();
            this.redAntsLabel = new System.Windows.Forms.Label();
            this.speedPlusButton = new System.Windows.Forms.Button();
            this.redAntTextBox = new System.Windows.Forms.TextBox();
            this.speedSettingLabel = new System.Windows.Forms.Label();
            this.speedMinusButton = new System.Windows.Forms.Button();
            this.speedLabel = new System.Windows.Forms.Label();
            this.applyFoodButton = new System.Windows.Forms.Button();
            this.playPauseGroupBox = new System.Windows.Forms.GroupBox();
            this.infoButton = new System.Windows.Forms.Button();
            this.settingsPanel = new System.Windows.Forms.Panel();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.resolutionApplyButton = new System.Windows.Forms.Button();
            this.maxResolutionRadio = new System.Windows.Forms.RadioButton();
            this.mediumResolutionRadio = new System.Windows.Forms.RadioButton();
            this.smallResolutionRadio = new System.Windows.Forms.RadioButton();
            this.actionsGroupBox.SuspendLayout();
            this.setupGroupBox.SuspendLayout();
            this.playPauseGroupBox.SuspendLayout();
            this.settingsPanel.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // simulationPanel
            // 
            this.simulationPanel.Location = new System.Drawing.Point(1, 1);
            this.simulationPanel.Name = "simulationPanel";
            this.simulationPanel.Size = new System.Drawing.Size(0, 0);
            this.simulationPanel.TabIndex = 0;
            this.simulationPanel.MouseDown += new System.Windows.Forms.MouseEventHandler(this.simulationPanel_MouseDown);
            // 
            // actionsGroupBox
            // 
            this.actionsGroupBox.Controls.Add(this.checkRadioButton);
            this.actionsGroupBox.Controls.Add(this.redAntNestRadioButton);
            this.actionsGroupBox.Controls.Add(this.removeObjectRadioButton);
            this.actionsGroupBox.Controls.Add(this.blackNestRadioButton);
            this.actionsGroupBox.Controls.Add(this.foodRadioButton);
            this.actionsGroupBox.Location = new System.Drawing.Point(3, 3);
            this.actionsGroupBox.Name = "actionsGroupBox";
            this.actionsGroupBox.Size = new System.Drawing.Size(154, 142);
            this.actionsGroupBox.TabIndex = 0;
            this.actionsGroupBox.TabStop = false;
            this.actionsGroupBox.Text = "Actions";
            // 
            // checkRadioButton
            // 
            this.checkRadioButton.AutoSize = true;
            this.checkRadioButton.Location = new System.Drawing.Point(11, 112);
            this.checkRadioButton.Name = "checkRadioButton";
            this.checkRadioButton.Size = new System.Drawing.Size(74, 17);
            this.checkRadioButton.TabIndex = 4;
            this.checkRadioButton.TabStop = true;
            this.checkRadioButton.Text = "Follow Ant";
            this.checkRadioButton.UseVisualStyleBackColor = true;
            // 
            // redAntNestRadioButton
            // 
            this.redAntNestRadioButton.AutoSize = true;
            this.redAntNestRadioButton.Location = new System.Drawing.Point(11, 66);
            this.redAntNestRadioButton.Name = "redAntNestRadioButton";
            this.redAntNestRadioButton.Size = new System.Drawing.Size(89, 17);
            this.redAntNestRadioButton.TabIndex = 2;
            this.redAntNestRadioButton.TabStop = true;
            this.redAntNestRadioButton.Text = "Red Ant Nest";
            this.redAntNestRadioButton.UseVisualStyleBackColor = true;
            // 
            // removeObjectRadioButton
            // 
            this.removeObjectRadioButton.AutoSize = true;
            this.removeObjectRadioButton.Location = new System.Drawing.Point(11, 89);
            this.removeObjectRadioButton.Name = "removeObjectRadioButton";
            this.removeObjectRadioButton.Size = new System.Drawing.Size(99, 17);
            this.removeObjectRadioButton.TabIndex = 3;
            this.removeObjectRadioButton.TabStop = true;
            this.removeObjectRadioButton.Text = "Remove Object";
            this.removeObjectRadioButton.UseVisualStyleBackColor = true;
            // 
            // blackNestRadioButton
            // 
            this.blackNestRadioButton.AutoSize = true;
            this.blackNestRadioButton.Location = new System.Drawing.Point(11, 43);
            this.blackNestRadioButton.Name = "blackNestRadioButton";
            this.blackNestRadioButton.Size = new System.Drawing.Size(96, 17);
            this.blackNestRadioButton.TabIndex = 1;
            this.blackNestRadioButton.TabStop = true;
            this.blackNestRadioButton.Text = "Black Ant Nest";
            this.blackNestRadioButton.UseVisualStyleBackColor = true;
            // 
            // foodRadioButton
            // 
            this.foodRadioButton.AutoSize = true;
            this.foodRadioButton.Checked = true;
            this.foodRadioButton.Location = new System.Drawing.Point(11, 19);
            this.foodRadioButton.Name = "foodRadioButton";
            this.foodRadioButton.Size = new System.Drawing.Size(49, 17);
            this.foodRadioButton.TabIndex = 0;
            this.foodRadioButton.TabStop = true;
            this.foodRadioButton.Text = "Food";
            this.foodRadioButton.UseVisualStyleBackColor = true;
            // 
            // blackAntsLabel
            // 
            this.blackAntsLabel.AutoSize = true;
            this.blackAntsLabel.Location = new System.Drawing.Point(8, 87);
            this.blackAntsLabel.Name = "blackAntsLabel";
            this.blackAntsLabel.Size = new System.Drawing.Size(61, 13);
            this.blackAntsLabel.TabIndex = 0;
            this.blackAntsLabel.Text = "Black Ants:";
            // 
            // blackAntTextBox
            // 
            this.blackAntTextBox.BackColor = System.Drawing.Color.Silver;
            this.blackAntTextBox.Location = new System.Drawing.Point(83, 84);
            this.blackAntTextBox.Name = "blackAntTextBox";
            this.blackAntTextBox.Size = new System.Drawing.Size(64, 20);
            this.blackAntTextBox.TabIndex = 2;
            this.blackAntTextBox.Text = "500";
            this.blackAntTextBox.TextChanged += new System.EventHandler(this.TextBox_Changed);
            // 
            // foodTextBox
            // 
            this.foodTextBox.BackColor = System.Drawing.Color.Silver;
            this.foodTextBox.Location = new System.Drawing.Point(83, 22);
            this.foodTextBox.Name = "foodTextBox";
            this.foodTextBox.Size = new System.Drawing.Size(64, 20);
            this.foodTextBox.TabIndex = 0;
            this.foodTextBox.Text = "200";
            this.foodTextBox.TextChanged += new System.EventHandler(this.TextBox_Changed);
            // 
            // foodCountLabel
            // 
            this.foodCountLabel.AutoSize = true;
            this.foodCountLabel.Location = new System.Drawing.Point(8, 25);
            this.foodCountLabel.Name = "foodCountLabel";
            this.foodCountLabel.Size = new System.Drawing.Size(74, 13);
            this.foodCountLabel.TabIndex = 0;
            this.foodCountLabel.Text = "Food quantity:";
            // 
            // setAntButton
            // 
            this.setAntButton.Location = new System.Drawing.Point(83, 136);
            this.setAntButton.Name = "setAntButton";
            this.setAntButton.Size = new System.Drawing.Size(64, 23);
            this.setAntButton.TabIndex = 4;
            this.setAntButton.Text = "Apply";
            this.setAntButton.UseVisualStyleBackColor = true;
            this.setAntButton.Click += new System.EventHandler(this.setAntButton_Click);
            // 
            // playButton
            // 
            this.playButton.Location = new System.Drawing.Point(11, 19);
            this.playButton.Name = "playButton";
            this.playButton.Size = new System.Drawing.Size(136, 23);
            this.playButton.TabIndex = 0;
            this.playButton.Text = "Play/Pause";
            this.playButton.UseVisualStyleBackColor = true;
            this.playButton.Click += new System.EventHandler(this.PlayPause);
            // 
            // setupGroupBox
            // 
            this.setupGroupBox.Controls.Add(this.clickHoldLabel);
            this.setupGroupBox.Controls.Add(this.redAntsLabel);
            this.setupGroupBox.Controls.Add(this.speedPlusButton);
            this.setupGroupBox.Controls.Add(this.redAntTextBox);
            this.setupGroupBox.Controls.Add(this.speedSettingLabel);
            this.setupGroupBox.Controls.Add(this.speedMinusButton);
            this.setupGroupBox.Controls.Add(this.foodCountLabel);
            this.setupGroupBox.Controls.Add(this.speedLabel);
            this.setupGroupBox.Controls.Add(this.blackAntsLabel);
            this.setupGroupBox.Controls.Add(this.setAntButton);
            this.setupGroupBox.Controls.Add(this.applyFoodButton);
            this.setupGroupBox.Controls.Add(this.blackAntTextBox);
            this.setupGroupBox.Controls.Add(this.foodTextBox);
            this.setupGroupBox.Location = new System.Drawing.Point(3, 148);
            this.setupGroupBox.Name = "setupGroupBox";
            this.setupGroupBox.Size = new System.Drawing.Size(154, 223);
            this.setupGroupBox.TabIndex = 1;
            this.setupGroupBox.TabStop = false;
            this.setupGroupBox.Text = "Setup";
            // 
            // clickHoldLabel
            // 
            this.clickHoldLabel.AutoSize = true;
            this.clickHoldLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 6F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.clickHoldLabel.Location = new System.Drawing.Point(92, 198);
            this.clickHoldLabel.Name = "clickHoldLabel";
            this.clickHoldLabel.Size = new System.Drawing.Size(46, 9);
            this.clickHoldLabel.TabIndex = 7;
            this.clickHoldLabel.Text = "Click && hold";
            // 
            // redAntsLabel
            // 
            this.redAntsLabel.AutoSize = true;
            this.redAntsLabel.Location = new System.Drawing.Point(8, 113);
            this.redAntsLabel.Name = "redAntsLabel";
            this.redAntsLabel.Size = new System.Drawing.Size(54, 13);
            this.redAntsLabel.TabIndex = 0;
            this.redAntsLabel.Text = "Red Ants:";
            // 
            // speedPlusButton
            // 
            this.speedPlusButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.speedPlusButton.Location = new System.Drawing.Point(115, 171);
            this.speedPlusButton.Name = "speedPlusButton";
            this.speedPlusButton.Size = new System.Drawing.Size(32, 23);
            this.speedPlusButton.TabIndex = 6;
            this.speedPlusButton.Text = "+";
            this.speedPlusButton.UseVisualStyleBackColor = true;
            this.speedPlusButton.Click += new System.EventHandler(this.speedPlusButton_Click);
            this.speedPlusButton.MouseDown += new System.Windows.Forms.MouseEventHandler(this.speedPlusButton_MouseDown);
            this.speedPlusButton.MouseUp += new System.Windows.Forms.MouseEventHandler(this.speedPlusButton_MouseUp);
            // 
            // redAntTextBox
            // 
            this.redAntTextBox.BackColor = System.Drawing.Color.Silver;
            this.redAntTextBox.Location = new System.Drawing.Point(83, 110);
            this.redAntTextBox.Name = "redAntTextBox";
            this.redAntTextBox.Size = new System.Drawing.Size(64, 20);
            this.redAntTextBox.TabIndex = 3;
            this.redAntTextBox.Text = "200";
            this.redAntTextBox.TextChanged += new System.EventHandler(this.TextBox_Changed);
            // 
            // speedSettingLabel
            // 
            this.speedSettingLabel.AutoSize = true;
            this.speedSettingLabel.Location = new System.Drawing.Point(8, 176);
            this.speedSettingLabel.Name = "speedSettingLabel";
            this.speedSettingLabel.Size = new System.Drawing.Size(41, 13);
            this.speedSettingLabel.TabIndex = 0;
            this.speedSettingLabel.Text = "Speed:";
            // 
            // speedMinusButton
            // 
            this.speedMinusButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.speedMinusButton.Location = new System.Drawing.Point(83, 171);
            this.speedMinusButton.Name = "speedMinusButton";
            this.speedMinusButton.Size = new System.Drawing.Size(32, 23);
            this.speedMinusButton.TabIndex = 5;
            this.speedMinusButton.Text = "-";
            this.speedMinusButton.UseVisualStyleBackColor = true;
            this.speedMinusButton.Click += new System.EventHandler(this.speedMinusButton_Click);
            this.speedMinusButton.MouseDown += new System.Windows.Forms.MouseEventHandler(this.speedMinusButton_MouseDown);
            this.speedMinusButton.MouseUp += new System.Windows.Forms.MouseEventHandler(this.speedMinusButton_MouseUp);
            // 
            // speedLabel
            // 
            this.speedLabel.AutoSize = true;
            this.speedLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.speedLabel.Location = new System.Drawing.Point(48, 176);
            this.speedLabel.Name = "speedLabel";
            this.speedLabel.Size = new System.Drawing.Size(25, 13);
            this.speedLabel.TabIndex = 0;
            this.speedLabel.Text = "4.0";
            // 
            // applyFoodButton
            // 
            this.applyFoodButton.Location = new System.Drawing.Point(83, 48);
            this.applyFoodButton.Name = "applyFoodButton";
            this.applyFoodButton.Size = new System.Drawing.Size(64, 23);
            this.applyFoodButton.TabIndex = 1;
            this.applyFoodButton.Text = "Apply";
            this.applyFoodButton.UseVisualStyleBackColor = true;
            this.applyFoodButton.Click += new System.EventHandler(this.applyFoodButton_Click);
            // 
            // playPauseGroupBox
            // 
            this.playPauseGroupBox.Controls.Add(this.playButton);
            this.playPauseGroupBox.Location = new System.Drawing.Point(3, 374);
            this.playPauseGroupBox.Name = "playPauseGroupBox";
            this.playPauseGroupBox.Size = new System.Drawing.Size(154, 56);
            this.playPauseGroupBox.TabIndex = 2;
            this.playPauseGroupBox.TabStop = false;
            this.playPauseGroupBox.Text = "Play/Pause";
            // 
            // infoButton
            // 
            this.infoButton.Location = new System.Drawing.Point(86, 564);
            this.infoButton.Name = "infoButton";
            this.infoButton.Size = new System.Drawing.Size(64, 23);
            this.infoButton.TabIndex = 3;
            this.infoButton.Text = "Info";
            this.infoButton.UseVisualStyleBackColor = true;
            this.infoButton.Click += new System.EventHandler(this.infoButton_Click);
            // 
            // settingsPanel
            // 
            this.settingsPanel.Controls.Add(this.groupBox1);
            this.settingsPanel.Controls.Add(this.actionsGroupBox);
            this.settingsPanel.Controls.Add(this.infoButton);
            this.settingsPanel.Controls.Add(this.setupGroupBox);
            this.settingsPanel.Controls.Add(this.playPauseGroupBox);
            this.settingsPanel.Location = new System.Drawing.Point(12, 12);
            this.settingsPanel.Name = "settingsPanel";
            this.settingsPanel.Size = new System.Drawing.Size(162, 598);
            this.settingsPanel.TabIndex = 4;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.resolutionApplyButton);
            this.groupBox1.Controls.Add(this.maxResolutionRadio);
            this.groupBox1.Controls.Add(this.mediumResolutionRadio);
            this.groupBox1.Controls.Add(this.smallResolutionRadio);
            this.groupBox1.Location = new System.Drawing.Point(3, 432);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(154, 127);
            this.groupBox1.TabIndex = 4;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Resolution";
            // 
            // resolutionApplyButton
            // 
            this.resolutionApplyButton.Location = new System.Drawing.Point(11, 89);
            this.resolutionApplyButton.Name = "resolutionApplyButton";
            this.resolutionApplyButton.Size = new System.Drawing.Size(64, 23);
            this.resolutionApplyButton.TabIndex = 6;
            this.resolutionApplyButton.Text = "Apply";
            this.resolutionApplyButton.UseVisualStyleBackColor = true;
            this.resolutionApplyButton.Click += new System.EventHandler(this.resolutionApplyButton_Click);
            // 
            // maxResolutionRadio
            // 
            this.maxResolutionRadio.AutoSize = true;
            this.maxResolutionRadio.Location = new System.Drawing.Point(11, 66);
            this.maxResolutionRadio.Name = "maxResolutionRadio";
            this.maxResolutionRadio.Size = new System.Drawing.Size(69, 17);
            this.maxResolutionRadio.TabIndex = 5;
            this.maxResolutionRadio.Text = "Maximum";
            this.maxResolutionRadio.UseVisualStyleBackColor = true;
            // 
            // mediumResolutionRadio
            // 
            this.mediumResolutionRadio.AutoSize = true;
            this.mediumResolutionRadio.BackColor = System.Drawing.Color.LimeGreen;
            this.mediumResolutionRadio.Checked = true;
            this.mediumResolutionRadio.Location = new System.Drawing.Point(11, 43);
            this.mediumResolutionRadio.Name = "mediumResolutionRadio";
            this.mediumResolutionRadio.Size = new System.Drawing.Size(78, 17);
            this.mediumResolutionRadio.TabIndex = 4;
            this.mediumResolutionRadio.TabStop = true;
            this.mediumResolutionRadio.Text = "1024 x 768";
            this.mediumResolutionRadio.UseVisualStyleBackColor = false;
            // 
            // smallResolutionRadio
            // 
            this.smallResolutionRadio.AutoSize = true;
            this.smallResolutionRadio.BackColor = System.Drawing.Color.Transparent;
            this.smallResolutionRadio.Location = new System.Drawing.Point(11, 19);
            this.smallResolutionRadio.Name = "smallResolutionRadio";
            this.smallResolutionRadio.Size = new System.Drawing.Size(72, 17);
            this.smallResolutionRadio.TabIndex = 3;
            this.smallResolutionRadio.Text = "800 x 600";
            this.smallResolutionRadio.UseVisualStyleBackColor = false;
            // 
            // AntSimulationForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Tan;
            this.ClientSize = new System.Drawing.Size(182, 615);
            this.Controls.Add(this.settingsPanel);
            this.Controls.Add(this.simulationPanel);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "AntSimulationForm";
            this.Text = "Ant Simulation";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Settings_FormClosed);
            this.actionsGroupBox.ResumeLayout(false);
            this.actionsGroupBox.PerformLayout();
            this.setupGroupBox.ResumeLayout(false);
            this.setupGroupBox.PerformLayout();
            this.playPauseGroupBox.ResumeLayout(false);
            this.settingsPanel.ResumeLayout(false);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel simulationPanel;
        private System.Windows.Forms.GroupBox actionsGroupBox;
        private System.Windows.Forms.RadioButton removeObjectRadioButton;
        private System.Windows.Forms.RadioButton blackNestRadioButton;
        private System.Windows.Forms.RadioButton foodRadioButton;
        private System.Windows.Forms.Label blackAntsLabel;
        private System.Windows.Forms.TextBox blackAntTextBox;
        private System.Windows.Forms.TextBox foodTextBox;
        private System.Windows.Forms.Label foodCountLabel;
        private System.Windows.Forms.Button setAntButton;
        private System.Windows.Forms.Button playButton;
        private System.Windows.Forms.GroupBox setupGroupBox;
        private System.Windows.Forms.GroupBox playPauseGroupBox;
        private System.Windows.Forms.RadioButton redAntNestRadioButton;
        private System.Windows.Forms.Label redAntsLabel;
        private System.Windows.Forms.TextBox redAntTextBox;
        private System.Windows.Forms.Button infoButton;
        private System.Windows.Forms.Label speedSettingLabel;
        private System.Windows.Forms.Button speedPlusButton;
        private System.Windows.Forms.Button speedMinusButton;
        private System.Windows.Forms.Label speedLabel;
        private System.Windows.Forms.Label clickHoldLabel;
        private System.Windows.Forms.Panel settingsPanel;
        private System.Windows.Forms.GroupBox groupBox1;
        public System.Windows.Forms.RadioButton maxResolutionRadio;
        private System.Windows.Forms.RadioButton mediumResolutionRadio;
        private System.Windows.Forms.RadioButton smallResolutionRadio;
        private System.Windows.Forms.Button resolutionApplyButton;
        private System.Windows.Forms.Button applyFoodButton;
        private System.Windows.Forms.RadioButton checkRadioButton;
    }
}
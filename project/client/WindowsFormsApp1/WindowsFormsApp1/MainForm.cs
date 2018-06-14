using MySql;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    public partial class MainForm : Form
    {
        private int id;

        Bitmap bt;
        private DBConnect dBConnect;

        public MainForm(int id)
        {
            InitializeComponent();
            this.id = id;
        }


        public Bitmap snip(Point point, Size size)
        {
            Bitmap bmpScreenshot = new Bitmap(size.Width,
                                           size.Height,
                                           PixelFormat.Format32bppArgb);

            var gfxScreenshot = Graphics.FromImage(bmpScreenshot);

            gfxScreenshot.CopyFromScreen(point.X,
                                        point.Y,
                                        0,
                                        0,
                                        size,
                                        CopyPixelOperation.SourceCopy);
            
            return bmpScreenshot;
        }




        private void button1_Click(object sender, EventArgs e)
        {

            dBConnect.Update(null, id);
            button4.Text = "Share";
            button4.ForeColor = Color.Red;
            toolTip1.RemoveAll();


            this.WindowState = FormWindowState.Minimized;

            Capture capture = new Capture();

            Capture testDialog = new Capture();
            
            if (capture.ShowDialog(this) == DialogResult.OK)
            {
                if (capture.s1.Width > 0 && capture.s1.Height > 0)
                {
                    bt = snip(capture.p1, capture.s1);
                    this.WindowState = FormWindowState.Normal;
                    this.Width = capture.s1.Width + 10;
                    this.Height = capture.s1.Height + 76;
                    if (capture.p1.Y - 76 > 0 && capture.p1.X - 10 > 0)
                    {

                        Left = capture.p1.X - 10;
                        Top = capture.p1.Y - 76;
                    }
                    else
                    {
                        Left = capture.p1.X;
                        Top = capture.p1.Y;
                    }

                    button2.Enabled = true;
                    button3.Enabled = true;
                    button4.Enabled = true;
                    button4.Focus();
                }else this.WindowState = FormWindowState.Normal;
            }
            else
            {
                this.bt = null;
                this.WindowState = FormWindowState.Normal;

            }
            

            capture.Dispose();

            pictureBox1.Image = bt;
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog1 = new SaveFileDialog();
            
            saveFileDialog1.Title = "Save image";
            
            saveFileDialog1.CheckPathExists = true;

            saveFileDialog1.DefaultExt = "PNG";

            saveFileDialog1.Filter = "image file (*.png)|*.png";

            saveFileDialog1.FilterIndex = 2;

            saveFileDialog1.RestoreDirectory = true;



            if (saveFileDialog1.ShowDialog() == DialogResult.OK)
            {
                bt.Save(saveFileDialog1.FileName, ImageFormat.Png);
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            pictureBox1.Image = null;
            button2.Enabled = false;
            button3.Enabled = false;
            button4.Enabled = false;
            this.Size = new Size(490, 76);


            dBConnect.Update(null, id);
            button4.Text = "Share";
            button4.ForeColor = Color.Red;
            toolTip1.RemoveAll();

        }

        private void button4_Click(object sender, EventArgs e)
        {
            if (button4.Text.Equals("Share"))
            {   //update db
                dBConnect.Update(bt, id);
                //copy to oclipboard 
                Thread staThread = new Thread(
                            delegate ()
                            {
                                if (Clipboard.ContainsText())
                                {
                                    Clipboard.SetText("http://"+ Properties.Settings.Default.host+ "/img?id="+id);
                                }
                            });
                staThread.SetApartmentState(ApartmentState.STA);
                staThread.Start();
                staThread.Join();
                toolTip1.SetToolTip(button4, "Link copyed to clipboard.");
                button4.ForeColor = Color.LimeGreen;
                button4.Text = "Stop Sharing";

            }
            else if (button4.Text.Equals("Stop Sharing"))
            {
                dBConnect.Update(null, id);
                button4.Text = "Share";
                button4.ForeColor = Color.Red;
                toolTip1.RemoveAll();
            }
        }

        private void MainForm_FormClosed(object sender, FormClosedEventArgs e)
        {
           
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            dBConnect = new DBConnect();

            if (id < 1)
            {
                AuthForm authForm = new AuthForm();
                if (authForm.ShowDialog(this) == DialogResult.OK)
                {
                    this.id = authForm.id;
                }
                else Application.Exit();
            }

            button1.PerformClick();
        }

        private void toolTip1_Popup(object sender, PopupEventArgs e)
        {

        }

        private void button4_MouseHover(object sender, EventArgs e)
        {
         
        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            dBConnect.Update(null, id);
            button4.Text = "Share";
            button4.ForeColor = Color.Red;
            toolTip1.RemoveAll();
        }

        private void button5_Click(object sender, EventArgs e)
        {
            dBConnect.Update(null, id);
       
            Properties.Settings.Default.email = "";
            Properties.Settings.Default.password = "";
            Properties.Settings.Default.Save();

            System.Diagnostics.Process.Start(Application.ExecutablePath); 
            this.Close();
        }
    }
}

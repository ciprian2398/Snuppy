using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    public partial class Capture : Form
    {

        public Capture()
        {
            InitializeComponent();
        }

        private bool down;
        private Point point;

        public Point p1;
        public Size s1;

        private void Form1_MouseDown(object sender, MouseEventArgs e)
        {
            panel1.Location = new Point(Cursor.Position.X, Cursor.Position.Y);
            down = true;
            point = panel1.Location;
        }

        private void Form1_MouseUp(object sender, MouseEventArgs e)
        {
            down = false;

            p1 = panel1.Location;
            s1 = panel1.Size;

            DialogResult = DialogResult.OK;

            panel1.Size = new Size(0, 0);
        }

        private void Form1_MouseMove(object sender, MouseEventArgs e)
        {
            if (down)
            {
                
                if (Cursor.Position.X > point.X && Cursor.Position.Y > point.Y)
                {
                    panel1.Size = new Size((Cursor.Position.X - point.X), (Cursor.Position.Y - point.Y));
                }
                
                if (Cursor.Position.X > point.X && Cursor.Position.Y < point.Y)
                {
                    panel1.Size = new Size((Cursor.Position.X - panel1.Location.X), panel1.Size.Height + (panel1.Location.Y - Cursor.Position.Y));

                    panel1.Location = new Point(panel1.Location.X, Cursor.Position.Y);

                }

                if (Cursor.Position.X < point.X && Cursor.Position.Y < point.Y)
                {
                    panel1.Size = new Size(point.X - Cursor.Position.X, point.Y - Cursor.Position.Y);

                    panel1.Location = new Point(Cursor.Position.X, Cursor.Position.Y);

                }

                if (Cursor.Position.X < point.X && Cursor.Position.Y > point.Y)
                {
                    panel1.Size = new Size(point.X - Cursor.Position.X, Cursor.Position.Y - point.Y);

                    panel1.Location = new Point(Cursor.Position.X, panel1.Location.Y);

                }
                
            }
        }
    }
}

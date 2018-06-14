using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    public partial class AuthForm : Form
    {
        public int id; 

        public AuthForm()
        {
            InitializeComponent();
        }

        private void AuthForm_Load(object sender, EventArgs e)
        {

        }

        private void linkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            String host = Properties.Settings.Default.host;
            Process.Start("http://"+host+"/reg");
        }

        private void button1_Click(object sender, EventArgs e)
        {
            String email = textBox1.Text;
            String password = textBox2.Text;

            DBConnect dBConnect = new DBConnect();
            int id = dBConnect.GetIdIfLogin(email, password);
            if (id > 0)
            {
                Properties.Settings.Default.email = email;
                Properties.Settings.Default.password = password;
                Properties.Settings.Default.Save();

                this.id = id;
                DialogResult = DialogResult.OK;

            }
            else
            {
                MessageBox.Show("Wrong email or password !");
            }
        }

        private void textBox2_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == Convert.ToChar(Keys.Enter))
            {
                button1.PerformClick();
            }
        }

        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == Convert.ToChar(Keys.Enter))
            {
                textBox2.Focus();
            }
        }
    }
}

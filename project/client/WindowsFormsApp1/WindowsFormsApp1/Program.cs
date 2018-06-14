using MySql;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);


            if (Properties.Settings.Default.firstEntry.Equals(true))
            {
                MessageBox.Show(" Using this application you agree to the following policy.\n No one is responsible for lost or stolen data.\n All inputs may be public.");
                Properties.Settings.Default.firstEntry = false;
                Properties.Settings.Default.host = Properties.Settings.Default.host;
                Properties.Settings.Default.Save();
                Application.Run(new MainForm(-1));
            }
            else
            {
                if (Properties.Settings.Default.email.Equals(String.Empty) ||
                    Properties.Settings.Default.password.Equals(String.Empty))
                {
                    Application.Run(new MainForm(-1));
                }
                else
                {
                    String email = Properties.Settings.Default.email;
                    String password = Properties.Settings.Default.password;

                    DBConnect dBConnect = new DBConnect();
                    int id = dBConnect.GetIdIfLogin(email, password);
                    Application.Run(new MainForm(id));


                }
            }
            
        }
    }
}

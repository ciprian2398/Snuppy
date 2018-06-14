

using MySql.Data.MySqlClient;

using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;


namespace WindowsFormsApp1
{
    class DBConnect
    {
        private MySqlConnection connection;
        private string server;
        private string database;
        private string uid;
        private string password;

        //Constructor
        public DBConnect()
        {
            Initialize();
        }

        //Initialize values
        private void Initialize()
        {
            server = Properties.Settings.Default.host;
            ;
            database = "snip_db";
            uid = "def_user";
            password = "P.cD#Mypx,D7MycXr44!";
            string connectionString;
            connectionString = "SERVER=" + server + ";" + "DATABASE=" +
            database + ";" + "UID=" + uid + ";" + "PASSWORD=" + password + ";";

            connection = new MySqlConnection(connectionString);
        }

        private bool OpenConnection()
        {
            try
            {
                connection.Open();
                return true;
            }
            catch (MySqlException ex)
            {
                //When handling errors, you can your application's response based 
                //on the error number.
                //The two most common error numbers when connecting are as follows:
                //0: Cannot connect to server.
                //1045: Invalid user name and/or password.
                switch (ex.Number)
                {
                    case 0:
                        MessageBox.Show("Cannot connect to server.  Contact administrator");
                        break;

                    case 1045:
                        MessageBox.Show("Invalid username/password, please try again");
                        break;
                }
                MessageBox.Show("Connection error," + ex.Number);
                return false;

            }
        }

        //Close connection
        private bool CloseConnection()
        {
            try
            {
                connection.Close();
                return true;
            }
            catch (MySqlException ex)
            {
                MessageBox.Show(ex.Message);
                return false;
            }
        }

        public void Insert()
        {
            //string query = "INSERT INTO tableinfo (name, age) VALUES('John Smith', '33')";
            string query = "INSERT INTO users(user_email, user_password) VALUES('email2', 'passsword2'); ";

            //open connection
            if (this.OpenConnection() == true)
            {
                //create command and assign the query and connection from the constructor
                MySqlCommand cmd = new MySqlCommand(query, connection);

                //Execute command
                cmd.ExecuteNonQuery();

                //close connection
                this.CloseConnection();
            }
        }

        //Update statement
        public void Update()
        {
            string query = "UPDATE tableinfo SET name='Joe', age='22' WHERE name='John Smith'";

            //Open connection
            if (this.OpenConnection() == true)
            {
                //create mysql command
                MySqlCommand cmd = new MySqlCommand();
                //Assign the query using CommandText
                cmd.CommandText = query;
                //Assign the connection using Connection
                cmd.Connection = connection;

                //Execute query
                cmd.ExecuteNonQuery();

                //close connection
                this.CloseConnection();
            }
        }

        public static byte[] ImageToByte(Image img)
        {
            ImageConverter converter = new ImageConverter();
            return (byte[])converter.ConvertTo(img, typeof(byte[]));
        }

        public void Update(Bitmap bitmap, int idUser)
        {
            if (this.OpenConnection() == true)
            {
                //command.CommandText = "UPDATE users SET image_blob = '@UserImage' WHERE id_user = '" + 1 + "';";
                var userImage = ImageToByte(bitmap);
                var command = new MySqlCommand("", connection);
                if (bitmap != null) {
                    command.CommandText = "UPDATE users SET image_blob = @userImage WHERE id_user = @userId ;";
                    var paramUserImage = new MySqlParameter("@userImage", MySqlDbType.Blob, userImage.Length);
                    var paramUserId = new MySqlParameter("@userId", MySqlDbType.Int32);
                    paramUserImage.Value = userImage;
                    paramUserId.Value = idUser;
                    command.Parameters.Add(paramUserImage);
                    command.Parameters.Add(paramUserId);
                    command.ExecuteNonQuery();
                }
                else
                {
                    command.CommandText = "UPDATE users SET image_blob = null WHERE id_user = @userId ;";
                    var paramUserId = new MySqlParameter("@userId", MySqlDbType.Int32);
                    paramUserId.Value = idUser;
                    command.Parameters.Add(paramUserId);
                    command.ExecuteNonQuery();
                }
                CloseConnection();
            }
        }

        //Delete statement
        public void Delete()
        {
            string query = "DELETE FROM tableinfo WHERE name='John Smith'";

            if (this.OpenConnection() == true)
            {
                MySqlCommand cmd = new MySqlCommand(query, connection);
                cmd.ExecuteNonQuery();
                this.CloseConnection();
            }
        }

        public int GetIdIfLogin(String email, String password)
        {
            string query = "select id_user from users where user_email LIKE '"+email+"' and user_password  LIKE BINARY  '"+password+"'";
            int id = -1;
            
            if (this.OpenConnection() == true)
            {
                MySqlCommand cmd = new MySqlCommand(query, connection);
                MySqlDataReader dataReader = cmd.ExecuteReader();
                
                while (dataReader.Read())
                {
                    id = Convert.ToInt32(dataReader["id_user"]);
                }
                dataReader.Close();
                this.CloseConnection();
            }

            return id;

        }

        public List<string>[] Select()
        {
            string query = "SELECT * FROM tableinfo";

            //Create a list to store the result
            List<string>[] list = new List<string>[3];
            list[0] = new List<string>();
            list[1] = new List<string>();
            list[2] = new List<string>();

            //Open connection
            if (this.OpenConnection() == true)
            {
                //Create Command
                MySqlCommand cmd = new MySqlCommand(query, connection);
                //Create a data reader and Execute the command
                MySqlDataReader dataReader = cmd.ExecuteReader();

                //Read the data and store them in the list
                while (dataReader.Read())
                {
                    list[0].Add(dataReader["id"] + "");
                    list[1].Add(dataReader["name"] + "");
                    list[2].Add(dataReader["age"] + "");
                }

                //close Data Reader
                dataReader.Close();

                //close Connection
                this.CloseConnection();

                //return list to be displayed
                return list;
            }
            else
            {
                return list;
            }
        }

        public int Count()
        {
            string query = "SELECT Count(*) FROM tableinfo";
            int Count = -1;

            //Open Connection
            if (this.OpenConnection() == true)
            {
                //Create Mysql Command
                MySqlCommand cmd = new MySqlCommand(query, connection);

                //ExecuteScalar will return one value
                Count = int.Parse(cmd.ExecuteScalar() + "");

                //close Connection
                this.CloseConnection();

                return Count;
            }
            else
            {
                return Count;
            }
        }
    }
}

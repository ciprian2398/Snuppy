package com.snuppy;

import com.oracle.tools.packager.IOUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static com.oracle.tools.packager.IOUtils.*;

public class PostHandler extends Connection implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        // parse request
        Map<String, Object> parameters = new HashMap<String, Object>();
        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        RequestHandler.parseQuery(query, parameters);

        if (parameters.size() == 2) {
            String email = (String) parameters.get("email");
            String password = (String) parameters.get("psw");

            if (!this.emailExist(email) && password.length() >= 5) {
                //add new user to db
                System.out.println("New user aded (" + email + ")");
                this.insert(new UsersEntity(email, password, null));
                String response = "<p>Your personal ID is <b>" + this.getIdByEmail(email) + "</b> <a href ='/snuppy'>download app</a></p>";
                he.sendResponseHeaders(200, response.length());
                OutputStream os = he.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();

            } else {
                //reg_fail
                InputStream in = getClass().getResourceAsStream("/HTML/reg_fail.html");
                BufferedInputStream reader = new BufferedInputStream(in);
                byte[] bytearray = reader.readAllBytes();

                // ok, we are ready to send the response.
                he.sendResponseHeaders(200, bytearray.length);
                OutputStream os = he.getResponseBody();
                os.write(bytearray, 0, bytearray.length);
                os.close();
            }


        } else {
            //reg
            InputStream in = getClass().getResourceAsStream("/HTML/reg.html");
            BufferedInputStream reader = new BufferedInputStream(in);
            byte[] bytearray = reader.readAllBytes();

            // ok, we are ready to send the response.
            he.sendResponseHeaders(200, bytearray.length);
            OutputStream os = he.getResponseBody();
            os.write(bytearray, 0, bytearray.length);
            os.close();
        }
    }
}

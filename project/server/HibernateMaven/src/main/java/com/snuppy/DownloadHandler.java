package com.snuppy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class DownloadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        InputStream in = getClass().getResourceAsStream("/clientApp/Snuppy.zip");
        BufferedInputStream reader = new BufferedInputStream(in);
        byte[] bytearray = reader.readAllBytes();
        // ok, we are ready to send the response.
        he.sendResponseHeaders(200, bytearray.length);
        OutputStream os = he.getResponseBody();
        os.write(bytearray, 0, bytearray.length);
        os.close();
    }
}

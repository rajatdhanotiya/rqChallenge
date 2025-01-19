package com.reliaquest.api.util;

import com.reliaquest.api.bean.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

@Service
public class ApiConnector {

    private final Logger LOGGER = LoggerFactory.getLogger(ApiConnector.class);
    //private static final Logger LOGGER = LogManager.getLogger(ApiConnector.class);

    public static final String CONTENT_TYPE = "Content-Type";

    @Value(value = "${max.connection.timeout:60000}")
    private String maxConnectionTimeout;

    @Value(value = "${max.socket.timeout:300000}")
    private String maxSocketTimeout;


    public ApiResponse doGet(String url, Map<String, Object> apiContextMap) throws ProtocolException, MalformedURLException, IOException {
        HttpURLConnection connection = null;
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(Integer.parseInt(maxConnectionTimeout));
            connection.setReadTimeout(Integer.parseInt(maxSocketTimeout));


            int statusCode = connection.getResponseCode();
            String response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                response = responseBuilder.toString();
            }

            LOGGER.debug("Exiting out " + getClass().getSimpleName() + " doGet() method for Url: " + url + " with status: " + statusCode);
            return new ApiResponse(statusCode, response);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public ApiResponse doPost(String url, String payload, Map<String, Object> apiContextMap) throws ProtocolException, MalformedURLException, IOException {
        HttpURLConnection connection = null;
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(Integer.parseInt(maxConnectionTimeout));
            connection.setReadTimeout(Integer.parseInt(maxSocketTimeout));
            connection.setDoOutput(true);
            connection.setRequestProperty(CONTENT_TYPE, "application/json");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();
            String response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                response = responseBuilder.toString();
            }

            LOGGER.debug("Exiting out " + getClass().getSimpleName() + " doPost() method for Url: " + url + " with status: " + statusCode);
            return new ApiResponse(statusCode, response);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public ApiResponse doDelete(String url, String payload, Map<String, Object> apiContextMap) throws ProtocolException, MalformedURLException, IOException {
        HttpURLConnection connection = null;
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setConnectTimeout(Integer.parseInt(maxConnectionTimeout));
            connection.setReadTimeout(Integer.parseInt(maxSocketTimeout));
            connection.setDoOutput(true);
            connection.setRequestProperty(CONTENT_TYPE, "application/json");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();
            String response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                response = responseBuilder.toString();
            }

            LOGGER.debug("Exiting out " + getClass().getSimpleName() + " doDelete() method for Url: " + url + " with status: " + statusCode);
            return new ApiResponse(statusCode, response);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}

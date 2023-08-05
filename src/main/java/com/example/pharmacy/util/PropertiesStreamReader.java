package com.example.pharmacy.util;

import com.example.pharmacy.exception.ServiceException;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PropertiesStreamReader {

    public Path getFileFromResource(String fileName) throws ServiceException {
        URL resource;
        Path path;
        try {
            resource = getClass().getClassLoader().getResource(fileName);
            if (resource != null) {
                path = Paths.get(resource.toURI());
            } else{
                throw new ServiceException("File not found " + fileName);
            }
        } catch (URISyntaxException ex) {
            throw new ServiceException(ex);
        }
        return path;
    }
}

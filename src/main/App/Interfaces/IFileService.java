package Interfaces;

import Exceptions.FileException;

import java.io.IOException;

public interface IFileService{
    String readFile(String path, String filename) throws IOException;

    String saveFile(String path, String filename, String content) throws IOException;

    public String uploadFile(String path) throws FileException;
    String DownloadFile();
    void DeleteFile(String Path);

}
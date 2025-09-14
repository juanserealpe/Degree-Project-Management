package App.Interfaces;

import App.Exceptions.FileException;

public interface IFileService{
    public String uploadFile(String path) throws FileException;
    String DownloadFile();
    void DeleteFile(String Path);

}
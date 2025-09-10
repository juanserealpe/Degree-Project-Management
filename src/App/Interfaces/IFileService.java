package App.Interfaces;

import App.Services.FileResult;

public interface IFileService{
    FileResult UploadFile();
    FileResult DownloadFile();
    void DeleteFile(String Path);

}
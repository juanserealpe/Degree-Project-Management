package App.Services;

public class FileResult {
    String path;
    FileStatus fileStatus;

    public FileResult() {
    }

    public FileResult(String path, FileStatus fileStatus) {
        this.path = path;
        this.fileStatus = fileStatus;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileStatus(FileStatus fileStatus) {
        this.fileStatus = fileStatus;
    }

    public FileStatus getFileStatus() {
        return fileStatus;
    }
}
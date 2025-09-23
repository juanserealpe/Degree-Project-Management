package App.Models;

import java.util.Date;

public class FormatA extends Process{
    private String title;
    private String observations;
    private int attempt;
    private String URL;

    public FormatA(String title, String observations, String URL, int attempt) {
        this.title = title;
        this.observations = observations;
        this.URL = URL;
        this.attempt = attempt;
    }

    public FormatA() {
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getDate() {
        return super.getDate();
    }

    @Override
    public EnumState getEnumState() {
        return super.getEnumState();
    }

    @Override
    public void setDate(Date date) {
        super.setDate(date);
    }

    @Override
    public void setEnumState(EnumState enumState) {
        super.setEnumState(enumState);
    }
}

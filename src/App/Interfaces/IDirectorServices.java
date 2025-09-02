package App.Interfaces;

import App.Dtos.DirectorDTO;

public interface IDirectorServices {

    public void registerDirector(DirectorDTO prmStudent) throws Exception;

    public void deleteDirector(String prmEmail) throws Exception;
}

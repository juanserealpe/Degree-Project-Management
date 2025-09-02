package App.Interfaces;

import App.Dtos.DirectorDTO;
import java.util.List;

public interface IDirectorServices {

    public void registerDirector(DirectorDTO prmDirector) throws Exception;

    public void deleteDirector(String prmEmail) throws Exception;

    public List<DirectorDTO> getAllDirectors() throws Exception;

    public DirectorDTO getDirectorByEmail(String prmEmail) throws Exception;

    public DirectorDTO validateDirector(DirectorDTO prmDirector) throws Exception;
}

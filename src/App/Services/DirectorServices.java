package App.Services;

import App.Dtos.DirectorDTO;
import App.Interfaces.IDirectorServices;
import App.Interfaces.IEncrypt;
import App.Interfaces.IRepository;

public class DirectorServices implements IDirectorServices {

    private final IRepository<DirectorDTO> _directorRepository;
    private final IEncrypt _encryptService;

    public DirectorServices(IRepository<DirectorDTO> _directorRepository, IEncrypt _encryptService) {
        this._directorRepository = _directorRepository;
        this._encryptService = _encryptService;
    }

    @Override
    public void registerDirector(DirectorDTO prmStudent) throws Exception {
        String encryptedPassword = _encryptService.Encrypt(prmStudent.getAccount().getPassword());
        prmStudent.getAccount().setPassword(encryptedPassword);
        _directorRepository.toAdd(prmStudent);
    }

    @Override
    public void deleteDirector(String prmEmail) throws Exception {
        _directorRepository.toDeleteByString(prmEmail);
    }

}

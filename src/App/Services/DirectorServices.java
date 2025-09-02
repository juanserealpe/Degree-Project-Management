package App.Services;

import App.Dtos.DirectorDTO;
import App.Interfaces.IDirectorServices;
import App.Interfaces.IEncrypt;
import App.Interfaces.IRepository;
import App.Interfaces.IValidatorRegisterServices;
import java.util.List;

public class DirectorServices implements IDirectorServices {

    private final IRepository<DirectorDTO> _directorRepository;
    private final IEncrypt _encryptService;
    private final IValidatorRegisterServices _validatorService;

    public DirectorServices(IRepository<DirectorDTO> _directorRepository, IEncrypt _encryptService, IValidatorRegisterServices _validatorService) {
        this._directorRepository = _directorRepository;
        this._encryptService = _encryptService;
        this._validatorService = _validatorService;
    }

    @Override
    public void registerDirector(DirectorDTO prmDirector) throws Exception {
        _validatorService.isValidEmail(prmDirector.getDirector().getEmail());
        _validatorService.isValidTelephone(prmDirector.getDirector().getNumberPhone());
        _validatorService.isValidPassword(prmDirector.getAccount().getPassword());
        String encryptedPassword = _encryptService.Encrypt(prmDirector.getAccount().getPassword());
        prmDirector.getAccount().setPassword(encryptedPassword);
        _directorRepository.toAdd(prmDirector);
    }

    @Override
    public void deleteDirector(String prmEmail) throws Exception {
        _directorRepository.toDeleteByString(prmEmail);
    }

    @Override
    public List<DirectorDTO> getAllDirectors() throws Exception {
        return _directorRepository.toGetAll();
    }

    @Override
    public DirectorDTO getDirectorByEmail(String prmEmail) throws Exception {
        return _directorRepository.toGetByString(prmEmail);
    }

}

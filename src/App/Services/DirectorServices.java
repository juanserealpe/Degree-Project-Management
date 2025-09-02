package App.Services;

import App.Dtos.DirectorDTO;
import App.Interfaces.IDataNormalizerServices;
import App.Interfaces.IDirectorServices;
import App.Interfaces.IEncrypt;
import App.Interfaces.IRepository;
import App.Interfaces.IValidatorRegisterServices;
import java.util.List;

public class DirectorServices implements IDirectorServices {

    private final IRepository<DirectorDTO> _directorRepository;
    private final IEncrypt _encryptService;
    private final IValidatorRegisterServices _validatorService;
    private final IDataNormalizerServices _dataNormalizeService;

    public DirectorServices(IRepository<DirectorDTO> _directorRepository, IEncrypt _encryptService, IValidatorRegisterServices _validatorService, IDataNormalizerServices _dataNormalizeService) {
        this._directorRepository = _directorRepository;
        this._encryptService = _encryptService;
        this._validatorService = _validatorService;
        this._dataNormalizeService = _dataNormalizeService;
    }

    @Override
    public void registerDirector(DirectorDTO prmDirector) throws Exception {
        DirectorDTO validatedStudentDTO = validateDirector(prmDirector);
        String encryptedPassword = _encryptService.Encrypt(validatedStudentDTO.getAccount().getPassword());
        validatedStudentDTO.getAccount().setPassword(encryptedPassword);
        _directorRepository.toAdd(validatedStudentDTO);
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

    @Override
    public DirectorDTO validateDirector(DirectorDTO prmDirector) throws Exception {
        _validatorService.isValidEmail(prmDirector.getDirector().getEmail());
        _validatorService.isValidTelephone(prmDirector.getDirector().getNumberPhone());
        _validatorService.isValidPassword(prmDirector.getAccount().getPassword());

        String newNames = _dataNormalizeService.normalizeString(prmDirector.getDirector().getNames());
        String newLastNames = _dataNormalizeService.normalizeString(prmDirector.getDirector().getLastNames());

        prmDirector.getDirector().setNames(newNames);
        prmDirector.getDirector().setLastNames(newLastNames);
        return prmDirector;
    }

}

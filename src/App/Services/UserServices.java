package Services;

import Dtos.StudentDTO;
import Dtos.UserDTO;
import Interfaces.*;
import Models.User;

import java.util.List;

public class UserServices implements IUserServices {

    private final IRepository<UserDTO> _userRepository;
    private final IEncrypt _encryptService;
    private final IValidatorRegisterServices _validatorService;
    private final IDataNormalizerServices _dataNormalizeService;

    public UserServices(IRepository<UserDTO> _UserRepository, IEncrypt _encryptService, IValidatorRegisterServices _validatorService, IDataNormalizerServices _dataNormalizeService) {
        this._userRepository = _UserRepository;
        this._encryptService = _encryptService;
        this._validatorService = _validatorService;
        this._dataNormalizeService = _dataNormalizeService;
    }

    @Override
    public void registerUser(UserDTO prmUser) throws Exception {
        UserDTO validatedUserDTO = validateUser(prmUser);
        String encryptedPassword = _encryptService.Encrypt(validatedUserDTO.getAccount().getPassword());
        validatedUserDTO.getAccount().setPassword(encryptedPassword);
        _userRepository.toAdd(prmUser);
    }

    @Override
    public void deleteUser(String prmEmail) throws Exception {

    }

    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        return _userRepository.toGetAll();
    }

    @Override
    public UserDTO getUserByEmail(String prmEmail) throws Exception {
        return null;
    }

    @Override
    public UserDTO validateUser(UserDTO prmUser) throws Exception {
        _validatorService.isValidEmail(prmUser.getUser().getEmail());
        _validatorService.isValidTelephone(prmUser.getUser().getNumberPhone());
        _validatorService.isValidPassword(prmUser.getAccount().getPassword());
        String newNames = _dataNormalizeService.normalizeString(prmUser.getUser().getNames());
        String newLastNames = _dataNormalizeService.normalizeString(prmUser.getUser().getLastNames());
        prmUser.getUser().setNames(newNames);
        prmUser.getUser().setLastNames(newLastNames);
        return prmUser;
    }
}

package App.Interfaces;

import App.Models.Role;
import java.util.List;

public interface IRoleRepository {

    List<Role> getByEmail(String prmEmail) throws Exception;
}

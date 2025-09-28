package Services;

import Interfaces.IDegreeWorkRepository;
import Models.DegreeWork;
import Models.FormatA;

import java.util.List;

public class DirectorService {
    private IDegreeWorkRepository destinationRepository;
    public DirectorService(IDegreeWorkRepository degreeWorkRepository) {
        destinationRepository = degreeWorkRepository;
    }
    public List<DegreeWork> getDegreeWorksById(int DirectorId) {
        return destinationRepository.getDegreeWorksByDirectorId(DirectorId);
    }
    public List<FormatA> getFormatAByDirectorId(int DirectorId) {
        return destinationRepository.getFormatsAByDirectorId(DirectorId);
    }
}

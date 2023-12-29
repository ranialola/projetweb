package univ.rouen.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import univ.rouen.backend.DAO.Component;
import univ.rouen.backend.DAO.Illustrations;
import univ.rouen.backend.Repository.IllustrationsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IllustrationsService {

@Autowired
private IllustrationsRepository illustrationsRepository;

public List<Illustrations> getAllIllustrations() {
return illustrationsRepository.findAll();
}

public Optional<Illustrations> getIllustrationById(Long id) {
return illustrationsRepository.findById(id);
}

public Illustrations addIllustration(Illustrations illustration) {
return illustrationsRepository.save(illustration);
}

public void deleteIllustration(Long id) {
illustrationsRepository.deleteById(id);
}

public Illustrations addComponentToIllustration(Long illustrationId, Component component) {
Optional<Illustrations> optionalIllustrations = illustrationsRepository.findById(illustrationId);
if (optionalIllustrations.isPresent()) {
Illustrations illustration = optionalIllustrations.get();
illustration.getComponents().add(component);
return illustrationsRepository.save(illustration);
}
// Gérer le cas où l'illustration n'est pas trouvée
return null;
}
}
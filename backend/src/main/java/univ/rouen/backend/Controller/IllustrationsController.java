package univ.rouen.backend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import univ.rouen.backend.DAO.Component;
import univ.rouen.backend.DAO.Illustrations;
import univ.rouen.backend.Service.IllustrationsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/illustrations")
@CrossOrigin("*")
public class IllustrationsController {

@Autowired
private IllustrationsService illustrationsService;

@GetMapping
public ResponseEntity<List<Illustrations>> getAllIllustrations() {
List<Illustrations> illustrationsList = illustrationsService.getAllIllustrations();
return new ResponseEntity<>(illustrationsList, HttpStatus.OK);
}

@GetMapping("/{id}")
public ResponseEntity<Illustrations> getIllustrationById(@PathVariable Long id) {
return illustrationsService.getIllustrationById(id)
.map(illustration -> new ResponseEntity<>(illustration, HttpStatus.OK))
.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
}

@PostMapping("/{illustrationId}/addComponent")
public ResponseEntity<Illustrations> addComponentToIllustration(
@PathVariable Long illustrationId,
@RequestBody Component component) {

Illustrations updatedIllustration = illustrationsService.addComponentToIllustration(illustrationId, component);

if (updatedIllustration != null) {
return new ResponseEntity<>(updatedIllustration, HttpStatus.OK);
} else {
return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteIllustration(@PathVariable Long id) {
illustrationsService.deleteIllustration(id);
return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@PostMapping("/addWithImage")
public ResponseEntity<Illustrations> addIllustrationWithImage(
@RequestParam("illustration") String illustrationJson,
@RequestParam("x") double x,
@RequestParam("y") double y,
@RequestParam("image") MultipartFile imageFile) {

try {
ObjectMapper objectMapper = new ObjectMapper();
Illustrations illustration = objectMapper.readValue(illustrationJson, Illustrations.class);

illustration.setX(x);
illustration.setY(y);
illustration.setImage(imageFile.getBytes());

Illustrations savedIllustration = illustrationsService.addIllustration(illustration);
return new ResponseEntity<>(savedIllustration, HttpStatus.OK);

} catch (IOException e) {
e.printStackTrace();
return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
}
}
}
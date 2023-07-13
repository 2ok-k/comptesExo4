package com.test.demo;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class CompteController {
    private final CompteRepository compteRepository;

    @PostMapping("/comptes/upload")
    public String uploadComptes(@RequestBody MultipartFile fichier) throws IOException {
        Reader reader = new InputStreamReader(fichier.getInputStream());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withHeader("username","fullname","email","contact"));

        List<Compte> comptes = new ArrayList<>();

        for (CSVRecord champ : csvParser) {
            String username = champ.get("username");
            String fullname = champ.get("fullname");
            String email = champ.get("email");
            String contact = champ.get("contact");

            Compte compte = new Compte();
            compte.setUsername(username);
            compte.setFullname(fullname);
            compte.setEmail(email);
            compte.setContact(contact);

            comptes.add(compte);
        }

        compteRepository.saveAll(comptes);
        csvParser.close();

        return "Fichier uploader!";
    }
}

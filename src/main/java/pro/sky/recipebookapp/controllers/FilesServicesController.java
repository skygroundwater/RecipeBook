package pro.sky.recipebookapp.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipebookapp.services.fileservices.impl.IngredientsFileServiceImpl;
import pro.sky.recipebookapp.services.fileservices.impl.RecipesFileServiceImpl;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesServicesController {

    public final IngredientsFileServiceImpl ingredientsFileService;

    public final RecipesFileServiceImpl recipesFileService;

    public FilesServicesController(IngredientsFileServiceImpl ingredientsFileService, RecipesFileServiceImpl recipesFileService) {
        this.ingredientsFileService = ingredientsFileService;
        this.recipesFileService = recipesFileService;
    }

    private InputStreamResource getInputStreamFromFileOfService(File file) throws FileNotFoundException {
        if(file.exists()){
           return new InputStreamResource(new FileInputStream(file));
        }
        else return null;
    }

    @GetMapping("/exportrecipes")
    public ResponseEntity<InputStreamResource> downloadIngredientsDataFile() throws FileNotFoundException {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .contentLength(recipesFileService.getDataFile().length()).header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"RecipesLog.json\"").body(getInputStreamFromFileOfService(recipesFileService.getDataFile()));
    }
    @GetMapping("/exportingredients")
    public ResponseEntity<InputStreamResource> downloadRecipesDataFile() throws FileNotFoundException {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .contentLength(ingredientsFileService.getDataFile().length()).header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName = \"IngredientsLog.json\"").body(getInputStreamFromFileOfService(ingredientsFileService.getDataFile()));
    }

    private ResponseEntity<Void> uploadDataFile(String value, MultipartFile multipartFile){
        File dataFile = null;
        if(value.equals("importrecipes")){
            recipesFileService.cleanDataFile();
            dataFile = recipesFileService.getDataFile();
        }else if(value.equals("importingredients")){
            ingredientsFileService.cleanDataFile();
            dataFile = ingredientsFileService.getDataFile();
        }
        try {
            assert dataFile != null;
            try(FileOutputStream fos = new FileOutputStream(dataFile)){
                IOUtils.copy(multipartFile.getInputStream(), fos);
                return ResponseEntity.ok().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value="/importrecipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipesDataFile(@RequestParam MultipartFile file){
       return uploadDataFile("importrecipes", file);
    }

    @PostMapping(value="/importingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsDataFile(@RequestParam MultipartFile file) {
        return uploadDataFile("importingredients", file);
    }
}

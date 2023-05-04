package file.fileSystem.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import file.fileSystem.Service.FileService;



@RestController
@RequiredArgsConstructor
public class FileController {

    @Autowired
    private final FileService fileService;

    @GetMapping(value="path")
    public void saveFile() {
        fileService.saveFile();
    }

    
    



    
}

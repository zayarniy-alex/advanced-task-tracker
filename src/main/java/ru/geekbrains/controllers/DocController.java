package ru.geekbrains.controllers;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.geekbrains.entities.DocFilter;
import ru.geekbrains.entities.Document;
import ru.geekbrains.repositories.DocumentRepository;

import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class DocController {
    private final DocumentRepository documentRepository;
    private DocFilter localFilter;

    @Autowired
    public DocController(DocumentRepository documentRepository) {
        this.documentRepository=documentRepository;
    }

    @GetMapping(value="/documents")
    public String docsPage(Model model,
                           @RequestParam("objId") Long idObject, @RequestParam("objType") String typeObject,@RequestParam("mode") String mode)
    {
        model.addAttribute("activePage", "Documents");
        model.addAttribute("objId",idObject);
        model.addAttribute("objType",typeObject);
        model.addAttribute("docs", documentRepository.findByObject(typeObject,idObject));

        localFilter=new DocFilter(idObject,typeObject);

        return "documents";
    }

    @GetMapping(value="/documents/filter")
    public String docsFilter(Model model,
                             @ModelAttribute("filter") DocFilter filter
    )
    {
        model.addAttribute("activePage", "Documents");
        filter.setTypeObject(localFilter.getTypeObject());
        filter.setIdObject(localFilter.getIdObject());
        if (filter.getDescription().isEmpty())
            filter.setDescription(null);
        if (filter.getName().isEmpty())
            filter.setName(null);
        model.addAttribute("objId",filter.getIdObject());
        model.addAttribute("objType",filter.getTypeObject());
        model.addAttribute("docs", documentRepository.findByObjectFilter(filter.getTypeObject(),filter.getIdObject(),filter.getName(),filter.getDescription(),filter.getId()));
        return "documents";
    }

    @GetMapping(value="/document/create")
    public String createDocument(Model model, @RequestParam("objId") Long idObject, @RequestParam("objType")  String typeObject) {
        model.addAttribute("create", true);
        model.addAttribute("activePage", "Documents");
        model.addAttribute("objId",idObject);
        model.addAttribute("objType",typeObject);
        model.addAttribute("document", new Document());
        return "document";
    }

    @GetMapping(value="/document/delete")
    public String deleteDocument(Model model, @RequestParam("id") Long id) {
        model.addAttribute("activePage", "Documents");
        Document document=documentRepository.findById(id).get();
        documentRepository.deleteById(id);
        return "redirect:/documents?objId="+document.getObject_id()+"&objType="+document.getObject_type()+"&mode=EDIT";
    }

    @GetMapping(value="/document/open")
    public ResponseEntity<byte[]> openDocument(Model model, @RequestParam("id") Long id) {
        model.addAttribute("activePage", "Documents");
        Document document=documentRepository.findById(id).get();
        byte[] data=document.getData();
        try(FileOutputStream fos=new FileOutputStream(document.getTitle()))
        {
            fos.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ext= FilenameUtils.getExtension(document.getTitle());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/"+ext));
        String filename = document.getTitle();
        headers.add("content-disposition", "inline;filename=" + filename);
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(document.getData(), headers, HttpStatus.OK);
        return response;
    }

    @PostMapping("/document/save")
    public String saveDocument(Model model, RedirectAttributes redirectAttributes, Document document, @RequestParam("fileData") MultipartFile file) throws IOException {
        model.addAttribute("activePage", "Documents");
        model.addAttribute("objId",document.getObject_id());
        model.addAttribute("objType",document.getObject_type());
        model.addAttribute("mode","EDIT");

        document.setTitle(file.getOriginalFilename());
        document.setData(file.getBytes());

        try {
            documentRepository.save(document);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", true);
            if (document.getId() == null) {
                return "redirect:/document/create";
            }
        }
        return "redirect:/documents?objId="+document.getObject_id()+"&objType="+document.getObject_type()+"&mode=EDIT";
    }

}

package com.example.cosmetology.controllers;

import com.example.cosmetology.models.AboutTheSalon;
import com.example.cosmetology.models.AddressOfTheSalon;
import com.example.cosmetology.models.Articles;
import com.example.cosmetology.repository.AboutTheSalonRepo;
import com.example.cosmetology.repository.AddressOfTheSalonRepo;
import com.example.cosmetology.repository.ArticlesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexControllers {
    @Autowired
    AddressOfTheSalonRepo addressOfTheSalonRepo;
    @Autowired
    AboutTheSalonRepo aboutTheSalonRepo;
    @Autowired
    ArticlesRepo articlesRepo;

    @GetMapping("/")
    public String Index(Model model){
        List<AddressOfTheSalon> address = addressOfTheSalonRepo.findAll();
        model.addAttribute("address", address);

        List<AboutTheSalon> aboutTheSalons = aboutTheSalonRepo.findAll();
        model.addAttribute("aboutTheSalon", aboutTheSalons);

        List<Articles> articles = articlesRepo.findAll();
        model.addAttribute("articles", articles);
        return "index/index";
    }

    @GetMapping("/articles/{id}")
    public String fullArticles(@PathVariable(value = "id") long id, Model model){
        Articles articles = articlesRepo.findById(id).orElse(new Articles());
        model.addAttribute("fullarticles", articles);
        return "index/full-articles";
    }

    @PostMapping("/articles/{id}/delete")
    public String deleteArticles(@PathVariable(value = "id") long id){
        articlesRepo.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}/update")
    public String articlesUpdate(@PathVariable(value = "id") long id,
                                 Model model,
                                 @RequestParam(value = "error", defaultValue = "", required = false) String error){
        if (error.equals("null")) {
            model.addAttribute("error", "Заполните все поля!");
        } else if (error.equals("max")) {
            model.addAttribute("error", "Какое-то из полей слишком маленькое");
        }
        Articles articlesUpdate = articlesRepo.findById(id).orElse(new Articles());
        model.addAttribute("update", articlesUpdate);
        return "index/articles-update";
    }

    @PostMapping("/articles/{id}/update/add")
    public String adticlesUpdateAdd(@PathVariable(value = "id") long id,
                                    @RequestParam String name,
                                    @RequestParam String date,
                                    @RequestParam String img,
                                    @RequestParam String description){
        if (name.equals("") || date.equals("") || img.equals("") || description.equals("")){
            return "redirect:/articles/{id}/update?error=null";
        } else if (name.length() >= 250 || date.length() >= 250 || img.length() >= 250 || description.length() >= 2000) {
            return "redirect:/articles/{id}/update?error=max";
        } else {
            Articles articlesUpdate = articlesRepo.findById(id).orElse(new Articles());
            articlesUpdate.setName(name);
            articlesUpdate.setDate(date);
            articlesUpdate.setImg(img);
            articlesUpdate.setDescription(description);
            articlesRepo.save(articlesUpdate);
        }
        return "redirect:/";
    }

}

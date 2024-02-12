package com.example.cosmetology.Index.Controller;

import com.example.cosmetology.Index.Model.Services;
import com.example.cosmetology.expired_product.service.impl.ExpiredProductImpl;
import com.example.cosmetology.Index.Model.AboutTheSalon;
import com.example.cosmetology.Index.Model.AddressOfTheSalon;
import com.example.cosmetology.Index.Model.Articles;
import com.example.cosmetology.repository.AboutTheSalonRepo;
import com.example.cosmetology.repository.AddressOfTheSalonRepo;
import com.example.cosmetology.repository.ArticlesRepo;
import com.example.cosmetology.repository.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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
    @Autowired
    ServiceRepo serviceRepo;

    @GetMapping("/")
    public String Index(Model model){
        List<AddressOfTheSalon> address = addressOfTheSalonRepo.findAll();
        model.addAttribute("address", address);

        List<AboutTheSalon> aboutTheSalons = aboutTheSalonRepo.findAll();
        model.addAttribute("aboutTheSalon", aboutTheSalons);

        List<Articles> articles = articlesRepo.findAll();
        model.addAttribute("articles", articles);

        List<Services> services = serviceRepo.findAll();
        model.addAttribute("services", services);

        ExpiredProductImpl expiredProduct = new ExpiredProductImpl();
        if (!expiredProduct.SelectOrdersBoolean()){
            model.addAttribute("ordersInfo", "Есть продаваемый товар, у которого срок годности меньше 6 месяцев");
        } if (!expiredProduct.SelectPersonalOrdersBoolean()){
            model.addAttribute("ordersPersonalInfo", "Есть личный товар, у которого срок годности меньше 6 месяцев");
        }
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

    @GetMapping("/updateindex")
    public String updateIndex(Model model, @RequestParam(value = "error",defaultValue = "", required = false) String error){
        AboutTheSalon aboutTheSalons = aboutTheSalonRepo.findById(1L).orElse(new AboutTheSalon());
        model.addAttribute("aboutTheSalon",aboutTheSalons);

        AddressOfTheSalon addressOfTheSalons = addressOfTheSalonRepo.findById(1L).orElse(new AddressOfTheSalon());
        model.addAttribute("addresofthesalon", addressOfTheSalons);

        List<Services> services = serviceRepo.findAll();
        model.addAttribute("services", services);

        if (error.equals("null")){
            model.addAttribute("error", "Заполните все поля!");
        } else if (error.equals("max")){
            model.addAttribute("error", "Какое-то поле слишком длинное!");
        } else if (error.equals("serviceMin")){
            model.addAttribute("error2", "Заполните все поля!");
        } else if (error.equals("serviceMax")){
            model.addAttribute("error2", "Какое-то поле слишком длинное!");
        }


        return "index/update-index";
    }

    @PostMapping("/updateindex/add")
    public String updateIndexAdd(@RequestParam String activity,
                                 @RequestParam String history,
                                 @RequestParam String img,
                                 @RequestParam String address){
        if (activity.equals("") || history.equals("") || img.equals("") || address.equals("")){
            return "redirect:/updateindex?error=null";
        } else if (activity.length() >= 5000 || history. length() >= 5000 || img.length() >= 250 || address.length() >= 250) {
            return "redirect:/updateindex?error=max";
        } else{
            AboutTheSalon aboutTheSalon = aboutTheSalonRepo.findById(1L).orElse(new AboutTheSalon());
            aboutTheSalon.setActivity(activity);
            aboutTheSalon.setHistory(history);
            aboutTheSalonRepo.save(aboutTheSalon);

            AddressOfTheSalon addressOfTheSalon = addressOfTheSalonRepo.findById(1L).orElse(new AddressOfTheSalon());
            addressOfTheSalon.setImg(img);
            addressOfTheSalon.setAddress(address);
            addressOfTheSalonRepo.save(addressOfTheSalon);
        }
        return "redirect:/updateindex";
    }

    @PostMapping("/updateindex/service/add")
    public String addService(@RequestParam String img,
                             @RequestParam String name,
                             @RequestParam String description){
        if (img.length() <= 2 || name.length() <=2 || description.length() <= 2){
            return "redirect:/updateindex?error=serviceMin";
        } else if (img.length() >= 250 || name.length() >=250 || description.length() >= 250){
            return "redirect:/updateindex?error=serviceMax";
        }
        Services services = new Services(img,name,description);
        serviceRepo.save(services);
        return "redirect:/updateindex";
    }

    @PostMapping("/updateindex/service/delete{id}")
    public String serviceDelete(@PathVariable(value="id") Long id){
        serviceRepo.deleteById(id);
        return "redirect:/updateindex";
    }

}

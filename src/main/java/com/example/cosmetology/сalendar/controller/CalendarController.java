package com.example.cosmetology.сalendar.controller;

import com.example.cosmetology.сalendar.model.Calendar;
import com.example.cosmetology.сalendar.service.impl.CalendarServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class CalendarController {
    private CalendarServiceImpl calendarService = new CalendarServiceImpl();
    private List<Calendar> calendarDate = new ArrayList<>();
    private List<Calendar> nextDay = new ArrayList<>();
    private List<Calendar> previousDay = new ArrayList<>();
    @GetMapping("/calendar/add")
    public String calendarAdd(@RequestParam(value = "error",defaultValue = "",required = false) String error, Model model){
        if (error.equals("min")){
            model.addAttribute("error", "Заполните все поля!");
        }
        return "calendar/calendar-add";
    }
    @PostMapping("/calendar/add")
    public String calendarAdd(@RequestParam String firstname,
                                  @RequestParam String name,
                                  @RequestParam String lastname,
                                  @RequestParam String number,
                                  @RequestParam String date,
                                  @RequestParam String time){
        if (firstname.equals("") || name.equals("") || date.equals("") || time.equals("")){
            return "redirect:/calendar/add?error=min";
        }
        calendarService.calendarAdd(firstname, name,lastname, number, date, time);
        return "redirect:/";
    }

    @GetMapping("/calendar/date")
    public String calendarSerachDate(@RequestParam(value = "error",defaultValue = "",required = false) String error, Model model){
        if (error.equals("min")){
            model.addAttribute("error", "Заполните все поля!");
        }
        return "calendar/calendar-search-date";
    }
    @PostMapping("/calendar/date")
    public String CalendarSelectDate(@RequestParam String date){
        if (date.length() <= 5){
            return "redirect:/calendar/date?error=min";
        }
        calendarDate = calendarService.caledarSelectDate(date);
        return "redirect:/calendar/select/date";
    }
    @GetMapping("/calendar/select/date")
    public String calendarDate(Model model){
        Collections.sort(calendarDate);
        model.addAttribute("calendarDate", calendarDate);
        return "calendar/calendar";
    }

    @GetMapping("/calendar/name")
    public String calendarSearchName(@RequestParam(value = "error",defaultValue = "",required = false) String error, Model model){
        if (error.equals("min")){
            model.addAttribute("error", "Заполните все поля!");
        }
        return "calendar/calendar-search-name";
    }

    @PostMapping("/calendar/name")
    public String CalendarSelectName(@RequestParam String firstname,
                                     @RequestParam String name){
        if (firstname.equals("") || name.equals("")){
            return "redirect:/calendar/name?error=min";
        }
        calendarDate = calendarService.caledarSelectName(firstname, name);
        return "redirect:/calendar/select/name";
    }

    @GetMapping("/calendar/select/name")
    public String calendarName(Model model){
        Collections.sort(calendarDate);
        Collections.reverse(calendarDate);
        model.addAttribute("calendarDate", calendarDate);
        return "calendar/calendar";
    }

    @PostMapping("/calendar/delete/{id}")
    public String calendarDelete(@PathVariable(value = "id") long id){
        calendarService.calendarDelete(id);
        return "redirect:/";
    }

   @PostMapping("/calendar/next/{date}")
    public String calendarNext(@PathVariable(value = "date") String date){
       nextDay = calendarService.nextDate(date);
       return "redirect:/calendar/next";
   }

   @GetMapping("/calendar/next")
    public String calendarNext(Model model){
       Collections.sort(nextDay);
       model.addAttribute("calendarDate", nextDay);
       return "calendar/calendar";
   }

    @PostMapping("/calendar/back/{date}")
    public String calendarBack(@PathVariable(value = "date") String date){
        previousDay = calendarService.backDate(date);
        return "redirect:/calendar/back";
    }

    @GetMapping("/calendar/back")
    public String calendarBack(Model model){
        Collections.sort(previousDay);
        model.addAttribute("calendarDate", previousDay);
        return "calendar/calendar";
    }
}

package com.example.project_jjol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.project_jjol.model.Notice;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.NoticeService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public String listNotices(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
        }
        model.addAttribute("notices", noticeService.findAll());
        return "views/notice_list";
    }

    @GetMapping("/{id}")
    public String viewNotice(@PathVariable("id") Long id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
        }
        model.addAttribute("notice", noticeService.findById(id));
        return "views/notice_view";
    }

    @GetMapping("/create")
    public String createNoticeForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("notice", new Notice());
        return "views/notice_form";
    }

    @PostMapping("/create")
    public String createNotice(@ModelAttribute Notice notice, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        notice.setUserId(loggedInUser.getUserId());
        noticeService.save(notice);
        return "redirect:/notices";
    }

    @GetMapping("/edit/{id}")
    public String editNoticeForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("notice", noticeService.findById(id));
        return "views/notice_form";
    }

    @PostMapping("/edit/{id}")
    public String editNotice(@PathVariable("id") Long id, @ModelAttribute Notice notice) {
        notice.setId(id);
        noticeService.update(notice);
        return "redirect:/notices";
    }

    @PostMapping("/delete/{id}")
    public String deleteNotice(@PathVariable("id") Long id) {
        noticeService.delete(id);
        return "redirect:/notices";
    }
}

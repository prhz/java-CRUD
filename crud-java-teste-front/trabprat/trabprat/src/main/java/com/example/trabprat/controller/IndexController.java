package com.example.trabprat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.trabprat.model.Funcionario;
import com.example.trabprat.model.Search;
import com.example.trabprat.model.Sistema;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/rootRedirect")
    public String redirectToRoot() {
        return "redirect:/";
    }

    @GetMapping("/addRedirect")
    public String redirectToAdd() {
        return "redirect:/add";
    }

    @GetMapping("/searchRedirect")
    public String redirectToSearch() {
        return "redirect:/search";
    }

    @GetMapping("/updateRedirect")
    public String updateRedirect() {
        if (Sistema.search) {
            Sistema.search = false;
            return "redirect:/update";
        } else {
            return "redirect:/search";
        }
    }

    @GetMapping("/deleteRedirect")
    public String deleteRedirect() {
        if (Sistema.search) {
            Sistema.search = false;
            return "redirect:/delete";
        } else {
            return "redirect:/search";
        }
    }

    @GetMapping("/delete")
    public String deletePage() {
        Sistema.RemoveFuncionario(Sistema.currentFuncionarioId);
        return "redirect:/";
    }

    @GetMapping("/result")
    public String result(@ModelAttribute Search search, Model model) {
        Sistema.search = false;
        Funcionario funcionario = Sistema.getFuncionario(search.getStr());
        if (funcionario != null) {
            Sistema.currentFuncionarioId = funcionario.getId();
            Sistema.search = true;
            model.addAttribute("search_bool", "true");
            model.addAttribute("nome", funcionario.getNome());
            model.addAttribute("idade", funcionario.getIdade());
            model.addAttribute("email", funcionario.getEmail());
            model.addAttribute("senha", funcionario.getSenha());
            model.addAttribute("id", funcionario.getId());
        } else {
            model.addAttribute("search_bool", "false");
        }
        return "search";
    }

    @GetMapping("/search")
    public String userSearch() {
        return "search";
    }

    @GetMapping("/add")
    public String addPage() {
        return "add";
    }

    @GetMapping("/update")
    public String replacePage(Model model) {
        model.addAttribute("nome", Sistema.getFuncionario(Sistema.currentFuncionarioId).getNome());
        model.addAttribute("email", Sistema.getFuncionario(Sistema.currentFuncionarioId).getEmail());
        model.addAttribute("idade", Sistema.getFuncionario(Sistema.currentFuncionarioId).getIdade());
        model.addAttribute("senha", Sistema.getFuncionario(Sistema.currentFuncionarioId).getSenha());
        return "update";
    }

    @GetMapping("/updateFunc")
    public String updateFunc(@ModelAttribute Funcionario f, Model model) {
        if (f != null) {
            Sistema.getFuncionario(Sistema.currentFuncionarioId).setNome(f.getNome());
            Sistema.getFuncionario(Sistema.currentFuncionarioId).setEmail(f.getEmail());
            Sistema.getFuncionario(Sistema.currentFuncionarioId).setIdade(f.getIdade());
            Sistema.getFuncionario(Sistema.currentFuncionarioId).setSenha(f.getSenha());
        }
        return "index";

    }

    @PostMapping("/addFunc")
    public String addFunc(@ModelAttribute Funcionario f, Model model) {
        Sistema.addFuncionario(f);
        return "redirect:/";
    }
}

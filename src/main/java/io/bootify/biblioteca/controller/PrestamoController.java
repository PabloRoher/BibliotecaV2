package io.bootify.biblioteca.controller;

import io.bootify.biblioteca.domain.Lector;
import io.bootify.biblioteca.domain.Libro;
import io.bootify.biblioteca.model.EstadoPrestamo;
import io.bootify.biblioteca.model.PrestamoDTO;
import io.bootify.biblioteca.repos.LectorRepository;
import io.bootify.biblioteca.repos.LibroRepository;
import io.bootify.biblioteca.service.PrestamoService;
import io.bootify.biblioteca.util.CustomCollectors;
import io.bootify.biblioteca.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoController(final PrestamoService prestamoService,
            final LectorRepository lectorRepository, final LibroRepository libroRepository) {
        this.prestamoService = prestamoService;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("estadoPrestamoValues", EstadoPrestamo.values());
        model.addAttribute("lectorIDValues", lectorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Lector::getId, Lector::getNombre)));
        model.addAttribute("lectorValues", lectorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Lector::getId, Lector::getNombre)));
        model.addAttribute("libroValues", libroRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Libro::getId, Libro::getTitulo)));
        model.addAttribute("libroAsociadoValues", libroRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Libro::getId, Libro::getTitulo)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("prestamoes", prestamoService.findAll());
        return "prestamo/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("prestamo") final PrestamoDTO prestamoDTO) {
        return "prestamo/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("prestamo") @Valid final PrestamoDTO prestamoDTO,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "prestamo/add";
        }
        try {
            prestamoService.create(prestamoDTO);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("prestamo.create.success"));
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, e.getMessage());
            return "redirect:/prestamos/add";
        }
        return "redirect:/prestamos";
    }


    @GetMapping("/edit/{idPrestamo}")
    public String edit(@PathVariable final Long idPrestamo, final Model model) {
        model.addAttribute("prestamo", prestamoService.get(idPrestamo));
        return "prestamo/edit";
    }

    @PostMapping("/edit/{idPrestamo}")
    public String edit(@PathVariable final Long idPrestamo,
            @ModelAttribute("prestamo") @Valid final PrestamoDTO prestamoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "prestamo/edit";
        }
        prestamoService.update(idPrestamo, prestamoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("prestamo.update.success"));
        return "redirect:/prestamos";
    }

    @PostMapping("/delete/{idPrestamo}")
    public String delete(@PathVariable final Long idPrestamo,
            final RedirectAttributes redirectAttributes) {
        prestamoService.delete(idPrestamo);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("prestamo.delete.success"));
        return "redirect:/prestamos";
    }

}

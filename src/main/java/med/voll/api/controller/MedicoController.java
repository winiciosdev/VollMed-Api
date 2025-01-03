package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados){
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedicos> listar(@PageableDefault(size = 10, sort = {"Nome"}) Pageable paginacao){
        return  repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicos::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid AtualizarDadosMedico dados){

        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){

        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}

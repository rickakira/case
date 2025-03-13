package br.com.itau.pix.controller;

import br.com.itau.pix.converters.interfaces.Utils;
import br.com.itau.pix.dtos.PessoaDTO;
import br.com.itau.pix.dtos.PessoaPatchDTO;
import br.com.itau.pix.services.ChaveService;
import br.com.itau.pix.services.PessoaService;
import br.com.itau.pix.dtos.PessoaSearch;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController implements Utils {

    private final PessoaService service;
    private final ChaveService chaveService;

    @PostMapping(value = "/fisica")
    public ResponseEntity<UUID> savePF(@RequestBody PessoaDTO dto) {
       var id = service.savePF(dto);

       return ResponseEntity.ok(id);
    }

    @PostMapping("/juridica")
    public ResponseEntity<UUID> savePJ(@RequestBody PessoaDTO dto) {
        var id = service.savePJ(dto);

        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> update(@PathVariable String id,
                                            @RequestBody PessoaPatchDTO dto) {
        var pessoaDTO = service.update(id, dto);

        return ResponseEntity.of(pessoaDTO);
    }

    @GetMapping
    @SuppressWarnings("rawtypes")
    public ResponseEntity getBy(@RequestParam(required = false) String id,
                                             @RequestParam(required = false) String tipoChave,
                                             @RequestParam(required = false) Integer agencia,
                                             @RequestParam(required = false) Integer conta,
                                             @RequestParam(required = false) String nomeCorrentista,
                                             @RequestParam(required = false) String dataInclusao,
                                             @RequestParam(required = false) String dataInativacao) {

            var pessoaSearch = new PessoaSearch(id,
                    tipoChave, agencia, conta, nomeCorrentista,
                    parseData(dataInclusao),
                    parseData(dataInativacao));

            if (StringUtils.isNotEmpty(pessoaSearch.id())) {
                return ResponseEntity.ok(service.getById(pessoaSearch));
            }

            var pessoaDtos = service.getBy(pessoaSearch);

            return ResponseEntity.ofNullable(pessoaDtos);
    }
}

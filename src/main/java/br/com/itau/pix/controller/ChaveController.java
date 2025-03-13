package br.com.itau.pix.controller;

import br.com.itau.pix.converters.interfaces.Utils;
import br.com.itau.pix.dtos.ChaveDTO;
import br.com.itau.pix.enums.TipoChave;
import br.com.itau.pix.services.ChaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chaves")
@RequiredArgsConstructor
public class ChaveController implements Utils {

    private final ChaveService service;

    @GetMapping("/pessoa/{idPessoa}")
    public ResponseEntity<List<ChaveDTO>> getAllBy(@PathVariable String idPessoa) {
        var dtos = service.getChavesBy(idPessoa);

        return ResponseEntity.ofNullable(dtos);
    }

    @PostMapping("/aleatorio/pessoa/{idPessoa}/chave/{chave}")
    public ResponseEntity<UUID> saveAleatorio(@PathVariable String idPessoa,
                                              @PathVariable String chave) {

        ChaveDTO dto = of(chave, TipoChave.ALEATORIO);

        var id = service.save(idPessoa, dto);

        return ResponseEntity.ok(id);
    }

    @PostMapping("celular/pessoa/{idPessoa}")
    public ResponseEntity<UUID> saveCelular(@PathVariable String idPessoa,
                                            @RequestParam String chave) {

        ChaveDTO dto = of(chave, TipoChave.CELULAR);
        var id = service.save(idPessoa, dto);

        return ResponseEntity.ok(id);
    }

    @PostMapping("cnpj/pessoa/{idPessoa}/chave/{chave}")
    public ResponseEntity<UUID> saveCNPJ(@PathVariable String idPessoa,
                                         @PathVariable String chave) {

        ChaveDTO dto = of(chave, TipoChave.CNPJ);
        var id = service.save(idPessoa, dto);

        return ResponseEntity.ok(id);
    }

    @PostMapping("cpf/pessoa/{idPessoa}/chave/{chave}")
    public ResponseEntity<UUID> saveCPF(@PathVariable String idPessoa,
                                        @PathVariable String chave) {

        ChaveDTO dto = of(chave, TipoChave.CPF);
        var id = service.save(idPessoa, dto);

        return ResponseEntity.ok(id);
    }

    @PostMapping("email/pessoa/{idPessoa}/chave/{chave}")
    public ResponseEntity<UUID> saveEmail(@PathVariable String idPessoa,
                                          @PathVariable String chave) {

        ChaveDTO dto = of(chave, TipoChave.EMAIL);
        var id = service.save(idPessoa, dto);

        return ResponseEntity.ok(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ChaveDTO> delete(@PathVariable String id) {

        var chaveDTO = service.delete(id);
        return ResponseEntity.of(chaveDTO);
    }
}

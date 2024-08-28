package br.edu.ifpb.pweb2.talenthub.utils.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidaturaDTO {
    private String nomeAluno;
    private Long alunoID;
    
    private String nomeEmpresa;
    private Long empresaID;

    private String nomeOferta;
    private Long ofertaID;
}

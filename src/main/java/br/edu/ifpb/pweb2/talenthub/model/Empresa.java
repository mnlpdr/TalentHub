package br.edu.ifpb.pweb2.talenthub.model;

import jakarta.persistence.*;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String cnpj;

    private String endereco;

    private String telefone;

    private String email;

    private String atividadePrincipal;

    private String pessoaContato;

    private String url;

    @Lob
//    @Column(name = "documento_endereco", columnDefinition = "BYTEA")
    @Column(name = "documento_endereco")
    private byte[] documentoEndereco;

    @OneToMany(mappedBy = "empresa")
    private Set<Oferta> ofertas;

    //FOI ESSE TRANSIENT QUE RESOLVEU MAIS DE 2 HORAS DE DOR DE CABEÇA!!!
    // Transient annotation prevents the field from being persisted directly
    @Transient
    private MultipartFile documentoEnderecoFile;
}

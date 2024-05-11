package com.myfood.myfoodback.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;

@Entity
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private double preco;
    private String imagem;
    private boolean visibilidadeAvaliacao;

    @OneToMany(mappedBy = "produtos", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoesId;

    public Produtos() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return this.preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return this.imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public boolean isVisibilidadeAvaliacao() {
        return this.visibilidadeAvaliacao;
    }

    public boolean getVisibilidadeAvaliacao() {
        return this.visibilidadeAvaliacao;
    }

    public void setVisibilidadeAvaliacao(boolean visibilidadeAvaliacao) {
        this.visibilidadeAvaliacao = visibilidadeAvaliacao;
    }

    public List<Avaliacao> getAvaliacoesId() {
        return this.avaliacoesId;
    }

    public void setAvaliacoesId(List<Avaliacao> avaliacoes) {
        this.avaliacoesId = avaliacoes;
    }

}

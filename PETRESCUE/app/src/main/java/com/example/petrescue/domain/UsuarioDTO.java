package com.example.petrescue.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.petrescue.domain.enums.TipoUsuario;
import com.example.petrescue.domain.subClasses.Localizacao;

public class UsuarioDTO implements Parcelable {

    private Integer id;
    private Double saldo;
    private String email;
    private String senha;
    private String nome;
    private String foto;
    private Localizacao localizacao;

    private TipoUsuario tipoUsuario;

    private String nomeOng;
    private String cpfCnpj;
    private String descricao;

    public UsuarioDTO(Integer id, Double saldo, String email, String senha, String nome, String foto, Localizacao localizacao, TipoUsuario tipoUsuario, String nomeOng, String cpfCnpj, String descricao) {
        this.id = id;
        this.saldo = saldo;
        this.email = email;
        this.senha=senha;
        this.nome = nome;
        this.foto = foto;
        this.localizacao = localizacao;
        this.tipoUsuario = tipoUsuario;
        this.nomeOng = nomeOng;
        this.cpfCnpj = cpfCnpj;
        this.descricao = descricao;
    }

    public UsuarioDTO() {
    }

    protected UsuarioDTO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            saldo = null;
        } else {
            saldo = in.readDouble();
        }
        email = in.readString();
        senha = in.readString();
        nome = in.readString();
        foto = in.readString();
        nomeOng = in.readString();
        cpfCnpj = in.readString();
        descricao = in.readString();
    }

    public static final Creator<UsuarioDTO> CREATOR = new Creator<UsuarioDTO>() {
        @Override
        public UsuarioDTO createFromParcel(Parcel in) {
            return new UsuarioDTO(in);
        }

        @Override
        public UsuarioDTO[] newArray(int size) {
            return new UsuarioDTO[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNomeOng() {
        return nomeOng;
    }

    public void setNomeOng(String nomeOng) {
        this.nomeOng = nomeOng;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (saldo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(saldo);
        }
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeString(nome);
        dest.writeString(foto);
        dest.writeString(nomeOng);
        dest.writeString(cpfCnpj);
        dest.writeString(descricao);
    }
}

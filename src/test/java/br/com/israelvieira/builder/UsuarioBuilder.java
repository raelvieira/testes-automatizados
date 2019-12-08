package br.com.israelvieira.builder;

import br.com.israelvieira.modelo.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;

    private static int contadorID = 1;

    private UsuarioBuilder() { }

    public static UsuarioBuilder umUsuario() {
        UsuarioBuilder builder = new UsuarioBuilder();
        builder.usuario = new Usuario();
        builder.usuario.setNome("Usuario " + Integer.toString(contadorID));
        builder.usuario.setId(contadorID++ );
        builder.usuario.setMatricula("M403");
        return builder;
    }

    public UsuarioBuilder comNome(String nome) {
        usuario.setNome(nome);
        return this;
    }

    public Usuario constroi(){
        return usuario;
    }
}

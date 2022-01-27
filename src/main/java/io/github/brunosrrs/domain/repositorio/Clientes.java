package io.github.brunosrrs.domain.repositorio;

import io.github.brunosrrs.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

//classe que vai realizar ações com o banco, por isso utilização do @Repository
@Repository
public class Clientes {

    //criação de script sql
    private static String INSERT = "insert into cliente (nome) values (?) ";
    private static String SELECT_ALL = "SELECT * FROM CLIENTE ";
    private static String UPDATE = "update cliente set nome = ? where id = ? ";
    private static String DELETE = "delete from cliente where id = ? ";

    //classe JPA que permite executar as ações no banco,e linkar a classe c o banco, o @AutoWired para injeção no projeto
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //cria um cliente no banco
    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update(INSERT, cliente.getNome());
        return cliente;
    }

    //atualiza um cliente no banco
    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(UPDATE, cliente.getNome(), cliente.getId());
        return cliente;
    }

    //método para obtençao do id
    public void deletar(Cliente cliente){
        deletar(cliente.getId());
    }
    //pega o id do método de cima de executa o sql de delete;
    public void deletar (Integer id){
        jdbcTemplate.update(DELETE, id);
    }

    //pesquisa do cliente por nome
    public List<Cliente> buscarPorNome(String nome){
        return jdbcTemplate.query(SELECT_ALL.concat("where nome like ?"),
                getClienteRowMapper(),
                "%" + nome + "%");
    }

    //método para realização de um select all
    public List<Cliente> obterTodos(){
        return jdbcTemplate.query(SELECT_ALL, getClienteRowMapper());
    }

    //método row mapper que retorna uma lista com todas as colunas da tabela cliente, ou seja a tabela toda
    private RowMapper<Cliente> getClienteRowMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt("id");
                String nome = rs.getString("nome");
                return new Cliente(id, nome);
            }
        };
    }

}

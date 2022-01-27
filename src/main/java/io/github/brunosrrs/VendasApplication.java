package io.github.brunosrrs;

import io.github.brunosrrs.domain.entity.Cliente;
import io.github.brunosrrs.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication
@RestController
public class VendasApplication {

    //executa após inicio da aplicação esse bloco de código abaixo
    @Bean
    public CommandLineRunner init (@Autowired Clientes clientes){
        return args -> {
            System.out.println("Criando clientes");
            clientes.salvar(new Cliente("Bruno"));
            clientes.salvar(new Cliente("Marcelino"));

            List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes");
            todosClientes.forEach(c ->{
                c.setNome(c.getNome() + " atualizado.");
                clientes.atualizar(c);
            });

            todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando clientes");
            clientes.buscarPorNome("ce").forEach(System.out::println);

            /*System.out.println("Deletando clientes");
            clientes.obterTodos().forEach(c ->{
                clientes.deletar(c);
            });*/

            todosClientes = clientes.obterTodos();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhuma cliente encontrado");
            } else {
                todosClientes.forEach(System.out::println);
            }

        };
    }

    //inicio da aplicação spring
    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class, args);
    }
}

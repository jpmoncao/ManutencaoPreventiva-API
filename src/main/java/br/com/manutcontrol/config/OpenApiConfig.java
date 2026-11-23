package br.com.manutcontrol.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("ManutControl API")
                        .version("1.0.0")
                        .description("""
                                API REST de manutenção preventiva industrial.

                                Centraliza máquinas, planos preventivos, técnicos e execuções para que
                                diferentes aplicações consultem vencimentos, histórico e cadastros.

                                **Regras de negócio principais**
                                - Código de máquina e matrícula de técnico são únicos
                                - Periodicidade do plano deve ser de pelo menos 1 dia
                                - Execução só é aceita em máquina ATIVA, com data não futura
                                - A próxima data do plano é recalculada: data da execução + periodicidade
                                - Máquina com planos e plano com histórico não podem ser excluídos

                                **Como testar**
                                Cadastre na ordem: máquina → técnico → plano → execução.
                                """))
                .servers(List.of(new Server().url("http://localhost:8080").description("Ambiente local")))
                .tags(List.of(
                        new Tag().name("Máquinas").description("Cadastro e consulta de equipamentos industriais"),
                        new Tag().name("Planos").description("Planos preventivos, vencidos e próximos vencimentos"),
                        new Tag().name("Técnicos").description("Cadastro de técnicos responsáveis pelas execuções"),
                        new Tag().name("Execuções").description("Registro e histórico de manutenções realizadas")
                ));
    }
}

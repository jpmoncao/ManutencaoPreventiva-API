# ManutencaoPreventiva-API — Manutenção Preventiva Industrial

API REST desenvolvida em Java 21, Spring Boot 3.5.7, Spring Data JPA e Swagger/OpenAPI. Projeto acadêmico de Arquitetura Orientada a Serviços.

## Problema
Muitas organizações enfrentam dificuldades no gerenciamento da manutenção preventiva devido à falta de controles padronizados e indicadores de acompanhamento. Essa deficiência contribui para falhas inesperadas em máquinas e equipamentos, ocasionando interrupções na produção, aumento dos custos de manutenção corretiva e redução da eficiência operacional.

## Como executar (Windows ou Linux)

**Requisitos:** Java JDK 21 e Maven 3.9+ instalados, com acesso à internet no primeiro uso para baixar dependências.

1. Extraia o ZIP.
2. Abra o terminal **dentro da pasta `ManutencaoPreventiva-API`** (onde está `pom.xml`).
3. Execute `mvn spring-boot:run` (ou execute `iniciar-windows.bat` no Windows).
4. Abra http://localhost:8080/swagger-ui.html
5. Teste os endpoints na interface do Swagger, na ordem máquinas, técnicos, planos e execuções.

**Sem PostgreSQL:** o projeto inicia com **H2**, banco relacional persistido no arquivo `./manutcontrol-db.mv.db`. Os registros continuam após reiniciar a API. Console opcional: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:file:./manutcontrol-db;DB_CLOSE_ON_EXIT=FALSE`, usuário `sa`, senha vazia).

**Com PostgreSQL (opcional):** execute `docker compose up -d` (exige Docker), depois `mvn spring-boot:run -Dspring-boot.run.profiles=postgres`. Valores padrão no `application-postgres.properties`; use variáveis `DB_URL`, `DB_USER`, `DB_PASSWORD` para personalizar. O perfil muda a conexão, sem migrar os dados anteriores do H2.

## 15 endpoints
| Método | Endereço | Função |
| --- | --- | --- |
| POST | /api/maquinas | Cadastra máquina |
| GET | /api/maquinas | Lista máquinas |
| GET | /api/maquinas/{id} | Consulta máquina |
| PUT | /api/maquinas/{id} | Atualiza máquina |
| DELETE | /api/maquinas/{id} | Exclui máquina sem planos |
| POST | /api/planos | Cadastra plano |
| GET | /api/planos | Lista planos |
| GET | /api/planos/{id} | Consulta plano |
| PUT | /api/planos/{id} | Atualiza plano |
| DELETE | /api/planos/{id} | Exclui plano sem execuções |
| GET | /api/planos/vencidos | Lista planos vencidos |
| GET | /api/planos/proximos?dias=7 | Lista vencimentos de hoje até N dias |
| POST | /api/tecnicos | Cadastra técnico |
| POST | /api/execucoes | Registra execução |
| GET | /api/execucoes | Lista histórico |

## Exemplos para usar no Swagger (ordem)

**POST /api/maquinas**
```json
{"codigo":"EXT-005","nome":"Extrusora 5 Camadas","setor":"Extrusao","fabricante":"Windmoller","status":"ATIVA"}
```

**POST /api/tecnicos**
```json
{"nome":"Joao Silva","matricula":"TEC-001","especialidade":"Mecanica"}
```

**POST /api/planos** (ajuste os IDs se necessário)
```json
{"maquinaId":1,"descricao":"Verificacao dos filtros","periodicidadeDias":30,"proximaData":"2026-11-01"}
```

**POST /api/execucoes** (ajuste IDs e use data de hoje, não futura)
```json
{"planoId":1,"tecnicoId":1,"dataExecucao":"2026-10-26","observacoes":"Filtros inspecionados"}
```

## Regras de negócio
- Código de máquina e matrícula de técnico não podem se repetir.
- Periodicidade deve ser de pelo menos 1 dia; todos os vínculos precisam existir.
- Plano com próximo vencimento anterior a hoje fica `VENCIDA`.
- Execução não pode ter data futura nem anterior ao início do ciclo atual; a máquina deve estar `ATIVA`.
- Ao executar, a próxima data será `dataExecucao + periodicidadeDias`.
- Máquinas com planos e planos com histórico de execuções não podem ser excluídos (preserva integridade).

## Organização
`controller/` endpoints HTTP; `service/` regras; `repository/` acesso JPA; `model/` entidades; `dto/` entradas e saídas; `exception/` erros; `config/` OpenAPI.

## Testes
`mvn test` executa o teste básico de carregamento de contexto. Recomendamos testes de integração adicionais antes do uso real. API **educacional**, sem autenticação, auditoria ou deploy de produção.

## IDs automáticos
Nos endpoints POST /api/maquinas e POST /api/tecnicos, o JSON de entrada não possui ID. O banco gera o ID automaticamente e a resposta traz o identificador criado. Planos e execuções também usam DTOs de entrada sem ID próprio; IDs de máquinas, técnicos e planos são informados somente como referências para relacionamentos.

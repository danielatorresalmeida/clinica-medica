# Clínica Médica

Projeto web desenvolvido em Java com Spring Boot para gestão de uma clínica médica.

A aplicação permite gerir utilizadores associados a diferentes funções, como pacientes, médicos e secretárias. Também inclui páginas e funcionalidades relacionadas com consultas, exames, receitas e disponibilidades.

## Funcionalidades

- Registo de pacientes, médicos e secretárias
- Listagem de utilizadores e entidades da clínica
- Edição de dados registados
- Remoção de pacientes, médicos e secretárias
- Gestão de consultas
- Gestão de exames
- Gestão de receitas
- Controlo de acesso baseado em funções, RBAC

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- Maven
- HTML e CSS

## Estrutura do projeto

```text
src/main/java/com/iefp/clinicaMedica
├── controller
├── model
├── repository
├── service
└── ClinicaMedicaApplication.java

src/main/resources
├── templates
└── static

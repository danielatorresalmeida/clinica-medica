# Clínica Médica

Sistema web de gestão clínica desenvolvido com Java, Spring Boot, Thymeleaf e base de dados H2.

O sistema permite gerir utilizadores, pacientes, médicos, secretárias, consultas, disponibilidades, exames e receitas médicas. O acesso às páginas é controlado por perfil de utilizador.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- H2 Database
- HTML
- CSS
- JavaScript
- Maven

## Como executar o projeto

### 1. Clonar o repositório

bash
git clone <URL_DO_REPOSITORIO>

Depois entra na pasta do projeto:

cd clinicaMedica
2. Executar a aplicação

No Windows:

mvnw.cmd spring-boot:run

No Linux/Mac:

./mvnw spring-boot:run
3. Aceder à aplicação

Depois de iniciar o servidor, abre no browser:

http://localhost:8080/login
Base de dados

O projeto usa uma base de dados H2 em memória.

A base de dados é recriada sempre que a aplicação inicia.

A consola H2 pode ser acedida em:

http://localhost:8080/h2-console

Dados de ligação:

JDBC URL: jdbc:h2:mem:clinica
Username: sa
Password:

O campo da password deve ficar vazio.

Credenciais de teste

Usa estas contas para testar os diferentes perfis do sistema.

Perfil	Email	Senha	Permissões principais
Administrador	admin@clinica.pt	123456	Acesso total ao sistema
Secretária	secretaria@clinica.pt	123456	Gestão clínica, consultas, exames, receitas, pacientes, médicos e disponibilidades
Médico	medico1@clinica.pt	123456	Consultas, pacientes, exames, receitas e disponibilidades associadas
Paciente	paciente1@clinica.pt	123456	Consultas, exames e receitas próprias

Nota: estas credenciais são apenas para teste local/académico. Não devem ser usadas em produção.

Perfis de acesso
Administrador

O administrador pode aceder às principais áreas do sistema, incluindo:

Página principal
Registo de utilizadores
Listagem geral
Pacientes
Médicos
Secretárias
Consultas
Exames
Receitas
Disponibilidades
Secretária

A secretária pode aceder às áreas de gestão clínica, mas não deve criar utilizadores administrativos.

Pode consultar e gerir:

Pacientes
Médicos
Consultas
Exames
Receitas
Disponibilidades
Médico

O médico pode consultar e gerir informação clínica associada ao seu perfil.

Pode aceder a:

Pacientes
Consultas
Exames
Receitas
Disponibilidades
Paciente

O paciente tem acesso limitado às suas próprias informações clínicas.

Pode aceder a:

Consultas
Exames
Receitas
Funcionalidades principais
Login e controlo de acesso

O sistema possui login com verificação de email e senha.

Após o login, cada utilizador é redirecionado para a página principal, onde vê apenas as opções permitidas para o seu perfil.

Caso tente aceder a uma página sem permissão, é redirecionado para a página de acesso negado.

Gestão de utilizadores

O sistema permite registar utilizadores com diferentes perfis:

Administrador
Secretária
Médico
Paciente
Consultas

Permite consultar marcações existentes.

Também permite marcar novas consultas com base nas disponibilidades livres dos médicos.

Disponibilidades

Permite criar horários disponíveis para médicos.

As disponibilidades podem ser usadas posteriormente para marcação de consultas.

Exames

Permite registar exames associados a consultas.

Cada exame pode conter:

Consulta associada
Paciente
Médico
Tipo de exame
Descrição
Data do pedido
Estado
Resultado
Receitas

Permite registar receitas médicas associadas a consultas.

Cada receita pode conter:

Consulta associada
Paciente
Médico
Medicamento
Dosagem
Instruções
Data de emissão
Estrutura principal do projeto
src
 └── main
     ├── java
     │   └── com.iefp.clinicaMedica
     │       ├── config
     │       ├── controller
     │       ├── model
     │       ├── repository
     │       └── service
     │
     └── resources
         ├── static
         │   ├── css
         │   └── js
         ├── templates
         ├── application.properties
         └── data.sql
Páginas principais
/login
/home
/registar-utilizador
/listagem
/pacientes
/medicos
/secretarias
/consultas
/consultas/nova
/exames
/exames/novo
/receitas
/receitas/nova
/disponibilidades
/disponibilidades/nova
/acesso-negado
Observações

Este projeto foi desenvolvido para fins académicos, com foco na aplicação de conceitos de desenvolvimento web com Spring Boot, MVC, persistência de dados, autenticação simples, controlo de permissões por perfil e utilização de templates Thymeleaf.

A aplicação usa dados de teste carregados automaticamente ao iniciar, permitindo testar rapidamente os diferentes perfis do sistema.

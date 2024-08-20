# TalentHub

## Descrição

**TalentHub** é uma aplicação web desenvolvida como parte do projeto da disciplina de Programação para Web 2. O sistema tem como objetivo facilitar o controle de ofertas de estágio feitas por empresas e permitir que alunos se candidatem a essas oportunidades. A aplicação também oferece funcionalidades para que os coordenadores de curso acompanhem e gerenciem os estágios, garantindo um fluxo eficiente entre as empresas, os alunos e a instituição de ensino.

## Funcionalidades

### Para Alunos
- **Candidatura a Ofertas de Estágio**: Alunos podem se candidatar a diversas ofertas de estágio disponíveis, com a possibilidade de filtrar por critérios específicos.
- **Cadastro de Aluno**: Alunos podem criar um perfil no sistema, informando seus dados pessoais, habilidades e competências.
- **Consulta de Candidaturas**: Visualização do status de suas candidaturas em andamento.

### Para Empresas
- **Cadastro de Empresa**: Empresas podem se registrar no sistema, fornecendo informações como CNPJ, endereço, e dados de contato.
- **Registro de Ofertas de Estágio**: Empresas podem criar novas ofertas de estágio, detalhando as atividades, requisitos e benefícios.
- **Consulta de Candidatos**: Visualização dos alunos candidatos a cada oferta.
- **Conversão de Oferta em Estágio**: Seleção de candidatos para transformar uma oferta em estágio formal.
- **Cancelamento de Ofertas**: Empresas podem cancelar ofertas de estágio registradas.

### Para Coordenadores de Curso
- **Acompanhamento de Ofertas e Estágios**: Visualização e gerenciamento de todas as ofertas de estágio e os estágios em andamento.
- **Consulta de Alunos Candidatos**: Acesso às informações dos alunos candidatos a vagas de estágio.
- **Alteração de Cadastros**: Edição e gerenciamento de cadastros de empresas e alunos.
- **Download de Termo de Estágio**: Geração e download do termo de estágio em formato PDF.

## Tecnologias Utilizadas

- **Back-End**: Spring Boot / Spring MVC
- **Front-End**: Thymeleaf, Bootstrap
- **Banco de Dados**: Qualquer banco de dados suportado pelo Hibernate
- **Segurança**: Spring Security 6.0+
- **Persistência**: Hibernate ORM
- **Validação**: Formulários com validação e mensagens de erro
- **Padrões de Projeto**: Post-Redirect-Get, Layouts e Fragmentos Thymeleaf
- **Controle de Versão**: Git, utilizando Maven para gerenciamento de dependências

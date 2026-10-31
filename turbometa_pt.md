# TurboMeta
## Proposta de Solução Técnica

**Tipo de documento:** Proposta de Arquitetura Técnica e Implementação  
**Projeto:** Aplicação Web TurboMeta  
**Status:** Solução Proposta  
**Arquitetura:** Monólito Modular

---

## 1. Resumo Executivo

O TurboMeta é uma aplicação web leve concebida para fornecer uma plataforma centralizada para gerir e pesquisar informações sobre turbocompressores.

A aplicação terá duas áreas distintas:

- **Backoffice:** Interface administrativa para gerir utilizadores e registos de turbocompressores.
- **Frontoffice:** Interface voltada para o utilizador, permitindo que utilizadores autenticados pesquisem e consultem informações sobre turbocompressores.

A solução proposta prioriza a simplicidade, a manutenibilidade, a segurança e a facilidade de implementação.

A aplicação será desenvolvida em Java e Spring Boot, com renderização de HTML no servidor e SQLite como base de dados.

A arquitetura evita deliberadamente infraestrutura e complexidade tecnológica desnecessárias. A solução completa será entregue como uma única aplicação implementável.

## 2. Objectivos do Projeto

Os principais objectivos são:

1. Fornecer autenticação segura de utilizadores.
2. Implementar controlo de acesso por perfil.
3. Permitir que os administradores gestionem os utilizadores da aplicação.
4. Permitir que os administradores gestionem os registos de turbocompressores.
5. Oferecer uma interface de pesquisa intuitiva para utilizadores autenticados.
6. Garantir usabilidade responsiva em computadores e dispositivos móveis.
7. Manter o mínimo possível de requisitos de implementação e operação.

## 3. Stack Tecnológica Proposta

As seguintes tecnologias são propostas:

| Componente | Tecnologia | Finalidade |
|---|---|---|
| Linguagem de Programação | Java 25 | Desenvolvimento da aplicação |
| Framework do Backend | Spring Boot 4.x | Framework da aplicação |
| Camada Web | Spring MVC | Tratamento de pedidos HTTP |
| Frontend | Thymeleaf | Renderização de HTML no servidor |
| Framework de UI | Bootstrap 5 | Interface responsiva |
| Base de Dados | SQLite | Persistência relacional |
| Acesso a Dados | Spring JDBC | Operações na base de dados |
| Autenticação | Spring Security | Autenticação e autorização |
| Hash de Palavras-passe | BCrypt / Argon2 | Armazenamento seguro de palavras-passe |
| Migrações da Base de Dados | Flyway | Gestão do esquema da base de dados |
| Ferramenta de Build | Maven | Gestão de dependências e build |
| Implementação | Docker | Empacotamento e implementação da aplicação |

### Justificação da Seleção de Tecnologias

**Spring Boot**

Fornece um framework maduro para aplicações, injeção de dependências, gestão de configuração, validação e integração de segurança.

**Thymeleaf + Bootstrap**

Oferece uma abordagem direta para construir interfaces web responsivas sem introduzir a complexidade de um framework frontend JavaScript separado.

**SQLite**

É uma base de dados relacional leve que não exige um servidor de base de dados dedicado. Os dados são armazenados num único ficheiro persistente.

**Spring Security**

Fornece mecanismos estabelecidos de autenticação e autorização, incluindo gestão de sessões e controlo de acesso por perfil.

**Spring JDBC**

Fornece acesso direto e leve à base de dados, sem exigir uma camada ORM complexa. Isto é particularmente adequado para o modelo de dados relativamente simples e para a integração com SQLite.

## 4. Arquitetura da Solução

A arquitetura proposta segue a abordagem de **Monólito Modular**.

Todos os componentes da aplicação serão executados dentro de uma única aplicação Spring Boot e partilharão o mesmo ciclo de implementação.

### 4.1 Arquitetura Lógica

A aplicação será composta pelas seguintes camadas:

**Camada de Apresentação**
- Templates Thymeleaf.
- Componentes Bootstrap.
- Formulários e tabelas HTML.
- Integração opcional com HTMX para interações dinâmicas de pesquisa.

**Camada de Aplicação**
- Controladores Spring MVC.
- Serviços da aplicação.
- Validação de entrada.
- Regras de negócio e verificações de autorização.

**Camada de Segurança**
- Spring Security.
- Autenticação baseada em sessão.
- Controlo de acesso com base nos privilégios do utilizador.
- Hash de palavras-passe.

**Camada de Persistência**
- Spring JDBC.
- Componentes de repositório.
- Base de dados SQLite.
- Migrações da base de dados com Flyway.

### 4.2 Módulos da Aplicação

A aplicação será organizada em dois módulos funcionais principais.

**Módulo de Gestão de Utilizadores**
- Autenticação de utilizadores.
- Administração de utilizadores.
- Ativação e desativação de contas.
- Autorização de utilizadores.

**Módulo de Gestão de Turbocompressores**
- Registo de turbocompressores.
- Edição de turbocompressores.
- Eliminação de turbocompressores.
- Pesquisa de turbocompressores.

## 5. Design da Base de Dados

A aplicação utilizará inicialmente SQLite com duas tabelas principais.

### 5.1 Tabela de Utilizadores

Tabela: `app_user`

| Campo | Tipo de Dados | Restrições |
|---|---|---|
| id | INTEGER | Chave primária, auto incremento |
| username | VARCHAR(30) | NOT NULL |
| email | TEXT | UNIQUE, NOT NULL |
| password_hash | TEXT | NOT NULL |
| active | BOOLEAN | NOT NULL |
| is_admin | BOOLEAN | NOT NULL |

**Regras de Negócio:**
- Endereços de e-mail devem ser válidos e únicos.
- As palavras-passe devem ser guardadas com hash de forma segura antes da persistência.
- Utilizadores inativos não poderão autenticar-se.
- Os administradores têm acesso ao backoffice.
- Os utilizadores regulares têm acesso ao frontoffice.

### 5.2 Tabela de Turbocompressores

Tabela: `turbo`

| Campo | Tipo de Dados | Restrições |
|---|---|---|
| ref | TEXT | Chave primária |
| brand | TEXT | NOT NULL |
| model | TEXT | NOT NULL |
| geo | TEXT | Opcional |

**Regras de Negócio:**
- Cada turbocompressor deve ter uma referência única.
- A marca e o modelo são obrigatórios.
- A informação geográfica é opcional.
- Os registos de turbocompressores só podem ser alterados por administradores.

### 5.3 Considerações sobre a Base de Dados

A base de dados será armazenada num ficheiro persistente fora do contentor da aplicação.

O Flyway será utilizado para versionar e gerir alterações ao esquema da base de dados, garantindo uma inicialização e atualização consistentes.

## 6. Requisitos Funcionais

### 6.1 Autenticação

A página inicial da aplicação será a ecrã de login.

**Funcionalidades:**
- Identidade visual e logótipo do TurboMeta.
- Autenticação por e-mail e palavra-passe.
- Validação de entradas.
- Feedback para credenciais inválidas.
- Funcionalidade de logout.
- Gestão de sessão.

Após autenticação bem-sucedida, os utilizadores serão redirecionados de acordo com as suas permissões:

- Administradores → Backoffice.
- Utilizadores regulares → Frontoffice.

### 6.2 Backoffice – Administração

Apenas os utilizadores autenticados com privilégios de administrador (`is_admin = true`) podem aceder ao backoffice.

#### 6.2.1 Gestão de Utilizadores

Os administradores poderão:

| Operação | Descrição |
|---|---|
| Pesquisar | Procurar utilizadores por nome de utilizador ou e-mail |
| Inserir | Criar novos utilizadores da aplicação |
| Editar | Atualizar informações do utilizador e permissões |
| Remover | Eliminar registos de utilizadores |
| Ativar/Desativar | Ativar ou desativar contas de utilizador |

Considerações adicionais:
- As palavras-passe serão guardadas com hash antes do armazenamento.
- A unicidade do e-mail será aplicada.
- Os administradores poderão atribuir ou revogar privilégios administrativos.
- A aplicação deve impedir a eliminação ou desativação acidental do último administrador ativo.

#### 6.2.2 Gestão de Turbocompressores

Os administradores poderão:

| Operação | Descrição |
|---|---|
| Pesquisar | Procurar por referência, marca ou modelo |
| Inserir | Registar um novo turbocompressor |
| Editar | Atualizar informações de um turbocompressor existente |
| Remover | Eliminar registos de turbocompressores |

Os resultados da pesquisa serão apresentados numa tabela estruturada e paginada.

### 6.3 Frontoffice – Pesquisa de Turbocompressores

Os utilizadores autenticados não administrativos terão acesso à funcionalidade de pesquisa de turbocompressores.

**Funcionalidade disponível:**
- Pesquisa por referência.
- Pesquisa por marca.
- Pesquisa por modelo.
- Apresentação de registos de turbocompressores correspondentes.
- Visualização dos detalhes disponíveis do turbocompressor.

O frontoffice será apenas de leitura. Os utilizadores não terão permissão para criar, alterar ou eliminar registos.

## 7. Interface do Utilizador

A interface seguirá um design limpo, moderno e responsivo.

### 7.1 Página de Login

- Logótipo e identidade visual do TurboMeta.
- Campos de e-mail e palavra-passe.
- Ação de login.
- Mensagens de erro de autenticação.

### 7.2 Backoffice

Navegação proposta:

- Dashboard.
- Utilizadores.
- Turbos.
- Logout.

As ecrãs de administração fornecerão:
- Pesquisa e filtros.
- Apresentação tabular de dados.
- Formulários de criação e edição.
- Confirmação antes da eliminação.
- Mensagens claras de validação e erro.

### 7.3 Frontoffice

O ecrã principal focar-se-á na pesquisa de turbocompressores.

Os utilizadores serão apresentados com:
- Campo de pesquisa.
- Filtros de pesquisa.
- Resultados da pesquisa.
- Referência, marca, modelo e informações geográficas do turbocompressor.

## 8. Considerações de Segurança

A segurança será implementada utilizando Spring Security.

As seguintes medidas serão incluídas:

1. Hash de palavra-passe usando BCrypt ou Argon2.
2. Autenticação baseada em sessão.
3. Autorização por perfil.
4. Proteção de endpoints administrativos.
5. Proteção CSRF para submissão de formulários.
6. Validação de entrada no servidor.
7. Proteção contra injeção SQL através de consultas parametrizadas.
8. Encerramento seguro da sessão no logout.
9. Restrição de acesso para contas inativas.
10. Prevenção de escalonamento de privilégios não autorizado.

A autorização será sempre aplicada no servidor, independentemente da interface apresentada ao utilizador.

## 9. Arquitetura de Implementação

A aplicação será empacotada e implementada como um contentor Docker.

### 9.1 Modelo de Implementação

A implementação proposta consiste em:

- Uma aplicação Spring Boot.
- Um contentor Docker.
- Um ficheiro de base de dados SQLite persistente.
- Proxy reverso opcional para terminação HTTPS.

### 9.2 Persistência

A base de dados SQLite será montada utilizando um volume Docker ou um diretório persistente do host.

Isto garante que a reinstalação da aplicação ou a substituição do contentor não resulte em perda de dados da base de dados.

### 9.3 Vantagens da Implementação

- Processo de implementação simples.
- Requisitos mínimos de infraestrutura.
- Nenhum servidor de base de dados dedicado.
- Baixo esforço operacional.
- Backup e restauração fáceis.
- Atualizações simples da aplicação.

## 10. Requisitos Não Funcionais

| Categoria | Requisito |
|---|---|
| Manutenibilidade | Componentes da aplicação modulares e claramente separados |
| Segurança | Autenticação e autorização aplicadas no servidor |
| Desempenho | Consultas eficientes à base de dados e resultados de pesquisa paginados |
| Disponibilidade | Disponibilidade da aplicação dependente da infraestrutura de alojamento |
| Usabilidade | Interface responsiva e intuitiva |
| Portabilidade | Implementação baseada em Docker |
| Escalabilidade | Arquitetura permite migração futura para PostgreSQL |
| Integridade dos Dados | Restrições da base de dados e validação ao nível da aplicação |

O SQLite é apropriado para a carga inicial esperada, especialmente quando a concorrência de escrita é limitada. Se a utilização da aplicação ou os requisitos de escrita concorrente aumentarem significativamente, o PostgreSQL pode ser introduzido como plataforma de persistência alternativa.

## 11. Escopo do Projeto

### Incluído na Entrega Inicial

- Página de login do TurboMeta e identidade visual.
- Autenticação de utilizadores e gestão de sessão.
- Administração de utilizadores (CRUD).
- Administração de turbocompressores (CRUD).
- Funcionalidade de pesquisa de turbocompressores.
- Controlo de acesso por perfil.
- Interface responsiva.
- Integração com base de dados SQLite.
- Migrações da base de dados.
- Configuração de empacotamento e implementação em Docker.

### Possíveis Melhorias Futuras

A arquitetura permitirá a introdução de funcionalidades adicionais sem exigir um redesenho fundamental.

Possíveis melhorias incluem:

- Recuperação de palavra-passe.
- Fluxos de registo de utilizadores.
- Importação/exportação de registos de turbocompressores (CSV/Excel).
- Filtros avançados de pesquisa.
- Imagens e anexos de turbocompressores.
- Registos de auditoria e rastreio de atividades administrativas.
- Perfis e permissões adicionais de utilizadores.
- Integração de API com sistemas externos.
- Migração para PostgreSQL.

Estas melhorias não fazem parte do âmbito inicial, a menos que sejam explicitamente acordadas.

## 12. Entregáveis Propostos

A entrega do projeto incluirá:

1. Código-fonte completo da aplicação.
2. Configuração do projeto Maven.
3. Esquema da base de dados SQLite e migrações do Flyway.
4. Implementação de autenticação e autorização.
5. Gestão de utilizadores no backoffice.
6. Gestão de turbocompressores no backoffice.
7. Pesquisa de turbocompressores no frontoffice.
8. Interface responsiva.
9. Dockerfile e configuração de implementação.
10. Documentação técnica abrangendo configuração, implementação e manutenção.

## 13. Conclusão

A solução proposta para o TurboMeta oferece uma abordagem pragmática e sustentável para entregar a funcionalidade necessária.

Ao combinar Java, Spring Boot, Thymeleaf, Bootstrap e SQLite, a aplicação pode fornecer todas as capacidades necessárias sem introduzir complexidade arquitetural ou operacional desnecessária.

A abordagem de Monólito Modular oferece um equilíbrio entre separação clara de responsabilidades, facilidade de desenvolvimento e implementação direta.

A solução foi concebida para responder aos requisitos atuais, mantendo a flexibilidade para evoluir conforme surgirem necessidades futuras do negócio.
